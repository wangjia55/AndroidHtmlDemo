package com.wqdata.net;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private WebView mWebView;
    private Button mButtonShow;
    private Button mButtonDismiss;
    private Button mButtonDialog;
    JsInterface jsInterface = new JsInterface();
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonShow = (Button) findViewById(R.id.button_one);
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //java 调用js的代码
                mWebView.loadUrl(String.format("javascript:showOrHide(1)"));
            }
        });

        mButtonDismiss = (Button) findViewById(R.id.button_two);
        mButtonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //java 调用js的代码
                mWebView.loadUrl(String.format("javascript:showOrHide(0)"));
            }
        });

        mButtonDialog = (Button) findViewById(R.id.button_three);
        mButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        mWebView.setWebViewClient(new webViewClient());

        jsInterface.setWvClientClickListener(
                new JsInterface.WebClientClickListener() {
            @Override
            public void wvHasClickEvent() {
                showDialog();
            }
        });
        mWebView.addJavascriptInterface(jsInterface,"jsInterface");
        mWebView.loadUrl("file:///android_asset/index.html");
        // ProcessWebString();
    }

    //Web视图
    private class webViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e(TAG, "onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "onPageFinished");
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


}
