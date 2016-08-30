package com.shequtong.yishequ.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * 
 * TODO 添加Cookie
 * @author  Wanglei 
 * @data:  2015年6月10日 上午11:06:56 
 * @version:  V1.0
 */
public class CookieRequest extends JsonObjectRequest {

    private Map mHeaders=new HashMap(1);

    public CookieRequest(String url, JSONObject jsonRequest, Listener listener,
                                   ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public CookieRequest(int method, String url, JSONObject jsonRequest, Listener listener,
                                   ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }
    
    public void setCookie(String cookie){
        mHeaders.put("Cookie", cookie);
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        return mHeaders;
    }

}
