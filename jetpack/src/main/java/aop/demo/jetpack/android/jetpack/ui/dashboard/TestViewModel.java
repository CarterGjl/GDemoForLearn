package aop.demo.jetpack.android.jetpack.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {


    private MutableLiveData<Integer> mIntegerMutableLiveData;
    public TestViewModel() {
        if (mIntegerMutableLiveData == null) {
            mIntegerMutableLiveData = new MutableLiveData<>();
        }
    }

    public MutableLiveData<Integer> getIntegerMutableLiveData() {

        return mIntegerMutableLiveData;
    }
}
