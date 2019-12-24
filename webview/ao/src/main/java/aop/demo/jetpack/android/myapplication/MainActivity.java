package aop.demo.jetpack.android.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Arrays;
import java.util.List;

import aop.demo.jetpack.android.myapplication.broadcast.RxBroadcastReceiver;
import aop.demo.jetpack.android.myapplication.storage.Config;
import aop.demo.jetpack.android.myapplication.view.WebViewFragment;
import aop.demo.jetpack.android.myapplication.viewmodel.IntercepdataViewmodel;
import aop.demo.jetpack.android.myapplication.viewmodel.InterceptData;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.net.ConnectivityManager.TYPE_WIFI;
import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

public class MainActivity extends AppCompatActivity {

    private String mUrl;

    public String getUrl() {


        return mUrl == null ? "" : mUrl;
    }

    private InterceptData mInterceptData;
    private FrameLayout mFrameLayout;
    private Uri mUri;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private WebChromeClient.CustomViewCallback mCallback;
    private RxPermissions mRxPermissions;
    private AlertDialog mAlertDialog;

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRxPermissions = new RxPermissions(this);

        Disposable subscribe = mRxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(aBoolean -> {
                    if (aBoolean) {

                    } else {
                        Toast.makeText(MainActivity.this, "Permission deny",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        mFrameLayout = findViewById(R.id.fl_content);
        List<String> list1 = Arrays.asList("/ot/ssohandler.html",
                "/order/status.html",
                "/order/confirm.html",
                "/aculearn-idm/v4/opr/studioclient.asp",
                "/aculearn-idm/v4/opr/studioclient.asp",
                "/ot/video/player.html");
        mInterceptData = new InterceptData(1, list1);


        IntercepdataViewmodel intercepdataViewmodel = ViewModelProviders.of(this).get(IntercepdataViewmodel.class);
        getLifecycle().addObserver(intercepdataViewmodel);
        InterceptData value = intercepdataViewmodel.getInterceptLivedata().getValue();
        if (Config.getInstance(this).getIntercepData() != null) {
            mInterceptData = Config.getInstance(this).getIntercepData();
        }
        if (value != null) {
            mInterceptData = value;
        }
        intercepdataViewmodel.getInterceptLivedata().observe(this, interceptData -> {
            mInterceptData = interceptData;
            Config.getInstance(this).saveInterceptData(interceptData);
        });


        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        if (mUrl == null) {
//            mUrl = "https://online.acucom.net:6443/ot/index.html";
//            mUrl = "http://online.lzr.com/ot/index.html";
            mUrl = BuildConfig.API_HOST + "/ot/index.html";
        }


        mUri = Uri.parse(mUrl);

//        if (mUri.getHost() != null && mUri.getHost().startsWith("online.")) {
//            MainActivity activityHashMap = Runtime.getInstance().getActivityHashMap();
//            if (activityHashMap != null) {
//                String url = activityHashMap.getUrl();
//                String host = Uri.parse(url).getHost();
//                if (host != null && host.startsWith("class.")) {
//                    activityHashMap.finish();
//                }
//            }
//        }
//        Runtime.getInstance().setActivityHashMap(this);
//        TextView textView = new TextView(this);
//        AnimationDrawable background = (AnimationDrawable) textView.getBackground();
        initView();
    }

    private void addFragment(WebViewFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }

    private WebView mWv;
    private ProgressBar mPb;

    @Override
    protected void onPause() {
        super.onPause();
        if (mWv != null) {
            mWv.onPause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mWv != null) {
//            mWv.reload();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWv != null) {
            mWv.onResume();
        }
    }

    private SwipeRefreshLayout mSrl;

    private FrameLayout mFlFullScreen;

    private static final String TAG = "MainActivity";

    private void initView() {


        mSrl = findViewById(R.id.srl);

        mFlFullScreen = findViewById(R.id.fl_full_screen);

        mWv = findViewById(R.id.wv);


        mPb = findViewById(R.id.pb);
        mSrl.setOnRefreshListener(() -> mWv.reload());
        mSrl.setOnChildScrollUpCallback((parent, child) -> mWv.getScrollY() > 0);
        mWv.setWebContentsDebuggingEnabled(true);
        WebSettings webSettings = mWv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        //开启 数据库 存储功能
        webSettings.setDatabaseEnabled(true);
        //开启 应用缓存 功能
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getPath());
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


        webSettings.setMediaPlaybackRequiresUserGesture(false);
        mWv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                MainActivity.this.startActivity(intent);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    if (url.equals(mUrl) ||
                            url.equals("about:blank")) {
                        return false;
                    }

                    WebView.HitTestResult hitTestResult = view.getHitTestResult();
                    int type = hitTestResult.getType();
                    if (type == WebView.HitTestResult.UNKNOWN_TYPE) {
                        return false;
                    }
                    if (url.endsWith(".pdf")) {
                        String data = "<iframe src='http://docs.google" +
                                ".com/gview?embedded=true&url=" + url + "'" + " width='100%' " +
                                "height='100%' style='border: none;'></iframe>";
                        mWv.loadData(data, "text/html", "UTF-8");
                        return true;
                    }
                    Uri uri = Uri.parse(url);

                    if (mInterceptData.getData().contains(uri.getPath())) {
                        return false;
                    }

                    if (url.endsWith("errornew.html") || url.endsWith("error.html")) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                    String host = uri.getHost();
                    if (host != null) {
                        boolean tc = host.startsWith("class.");
                        boolean tc1 = host.startsWith("odev.");
                        if (tc || tc1) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("url", url);
                            Global.isFade = false;
                            startActivity(intent);
                        }
                    }

                    if (uri.getHost() != null && uri.getHost().startsWith(
                            "online.")) {


                        if ((uri.getHost() + uri.getPath()).equals(mUri.getHost() + mUri.getPath())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        } else {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        }
                        return true;
                    }
                }


                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d(TAG, "shouldOverrideUrlLoading: " + request.getUrl().toString());
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    WebView.HitTestResult hitTestResult = view.getHitTestResult();
                    int type = hitTestResult.getType();
                    if (type == WebView.HitTestResult.UNKNOWN_TYPE) {
                        return false;
                    }
                    if (request.getUrl().toString().equals(mUrl) ||
                            request.getUrl().toString().equals("about:blank")) {
                        return false;
                    }

                    if (request.getUrl().toString().endsWith(".pdf")) {
                        String data = "<iframe src='http://docs.google" +
                                ".com/gview?embedded=true&url=" + request.getUrl().toString() + "'" + " width='100%' " +
                                "height='100%' style='border: none;'></iframe>";
                        mWv.loadData(data, "text/html", "UTF-8");
                        return true;
                    }
                    if (mInterceptData.getData().contains(request.getUrl().getPath())) {
                        return false;
                    }
                    if (request.getUrl().toString().endsWith("errornew.html") || request.getUrl().toString().endsWith("error.html")) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra("url", request.getUrl().toString());
                        startActivity(intent);
                        finish();
                        return true;
                    }
                    String host = request.getUrl().getHost();
                    if (host != null) {
                        boolean tc = host.startsWith("class.");
                        boolean tc1 = host.startsWith("odev.");
                        if (tc || tc1) {

                            mUri = request.getUrl();
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            intent.putExtra("url", request.getUrl().toString());
//                            startActivity(intent);
                            return false;
                        }
                    }

