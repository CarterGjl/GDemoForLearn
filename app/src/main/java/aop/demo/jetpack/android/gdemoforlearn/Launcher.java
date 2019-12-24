package aop.demo.jetpack.android.gdemoforlearn;

import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.session.MediaSession;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.media.session.MediaButtonReceiver;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import aop.demo.jetpack.android.RxBroadcastReceiver;
import aop.demo.jetpack.android.gdemoforlearn.view.EditButton;
import io.reactivex.functions.Consumer;

import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class Launcher extends AppCompatActivity {

    private static final String TAG = "Launcher";
    private FrameLayout mRoot;
    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        mRoot = findViewById(R.id.root);
        mTv = findViewById(R.id.tv);

        //取消按钮

        mTv.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mTv.setOnClickListener(new View.OnClickListener() {
            @CheckLogin
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
            }
        });
        test();
//        audioManager.registerMediaButtonEventReceiver();

//        MediaSession abc = new MediaSession(this, "abc");
//        MediaSessionCompat fdasdfas = new MediaSessionCompat(this, "fdasdfas");
//        fdasdfas.setCallback(new MediaSessionCompat.Callback() {
//
//            @Override
//            public void onPlay() {
//                super.onPlay();
//                Toast.makeText(Launcher.this, "paly", Toast.LENGTH_SHORT).show();
//            }
//
//
//            @Override
//            public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
//
//                KeyEvent keyEvent =
//                        (KeyEvent) mediaButtonEvent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
//                int keyCode = keyEvent.getKeyCode();
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_MEDIA_NEXT:
//                        Toast.makeText(Launcher.this, "KEYCODE_MEDIA_NEXT", Toast.LENGTH_SHORT).show();
//                        break;
//                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
//                        Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PREVIOUS", Toast.LENGTH_SHORT).show();
//
//                        break;
//
//                    case KeyEvent.KEYCODE_MEDIA_PAUSE:
//                        Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PAUSE", Toast.LENGTH_SHORT).show();
//
//                        break;
//
//                }
//                return super.onMediaButtonEvent(mediaButtonEvent);
//            }
//        });
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_MEDIA_BUTTON);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1, intent,PendingIntent.FLAG_ONE_SHOT);
//        abc.setMediaButtonReceiver(pendingIntent);
//
//
//        MediaButtonReceiver mediaButtonReceiver = new MediaButtonReceiver();
//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);
//
//        MediaController mediaController = new MediaController(this);
//        registerReceiver(mediaButtonReceiver,intentFilter);
//
//        RxBroadcastReceiver.create(this,intentFilter)
//                .subscribe(new Consumer<Intent>() {
//                    @Override
//                    public void accept(Intent intent) throws Exception {
//
//                        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
//                        Log.d(TAG, "accept: " + keyEvent);
//
//
//                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
//
//                            int keyCode = keyEvent.getKeyCode();
//                            switch (keyCode) {
//                                case KeyEvent.KEYCODE_MEDIA_NEXT:
//                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_NEXT", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
//                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PREVIOUS", Toast.LENGTH_SHORT).show();
//
//                                    break;
//
//                                case KeyEvent.KEYCODE_MEDIA_PAUSE:
//                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PAUSE", Toast.LENGTH_SHORT).show();
//
//                                    break;
//
//                            }
//                        }
//                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
//
//                            int keyCode = keyEvent.getKeyCode();
//                            switch (keyCode) {
//                                case KeyEvent.KEYCODE_MEDIA_NEXT:
//                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_NEXT", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
//                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PREVIOUS", Toast.LENGTH_SHORT).show();
//
//                                    break;
//
//                                case KeyEvent.KEYCODE_MEDIA_PAUSE:
//                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PAUSE", Toast.LENGTH_SHORT).show();
//
//                                    break;
//
//                            }
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.e(TAG, "accept: ",throwable );
//                    }
//                });

        mRoot = findViewById(R.id.root);

        EditButton editButton = new EditButton(this,  200);
        final FrameLayout.LayoutParams btn_cancel_param =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        btn_cancel_param.gravity = Gravity.CENTER;
        editButton.setLayoutParams(btn_cancel_param);
        mRoot.addView(editButton);
