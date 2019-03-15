//package aop.demo.jetpack.android.androidjetpackroom.widget;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.orhanobut.logger.Logger;
//import com.talent.chat.R;
//import com.talent.chat.carter.manager.record.OnRecordChangeListener;
//import com.talent.chat.carter.widget.Event.MotionEventActionType;
//import com.talent.chat.carter.widget.status.RecordStatus;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.AppCompatButton;
//import aop.demo.jetpack.android.androidjetpackroom.R;
//import aop.demo.jetpack.android.androidjetpackroom.widget.Event.MotionEventActionType;
//import aop.demo.jetpack.android.androidjetpackroom.widget.record.OnRecordChangeListener;
//import aop.demo.jetpack.android.androidjetpackroom.widget.record.RecordManager;
//import aop.demo.jetpack.android.androidjetpackroom.widget.status.RecordStatus;
//
//public class RecordButton extends AppCompatButton {
//
//
//    private final int MAX_RECORD_TIME = 60 * 1000;
//    private Dialog mRecordDialog;
//    private RecordManager mManager;
//    private Dialog mVoiceTooShortDialog;
//    private ImageView mIvRecVolume;
//    private RelativeLayout recrod_dialog_mic_content;
//    private RelativeLayout mIvCancelLayout;
//    private TextView mTvRecordDialogTxt;
//    private OnSendMessageListener mSendMessageListener;
//
//    public RecordButton(Context context) {
//        this(context, null);
//    }
//
//    public RecordButton(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initA();
//    }
//
//    private static final String TAG = "RecordButton";
//    private void initA() {
//
//        mRecordDialog = new Dialog(getContext(), R.style.DialogStyle);
//        mRecordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mRecordDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        mRecordDialog.setContentView(R.layout.record_dialog);
//        mVoiceTooShortDialog = new Dialog(getContext(), R.style.DialogStyle);
//        mVoiceTooShortDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mVoiceTooShortDialog.setCancelable(false);
//        mVoiceTooShortDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        mVoiceTooShortDialog.setContentView(R.layout.record_too_short_layout);
//
//        mIvRecVolume = mRecordDialog.findViewById(R.id.record_dialog_img);
//        recrod_dialog_mic_content = mRecordDialog.findViewById(R.id.recrod_dialog_mic_content);
//        mIvCancelLayout = mRecordDialog.findViewById(R.id.record_dialog_cancel_layout);
//        mTvRecordDialogTxt = mRecordDialog.findViewById(R.id.record_dialog_txt);
//        mManager = RecordManager.getInstance(getContext());
//        mManager.setOnRecordChangeListener(new OnRecordChangeListener() {
//            @Override
//            public void onVolumChanged(int voiceValue) {
//
//                setValue(voiceValue);
//                Log.d(TAG, "onVolumChanged: "+voiceValue);
//            }
//
//            @Override
//            public void onRecordTimeChanged(int timeValue, String localPath) {
//
//                if (timeValue > MAX_RECORD_TIME) {
//                    mManager.stopRecording();
//                    mSendMessageListener.onSendMessage(localPath, timeValue);
//                }
//            }
//        });
//    }
//
//    private boolean isCancel;
//    private float mDownY;
//
//    public void setOnSendMessageListener(@NonNull OnSendMessageListener sendMessageListener) {
//
//        mSendMessageListener = sendMessageListener;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        @MotionEventActionType
//        int action = event.getAction();
//        switch (action) {
//
//            case MotionEvent.ACTION_DOWN:
//                mManager.startRecording();
//                mDownY = event.getY();
//                showVoiceDialog(RecordStatus.Send);
//                setText(R.string.message_btn_4);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveY = event.getY();
//                if (mDownY - moveY > 200) {
//                    isCancel = true;
//                    showVoiceDialog(RecordStatus.Cancel);
//                } else if (mDownY - moveY < 20) {
//                    isCancel = false;
//                    showVoiceDialog(RecordStatus.Send);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                setText(R.string.message_btn_3);
//                mRecordDialog.dismiss();
//                Log.d(TAG, "ACTION_UP: "+event.getY());
//                if (isCancel) {
//
//                    mManager.cancelRecording();
//                } else {
//
//                    int longTime = mManager.stopRecording();
//                    // TODO: 2018/8/2 根据时间来判断是否发送
//                    if (mSendMessageListener != null) {
//                        mSendMessageListener.onSendMessage(mManager.getResourceId(), longTime);
////                        Logger.d("record time" + longTime);
//                        Log.d(TAG, "onTouchEvent: "+longTime);
//                    }
//
//
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                mManager.cancelRecording();
//                mRecordDialog.dismiss();
//                break;
//        }
//        return true;
//    }
//
//    private void setValue(int value) {
//
//        switch (value) {
//            case 1:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal001);
//                break;
//            case 2:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal002);
//                break;
//            case 3:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal003);
//                break;
//            case 4:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal004);
//
//                break;
//            case 5:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal005);
//                break;
//            case 6:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal006);
//                break;
//            case 7:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal007);
//                break;
//            case 8:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal008);
//                break;
//            case 9:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal009);
//                break;
//            default:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal001);
//                break;
//        }
//    }
//
//
//    /**
//     * 0显示发送  1 显示取消
//     *
//     * @param flag 显示dialog状态
//     */
//    private void showVoiceDialog(@RecordStatus int flag) {
//        switch (flag) {
//            case RecordStatus.Cancel:
//                recrod_dialog_mic_content.setVisibility(View.GONE);
//                mIvRecVolume.setVisibility(View.GONE);
//                mIvCancelLayout.setVisibility(View.VISIBLE);
//                mTvRecordDialogTxt.setText(R.string.message_title_18);
//                break;
//            case RecordStatus.Send:
//                recrod_dialog_mic_content.setVisibility(View.VISIBLE);
//                mIvRecVolume.setVisibility(View.VISIBLE);
//                mIvCancelLayout.setVisibility(View.GONE);
//                mTvRecordDialogTxt.setText(R.string.message_title_16);
//                break;
//        }
//        mRecordDialog.show();
//    }
//
//    public interface OnSendMessageListener {
//
//        void onSendMessage(String path, int time);
//    }
//}
