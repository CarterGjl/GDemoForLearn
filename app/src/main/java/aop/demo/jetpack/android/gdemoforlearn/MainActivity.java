package aop.demo.jetpack.android.gdemoforlearn;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import aop.demo.jetpack.android.gdemoforlearn.view.KProgressHUD;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRe;
    private UserAdapter mUserAdapter;
    private List<User> mUsers1;
    private List<User> mUsers;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private KProgressHUD mShow;

    private static final String TAG = "MainActivity";
    @TargetApi(28)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();


        decorView.post(()->{

            DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
            if (displayCutout != null) {
                Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());

                List<Rect> rects = displayCutout.getBoundingRects();
                if (rects == null || rects.size() == 0) {
                    Log.e("TAG", "不是刘海屏");
                } else {
                    Log.e("TAG", "刘海屏数量:" + rects.size());
                    for (Rect rect : rects) {
                        Log.e("TAG", "刘海屏区域：" + rect);
                    }
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
            getNotchParams();
        }

        setUpWindowAnimations();
        setContentView(R.layout.activity_main);
//        getNotchParams();

//        mShow = KProgressHUD.create(this, KProgressHUD.Style.BAR_DETERMINATE)
//                .setLabel("正在加载。。。")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.3f)
//                .setDimAmount(8)
//                .setCancellable(dialog -> {
//
//                })
//                .show();
        mUsers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mUsers.add(new User(String.valueOf(i), "用户" + i, i + 20,false));
        }
        mUsers1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mUsers1.add(new User(String.valueOf(i), "用户" + i, i % 3 == 0 ? i + 10: i + 20,true));
        }
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRe = findViewById(R.id.re);
        mUserAdapter = new UserAdapter();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRe.setLayoutManager(mLinearLayoutManager);
        mRe.setAdapter(mUserAdapter);
        mUserAdapter.submitList(mUsers);
//        Locale aDefault = Locale.getDefault();

        String language = Locale.getDefault().getLanguage();
        Log.d(TAG, "onCreate: "+language);

//        Log.d(TAG, "onCreate: "+aDefault);
    }

    private void setUpWindowAnimations() {
        Transition inflateTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade_activity);
        getWindow().setEnterTransition(inflateTransition);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(transition);
    }


    private boolean is = false;
    public void clioc(View view) {

        if (is){
            mUserAdapter.submitList(mUsers);
            mRe.setLayoutManager(mGridLayoutManager);
            is = false;
//            mShow.dismiss();
        }else {
            is = true;
            mRe.setLayoutManager(mLinearLayoutManager);
            mUserAdapter.submitList(mUsers1);
//            mShow.show();
        }

    }
}
