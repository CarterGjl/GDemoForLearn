package aop.demo.jetpack.android.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        AnimationDrawable background = (AnimationDrawable) textView.getBackground();
        initView();
    }
    private WebView mWv;

    private static final String TAG = "MainActivity";

    private void initView() {

        mWv = findViewById(R.id.wv);

        WebSettings webSettings = mWv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //本地存储
        webSettings.setDomStorageEnabled(true);
        //自动播放视频
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        mWv.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                MainActivity.this.startActivity(intent);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }


        });

        mWv.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                //supper 里面禁用了权限。。。
                
//                super.onPermissionRequest(request);
                request.grant(request.getResources());
            }



        });
        mWv.loadUrl("https://v2demo.mediasoup.org/?roomId=a0kwjond");
    }
}