                    if (request.getUrl().getHost() != null && request.getUrl().getHost().startsWith(
                            "online.")) {


                        if ((request.getUrl().getHost() + request.getUrl().getPath()).equals(mUri.getHost() + mUri.getPath())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("url", request.getUrl().toString());
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        } else {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("url", request.getUrl().toString());

                            startActivity(intent);
                            if (mUri.getHost() != null && mUri.getHost().startsWith("class.")) {
                                finish();
                            }
                        }
                        return true;
                    }

                }


                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWv.evaluateJavascript("window.mobile=true", value -> Log.d(TAG, "onReceiveValue: " + value));

                String curnet;
                switch (getConnectedType(MainActivity.this)) {
                    case TYPE_MOBILE:
                        curnet = "mobileNetwork";
//                                    Toast.makeText(context, "正在使用2G/3G/4G网络", Toast.LENGTH_SHORT).show();
                        break;
                    case TYPE_WIFI:
                        curnet = "wifi";
//                                    Toast.makeText(context, "正在使用wifi上网", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        curnet = "disconnected";
                        break;
                    default:
                        curnet = "mobileNetwork";
                        break;
                }


                postCurNet(curnet);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSrl.setRefreshing(false);
                mWv.setVisibility(View.VISIBLE);
                mPb.setVisibility(View.GONE);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mWv.setVisibility(View.VISIBLE);
                mPb.setVisibility(View.GONE);
