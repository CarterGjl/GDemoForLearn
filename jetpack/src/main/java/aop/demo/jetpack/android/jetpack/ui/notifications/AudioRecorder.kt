package aop.demo.jetpack.android.jetpack.ui.notifications

import android.media.*
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.concurrent.ArrayBlockingQueue


class AudioRecorder {
    var end: Long = 0
    private var mAudioBos: BufferedOutputStream? = null
    private lateinit var mFileOutputStream: FileOutputStream
    private val AUDIO_MIME_TYPE = "audio/mp4a-latm"
    private lateinit var encodeInputBuffers: Array<out ByteBuffer>
    private lateinit var encodeOutputBuffers: Array<out ByteBuffer>
    private lateinit var mAudioEncodeBufferInfo: MediaCodec.BufferInfo

    private var audioRecord: AudioRecord? = null
    private var mAudioEncoder: MediaCodec? = null
    private lateinit var mainScope: CoroutineScope

    private var mIsRecording = false
    private fun initAudioRecord() {
        mainScope = MainScope()
        val audioSource = MediaRecorder.AudioSource.MIC
        val sampleRate = 44100
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        audioRecord = AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, minBufferSize)
    }

    val queue = ArrayBlockingQueue<ByteArray>(10);
    private fun initAudioEncoder() {

        try {
            val bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT)
            mAudioEncoder = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)
            val audioFormat = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, 44100, 1)
            audioFormat.setInteger(MediaFormat.KEY_BIT_RATE, 96_000)
            audioFormat.setInteger(MediaFormat.KEY_AAC_PROFILE,MediaCodecInfo.CodecProfileLevel
                    .AACObjectLC)
            audioFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 0)
