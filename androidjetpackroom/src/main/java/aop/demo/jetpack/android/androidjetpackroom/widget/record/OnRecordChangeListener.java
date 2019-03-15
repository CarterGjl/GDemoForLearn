package aop.demo.jetpack.android.androidjetpackroom.widget.record;

public interface OnRecordChangeListener {

    void onVolumChanged(int voiceValue);

    void onRecordTimeChanged(int timeValue, String localPath);
}