//                mAlertDialog = new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Error")
//                        .setMessage("Load resource error")
//                        .setNegativeButton("No", (dialog, which) -> mAlertDialog.dismiss())
//                        .setPositiveButton("Reload", (dialog, which) -> view.reload()).create();
//                mAlertDialog.show();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

            }
        });

        mWv.setWebChromeClient(new WebChromeClient() {


            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                if (BuildConfig.DEBUG) {
                    mAlertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error from js")
                            .setMessage("message::" + message)
                            .setNegativeButton("No", (dialog, which) -> mAlertDialog.dismiss())
                            .setPositiveButton("reload", (dialog, which) -> view.reload()).create();
                    mAlertDialog.show();
                }

                return true;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                mCallback = callback;
                fullScreen();
                mWv.setVisibility(View.GONE);
                mFlFullScreen.setVisibility(View.VISIBLE);
                mFlFullScreen.addView(view);
                super.onShowCustomView(view, callback);

            }


            @Override
            public void onHideCustomView() {

                fullScreen();
                mWv.setVisibility(View.VISIBLE);
                mFlFullScreen.setVisibility(View.GONE);
                mFlFullScreen.removeAllViews();
                super.onHideCustomView();
            }

            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d(TAG, "onPermissionRequest: " + request.getResources());

                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    request.grant(request.getResources());
                }
//                super.onPermissionRequest(request);

            }


        });
//        mWv.loadUrl("https://v2demo.mediasoup.org/?roomId=a0kwjond");
        mWv.loadUrl(mUrl);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        Disposable subscribe = RxBroadcastReceiver.create(this, intentFilter)
                .subscribe(intent -> {
                    String curnet;
                    ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isAvailable()) {
                        switch (networkInfo.getType()) {
                            case TYPE_MOBILE:
                                curnet = "mobileNetwork";
//                                    Toast.makeText(context, "正在使用2G/3G/4G网络", Toast.LENGTH_SHORT).show();
                                break;
                            case TYPE_WIFI:
                                curnet = "wifi";
//                                    Toast.makeText(context, "正在使用wifi上网", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                curnet = "mobileNetwork";
                                break;
                        }
                    } else {
                        curnet = "disconnected";
//                            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
                    }

                    postCurNet(curnet);
                });
        mCompositeDisposable.add(subscribe);
    }

    private void postCurNet(String curnet) {
        mWv.evaluateJavascript("window.netState(" + curnet + ")", value -> Log.d(TAG, "onReceiveValue: " + value));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Log.i("ToVmp", "横屏");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Log.i("ToVmp", "竖屏");
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mCallback != null) {
            mCallback.onCustomViewHidden();
        }
        if (mWv.canGoBack()) {
            mWv.goBack();
        } else {
            super.onBackPressed();

        }
    }
}