//            audioFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, bufferSize)
            mAudioEncoder?.configure(null, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //启动mediacodec 等待传递数据

    }

    fun start() {
        if (mIsRecording) {
            mIsRecording = false
        } else {
            initAudioEncoder()
            initAudioRecord()
            mIsRecording = true

            mainScope.launch(Dispatchers.IO) {
                recorder()
            }
            mainScope.launch(Dispatchers.IO) {
                mAudioEncoder?.start()
                encodeInputBuffers = mAudioEncoder!!.inputBuffers
                encodeOutputBuffers = mAudioEncoder!!.outputBuffers
                mAudioEncodeBufferInfo = MediaCodec.BufferInfo()
                encodePcm()
            }
        }
    }

    private fun encodePcm() {
        println("encodePcm")
        var inputIndex: Int
        var inputBuffer: ByteBuffer
        var outputIndex: Int
        var outputBuffer: ByteBuffer
        var chunkAudio: ByteArray
        var outBitSize: Int
        var outPacketSize: Int
        var chunkPCM: ByteArray?

        while (mIsRecording || !queue.isEmpty()) {
            chunkPCM = getPCMData()//获取解码器所在线程输出的数据 代码后边会贴上
            if (chunkPCM == null) {
                continue
            }
            inputIndex = mAudioEncoder?.dequeueInputBuffer(-1)!!//同解码器
            if (inputIndex >= 0) {
                inputBuffer = encodeInputBuffers[inputIndex]//同解码器
                inputBuffer.clear()//同解码器
                inputBuffer.limit(chunkPCM.size)
                inputBuffer.put(chunkPCM)//PCM数据填充给inputBuffer
                mAudioEncoder!!.queueInputBuffer(inputIndex, 0, chunkPCM.size, 0, 0)//通知编码器 编码
            }

            outputIndex = mAudioEncoder!!.dequeueOutputBuffer(mAudioEncodeBufferInfo, 10000)
            while (outputIndex >= 0) {
                outBitSize = mAudioEncodeBufferInfo.size
                outPacketSize = outBitSize + 7//7为ADTS头部的大小
                outputBuffer = encodeOutputBuffers[outputIndex]//拿到输出Buffer
                outputBuffer.position(mAudioEncodeBufferInfo.offset)
                outputBuffer.limit(mAudioEncodeBufferInfo.offset + outBitSize)
                chunkAudio = ByteArray(outPacketSize)
                addADTStoPacket(44100, chunkAudio, outPacketSize)//添加ADTS
                outputBuffer.get(chunkAudio, 7, outBitSize)//将编码得到的AAC数据 取出到byte[]中 偏移量offset=7
                outputBuffer.position(mAudioEncodeBufferInfo.offset)
                try {
                    mAudioBos?.write(chunkAudio, 0, chunkAudio.size)//BufferOutputStream
                    // 将文件保存到内存卡中 *.aac
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mAudioEncoder!!.releaseOutputBuffer(outputIndex, false)
                outputIndex = mAudioEncoder!!.dequeueOutputBuffer(mAudioEncodeBufferInfo, 10000)
            }
        }

        stopRecorder()
    }

    fun stopRecorder(): Boolean {
        try {
            mAudioBos?.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (mAudioBos != null) {
                try {
                    mAudioBos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    mAudioBos = null
                }
            }
        }
        if (audioRecord != null) {
            audioRecord!!.stop()
            audioRecord!!.release()
            audioRecord = null
        }

        if (mAudioEncoder != null) {
            mAudioEncoder!!.stop()
            mAudioEncoder!!.release()
            mAudioEncoder = null
        }

        end = System.currentTimeMillis()

        val second = ((end - start) / 1000) as Int

//        runOnUiThread(Runnable {
//            recoderBtn.setText("开始录音")
//
//            if (second > 3) {
//                textView.setText(textView.getText() + "\n录音成功，时间为" + second + "秒")
//            } else {
//                textView.setText(textView.getText() + "\n录音失败，时间少于三秒")
//                mAudioFile.deleteOnExit()
//            }
//        })

        return true
    }

    /**
     * 添加ADTS头
     *
     * @param packet
     * @param packetLen
     */
    fun addADTStoPacket(sampleRateType: Int, packet: ByteArray, packetLen: Int) {
        val profile = 2 // AAC LC
        val chanCfg = 2 // CPE
        packet[0] = 0xFF.toByte()
        packet[1] = 0xF9.toByte()
        packet[2] = ((profile - 1 shl 6) + (sampleRateType shl 2) + (chanCfg shr 2)).toByte()
        packet[3] = ((chanCfg and 3 shl 6) + (packetLen shr 11)).toByte()
        packet[4] = (packetLen and 0x7FF shr 3).toByte()
        packet[5] = ((packetLen and 7 shl 5) + 0x1F).toByte()
        packet[6] = 0xFC.toByte()
    }

    private var bufer: ByteArray = ByteArray(2048)
    private var start: Long = 0

    private fun recorder() {
        println("recorder")
        try {
            val filePath = Environment.getExternalStorageDirectory().absolutePath + "/RecorderTest/" +
                    System
                            .currentTimeMillis() + ".aac"
            val file = File(filePath)
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            file.createNewFile()
            mFileOutputStream = FileOutputStream(file)
            mAudioBos = BufferedOutputStream(mFileOutputStream, 200 * 1024)
            audioRecord?.startRecording()
            start = System.currentTimeMillis()
            while (mIsRecording) {
                val read = audioRecord?.read(bufer, 0, 2048)
                if (read != null) {
                    if (read > 0) {
                        val audio = ByteArray(read)
                        System.arraycopy(bufer, 0, audio, 0, read)
                        putPcmData(audio)
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            audioRecord?.release()
            audioRecord = null
        }
    }

    private fun getPCMData(): ByteArray? {
        try {
            return if (queue.isEmpty()) {
                null
            } else queue.take()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return null
    }

    val TAG = "record"
    private fun putPcmData(audio: ByteArray) {
        Log.e(TAG, "putPCMData")
        try {
            queue.put(audio)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}