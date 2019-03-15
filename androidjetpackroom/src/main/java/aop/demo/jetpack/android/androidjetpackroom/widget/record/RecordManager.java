package aop.demo.jetpack.android.androidjetpackroom.widget.record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import aop.demo.jetpack.android.androidjetpackroom.widget.record.messagetype.HandlerMessageType;

public class RecordManager implements RecordControlListener {


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            @HandlerMessageType
            int what = msg.what;
            switch (what) {
                case HandlerMessageType.UPDATE_VOICE_CHANGE:
                    int volume = msg.arg1;
                    int time = msg.arg2;
                    if (mListener != null) {

                        mListener.onVolumChanged(volume);
                        if (time % 10 == 0) {

                            mListener.onRecordTimeChanged((time / 10), resourceId);
                        }
                    }
                    return true;
            }
            return false;
        }
    });
    @SuppressLint("StaticFieldLeak")
    private static RecordManager mRecordManager = null;
    private MediaRecorder mMediaRecorder;
    private int SAMPLE_RATE_IN_HZ = 16000; // 采样率
    private String recordPath;
    private File mFile;
    private ExecutorService mThreadPool;

    private String resourceId;
    public String getRecordPath() {


        return recordPath == null ? "" : recordPath;
    }

    private AtomicBoolean mIsRecording = new AtomicBoolean(false);
    private long mStartTime;
    private OnRecordChangeListener mListener;
    private Context mContext;

    private RecordManager() {
    }

    public static RecordManager getInstance(Context context) {
        synchronized (RecordManager.class) {
            if (mRecordManager == null) {
                mRecordManager = new RecordManager();
            }
            mRecordManager.init(context);
        }
        return mRecordManager;
    }

    private void init(Context context) {

        mContext = context;
        mThreadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void startRecording() {

        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
            //设置输出格式
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);// 设置输出编码格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置音频编码器可用于录制
            mMediaRecorder.setAudioChannels(1);// 设置录制的音频通道数-单通道
            mMediaRecorder.setAudioEncodingBitRate(SAMPLE_RATE_IN_HZ);// 设置音频编码录音比特率
            mMediaRecorder.setOnErrorListener(new RecorderErrorListener());// 设置录音错误监听
        } else {

            mMediaRecorder.stop();
            mMediaRecorder.reset();
        }

        //
        String path = mMediaRecorder.toString();
        this.recordPath = getRecordFilePath(path);
        this.mFile = new File(recordPath);
        mMediaRecorder.setOutputFile(mFile.getAbsolutePath());

        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            //设置正在录音
            mIsRecording.set(true);
            mStartTime = new Date().getTime();
            mThreadPool.execute(new RecordingChangeUpDater());

            Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(100);
            }

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            mIsRecording.set(false);
            mMediaRecorder.release();
            mMediaRecorder = null;
        }

    }


    @Override
    public String getRecordFileName() {
        return null;
    }

    private static final String TAG = "RecordManager";
    @Override
    public void cancelRecording() {

        if (mMediaRecorder == null) {
            return;
        }
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;

        if (mFile != null && mFile.exists() && !mFile.isDirectory()) {

            boolean delete = mFile.delete();
//            Logger.i("delete" + delete);
        }
        mIsRecording.set(false);
    }

    @Override
    public int stopRecording() {

        if (mMediaRecorder != null) {

            mIsRecording.set(false);
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;

            //获取当前的录音长度
            return (int) ((new Date().getTime() - mStartTime) / 1000);
        }
        return 0;
    }

    @Override
    public boolean isRecording() {
        return mIsRecording.get();
    }

    @Override
    public MediaRecorder getMediaRecorder() {
        return mMediaRecorder;
    }

    /**
     * @param record 录音文件名字
     * @return 录音文件完整路径
     */
    @Override
    public String getRecordFilePath(@NonNull String record) {

        this.resourceId = record;
        // TODO: 2019/2/25
        return "";
    }

    @NonNull
    public String getResourceId() {


        return resourceId == null ? "" : resourceId;
    }

    @Override
    public void setOnRecordChangeListener(@NonNull OnRecordChangeListener listener) {
        mListener = listener;
    }

    private final class RecordingChangeUpDater implements Runnable {
        @Override
        public void run() {

            int currentRecordCounter = 0;
            while (mIsRecording.get()) {

                int volume = 0;
                try {
                    volume = mMediaRecorder.getMaxAmplitude();
                } catch (Exception e) {
//                    Logger.e(e, "");
                    Log.e(TAG, "run: ",e );
                }
//                32768 调节灵敏度
                int value = 10 * volume / 2222;
                if (value > 9)
                    value = 9;
                Message msg = Message.obtain();
                msg.arg1 = value;
                msg.arg2 = currentRecordCounter;
                msg.what = HandlerMessageType.UPDATE_VOICE_CHANGE;
                mHandler.sendMessage(msg);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }
                currentRecordCounter++;
            }

        }
    }

    public class RecorderErrorListener implements MediaRecorder.OnErrorListener {

        @Override
        public void onError(MediaRecorder mp, int what, int extra) {
            String whatDescription;
            switch (what) {
                case MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN:
                    whatDescription = "MEDIA_RECORDER_ERROR_UNKNOWN";
                    break;
                default:
                    whatDescription = Integer.toString(what);
                    break;
            }

        }
    }

}
