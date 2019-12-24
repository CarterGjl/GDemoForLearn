package aop.demo.jetpack.android.myapplication.viewmodel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class InterceptdataService extends Service {
    public InterceptdataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
