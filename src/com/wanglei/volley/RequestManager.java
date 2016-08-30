package com.shequtong.yishequ.volley;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.shequtong.yishequ.MyApplication;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * 
 * TODO  get post 
 * @author  Wanglei 
 * @data:  2015年6月4日 下午3:35:05 
 * @version:  V1.0
 */
public class RequestManager {
	
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(MyApplication.getContext());

    private RequestManager() {
        // no instances
    }

    /**
     * get
     * @param url
     * @param tag
     * @param listener
     */
    public static void get(String url, Object tag, RequestListener listener) {
    	get(url, tag, null, listener);
    }

    /**
     * get
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public static void get(String url, Object tag, RequestParams params, RequestListener listener) {
    	ByteArrayRequest request = new ByteArrayRequest(Method.GET, url, null, responseListener(listener), responseError(listener));
    	addRequest(request , tag);
    }

    /**
     * post
     * @param url
     * @param tag
     * @param listener
     */
    public static void post(String url, Object tag, RequestListener listener) {
    	post(url, tag, null, listener);
    }
    
    /**
     * post
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public static void post(String url, Object tag, RequestParams params, RequestListener listener) {
    	ByteArrayRequest request = new ByteArrayRequest(Method.POST, url, params, responseListener(listener), responseError(listener));
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//设置请求超时时间
        addRequest(request , tag);
    }
    
    /**
     * 
     * TODO post
     * @param url
     * @param tag
     * @param params
     * @param listener
     * @param cookie
     * @throw 
     * @return void
     */
    public static void post(String url, Object tag, RequestParams params, RequestListener listener,String Cookies) {
    	ByteArrayRequest request = new ByteArrayRequest(Method.POST, url, params, responseListener(listener), responseError(listener));
    		request.setCookie(Cookies);
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//设置请求超时时间
    	addRequest(request , tag);
    }
    
    /**
     * 
     * TODO post 发送多个图片
     * @param url
     * @param tag
     * @param filePartName
     * @param files
     * @param params
     * @param listener
     * @param errorListener
     * @throw 
     * @return void
     */
    public static void post(String url,Object tag,String filePartName,List<File> files,Map<String, String> params,Response.Listener<String> listener, Response.ErrorListener errorListener){
    	MultipartRequest multipartRequest = new  MultipartRequest(url,  errorListener, listener,  filePartName,  files,  params);
    	addRequest(multipartRequest, tag);
    }
    
    /**
     * 
     * TODO post 发送多个图片
     * @param url
     * @param tag
     * @param filePartName
     * @param files
     * @param params
     * @param listener
     * @param errorListener
     * @throw 
     * @return void
     */
    public static void post(String url,Object tag,String filePartName,List<File> files,Map<String, String> params,Response.Listener<String> listener, Response.ErrorListener errorListener,String Cookies){
    	MultipartRequest multipartRequest = new  MultipartRequest(url,  errorListener, listener,  filePartName,  files,  params);
    	multipartRequest.setCookie(Cookies);
    	addRequest(multipartRequest, tag);
    }
    
    /**
     * 
     * TODO 添加到请求列队
     * @param request
     * @param tag
     * @throw 
     * @return void
     */
    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }
    
    /**
     * 
     * TODO request取消操作
     * @param tag
     * @throw 
     * @return void
     */
    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
    
    
    /**
     * 成功消息监听
     * @param l
     * @return
     */
    protected static Response.Listener<byte[]> responseListener(final RequestListener l) {
    	return new Response.Listener<byte[]>() {
			@Override
			public void onResponse(byte[] arg0) {
				String data = null;
				try {
					data = new String(arg0, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				l.requestSuccess(data);
			}
		};
    }
    
    /**
     * 返回错误监听
     * @param l
     * @return
     */
    protected static Response.ErrorListener responseError(final RequestListener l) {
    	return new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError e) {
				l.requestError(e);
			}
		};
    }
}
