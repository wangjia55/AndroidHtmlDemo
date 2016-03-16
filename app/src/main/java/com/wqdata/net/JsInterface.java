package com.wqdata.net;

import android.webkit.JavascriptInterface;

/**
 * Created by wangjia on 2016/3/16.
 */
public class JsInterface {
    public interface WebClientClickListener {
         void wvHasClickEvent();
    }

    private WebClientClickListener wvEnventPro = null;

    public void setWvClientClickListener(WebClientClickListener listener) {
        wvEnventPro = listener;
    }

    //注意一定要加上此标注才能允许js调用java代码，为了保证安全
    @JavascriptInterface
    public void javaFunction() {
        if(wvEnventPro != null)
            wvEnventPro.wvHasClickEvent();
    }
}
