package aop.demo.jetpack.android.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    private ImageView mIv;
    private AnimationDrawable mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mIv = findViewById(R.id.iv);
        mBackground = ((AnimationDrawable) mIv.getBackground());
    }

    public void start(View view) {
        mBackground.start();
    }

    public void stop(View view) {
        mBackground.stop();
    }
}
