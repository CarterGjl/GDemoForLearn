package aop.demo.jetpack.android.myapplication.view;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import aop.demo.jetpack.android.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShouldInterceptListener} interface
 * to handle interaction events.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mUrl;
    private String mParam2;

    private ShouldInterceptListener mListener;
    private WebView mWv;

    public WebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mWv = view.findViewById(R.id.wv);

        mWv.setWebContentsDebuggingEnabled(true);
        WebSettings webSettings = mWv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mWv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                MainActivity.this.startActivity(intent);
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                intent.putExtra("url", request.getUrl());
//                startActivity(intent);
                if (mListener != null) {
                    mListener.openInNewPage(request.getUrl().toString());
                }
//                return super.shouldOverrideUrlLoading(view, request);
                return true;
            }


        });

        mWv.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                super.onPermissionRequest(request);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }


        });
//        mWv.loadUrl("https://v2demo.mediasoup.org/?roomId=a0kwjond");
        mWv.loadUrl(mUrl);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setListener(ShouldInterceptListener listener) {
        mListener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ShouldInterceptListener {
        void openInNewPage(String url);
    }
}
