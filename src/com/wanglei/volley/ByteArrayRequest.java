package com.shequtong.yishequ.volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * 
 * TODO ByteArrayRequest 重载  getBody() and getParams()
 * @author  Wanglei 
 * @data:  2015年6月4日 下午3:22:46 
 * @version:  V1.0
 */
class ByteArrayRequest extends Request<byte[]> {

	private final Listener<byte[]> mListener;

	private Object mPostBody = null;

	private HttpEntity httpEntity =null;
	
	private Map<String, String> mHeaders=null;
	
	public ByteArrayRequest(int method, String url, Object postBody, Listener<byte[]> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mPostBody = postBody;
		this.mListener = listener;

		if (this.mPostBody != null && this.mPostBody instanceof RequestParams) {// contains file
			this.httpEntity = ((RequestParams) this.mPostBody).getEntity();
		}
	}

	/**
	 * mPostBody is null or Map<String, String>, 执行这个方法！
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, String> getParams() throws AuthFailureError {
		if (this.httpEntity == null && this.mPostBody != null && this.mPostBody instanceof Map<?, ?>) {
			return ((Map<String, String>) this.mPostBody);//common Map<String, String>
		}
		return null;//process as json, xml or MultipartRequestParams
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		
		if(mHeaders != null){
//		mHeaders.put("Charset", "UTF-8");
//		mHeaders.put("Content-Type", "application/x-javascript");
//		mHeaders.put("Accept-Encoding", "gzip,deflate");
		return mHeaders;
		}else{
			Map<String, String> headers = super.getHeaders();
			if (null == headers || headers.equals(Collections.emptyMap())) {
				headers = new HashMap<String, String>();
			}
			return headers;
		}
		
	}

	@Override
	public String getBodyContentType() {
		if (httpEntity != null) {
			return httpEntity.getContentType().getValue();
		}
		return null;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (this.mPostBody != null && this.mPostBody instanceof String) {//process as json or xml
			String postString = (String) mPostBody;
			if (postString.length() != 0) {
				try {
					return postString.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				return null;
			}
		}
		if (this.httpEntity != null) {//process as MultipartRequestParams
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					httpEntity.writeTo(baos);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			return baos.toByteArray();
		}
		return super.getBody();// mPostBody is null or Map<String, String>
	}

	@Override
	protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
		return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
	}
	
	@Override
	protected void deliverResponse(byte[] response) {
		this.mListener.onResponse(response);
	}
	
	public void setCookie(String cookie){
		mHeaders = new HashMap<String, String>();
        mHeaders.put("Cookie", cookie);
    }
}