//        Log.d(TAG, "onCreate: "+ Locale.getDefault());
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            Navigation.findNavController(Launcher.this,R.id.nv).navigate(R.id.blankFragment2);
//        });

        TextView textView = new TextView(this);
        textView.setText("fdasfdasfasf ");
    }

    private void test() {
        ComponentName componentName = new ComponentName(getPackageName(), BlutoothRe.class.getName());
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "test", componentName, null);
        mediaSessionCompat.setCallback(new MediaSessionCompat.Callback() {

            @Override
            public void onPlay() {
                super.onPlay();
                Log.d(TAG, "onPlay: ");
            }

            @Override
            public void onStop() {
                super.onStop();
                Log.d(TAG, "onStop: ");
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                Log.d(TAG, "onSkipToNext: ");
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                Log.d(TAG, "onSkipToPrevious: ");
            }

            @Override
            public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
                        KeyEvent keyEvent = (KeyEvent) mediaButtonEvent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);


                            int keyCode = keyEvent.getKeyCode();
                            switch (keyCode) {
                                case KeyEvent.KEYCODE_MEDIA_NEXT:
                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_NEXT", Toast.LENGTH_SHORT).show();
                                    break;
                                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PREVIOUS", Toast.LENGTH_SHORT).show();

                                    break;

                                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PAUSE", Toast.LENGTH_SHORT).show();

                                    break;

                            }

                return true;
            }
        });
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS);

        mediaSessionCompat.setActive(true);

    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        return Navigation.findNavController(this,R.id.nv).navigateUp();
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        String s = parseKeyCode(keyCode);
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        return super.onKeyDown(keyCode, event);
    }

    public String parseKeyCode(int keyCode) {
        String ret = "";
        switch (keyCode) {
            case KeyEvent.KEYCODE_POWER:
                // 监控/拦截/屏蔽电源键 这里拦截不了
                ret = "get Key KEYCODE_POWER(KeyCode:" + keyCode + ")";
                break;
            case KeyEvent.KEYCODE_RIGHT_BRACKET:
                // 监控/拦截/屏蔽返回键
                ret = "get Key KEYCODE_RIGHT_BRACKET";
                break;
            case KeyEvent.KEYCODE_MENU:
                // 监控/拦截菜单键
                ret = "get Key KEYCODE_MENU";
                break;
            case KeyEvent.KEYCODE_HOME:
                // 由于Home键为系统键，此处不能捕获
                ret = "get Key KEYCODE_HOME";
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                // 监控/拦截/屏蔽上方向键
                ret = "get Key KEYCODE_DPAD_UP";
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // 监控/拦截/屏蔽左方向键
                ret = "get Key KEYCODE_DPAD_LEFT";
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // 监控/拦截/屏蔽右方向键
                ret = "get Key KEYCODE_DPAD_RIGHT";
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                // 监控/拦截/屏蔽下方向键
                ret = "get Key KEYCODE_DPAD_DOWN";
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                // 监控/拦截/屏蔽中方向键
                ret = "get Key KEYCODE_DPAD_CENTER";
                break;
            case KeyEvent.FLAG_KEEP_TOUCH_MODE:
                // 监控/拦截/屏蔽长按
                ret = "get Key FLAG_KEEP_TOUCH_MODE";
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                // 监控/拦截/屏蔽下方向键
                ret = "get Key KEYCODE_VOLUME_DOWN(KeyCode:" + keyCode + ")";
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                // 监控/拦截/屏蔽中方向键
                ret = "get Key KEYCODE_VOLUME_UP(KeyCode:" + keyCode + ")";
                break;
            case 220:
                // case KeyEvent.KEYCODE_BRIGHTNESS_DOWN:
                // 监控/拦截/屏蔽亮度减键
                ret = "get Key KEYCODE_BRIGHTNESS_DOWN(KeyCode:" + keyCode + ")";
                break;
            case 221:
                // case KeyEvent.KEYCODE_BRIGHTNESS_UP:
                // 监控/拦截/屏蔽亮度加键
                ret = "get Key KEYCODE_BRIGHTNESS_UP(KeyCode:" + keyCode + ")";
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                ret = "get Key KEYCODE_MEDIA_PLAY(KeyCode:" + keyCode + ")";
                break;
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                ret = "get Key KEYCODE_MEDIA_PAUSE(KeyCode:" + keyCode + ")";
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                ret = "get Key KEYCODE_MEDIA_PREVIOUS(KeyCode:" + keyCode + ")";
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                ret = "get Key KEYCODE_MEDIA_PLAY_PAUSE(KeyCode:" + keyCode + ")";
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                ret = "get Key KEYCODE_MEDIA_NEXT(KeyCode:" + keyCode + ")";
                break;
            default:
                ret = "keyCode: "
                        + keyCode
                        + " (http://developer.android.com/reference/android/view/KeyEvent.html)";
                break;
        }
        return ret;
    }



    public class BlutoothRe extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);


            int keyCode = keyEvent.getKeyCode();
            switch (keyCode) {
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_NEXT", Toast.LENGTH_SHORT).show();
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PREVIOUS", Toast.LENGTH_SHORT).show();

                    break;

                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    Toast.makeText(Launcher.this, "KEYCODE_MEDIA_PAUSE", Toast.LENGTH_SHORT).show();

                    break;

            }

        }
    }
}
