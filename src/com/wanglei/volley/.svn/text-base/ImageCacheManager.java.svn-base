package com.shequtong.yishequ.volley;

import com.shequtong.yishequ.MyApplication;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * 
 * TODO 图片下载处理
 * @author  Wanglei 
 * @data:  2015年6月4日 下午3:28:20 
 * @version:  V1.0
 */
public class ImageCacheManager {

    // 取运行内存阈值的1/8作为图片缓存
    private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;
    
    private static BitmapLruCache mBitmapLruCache = new BitmapLruCache(MEM_CACHE_SIZE);

    private static ImageLoader mImageLoader = new ImageLoader(RequestManager.mRequestQueue, mBitmapLruCache);

    private ImageCacheManager() {

    }
    
    /**
     * 默认大小图片
     * @param requestUrl 请求的url
     * @param imageListener 
     * @return
     */
    public static ImageLoader.ImageContainer loadImage(String requestUrl, ImageLoader.ImageListener imageListener) {
        return loadImage(requestUrl, imageListener, 0, 0);
    }
    
    /**
     * 设置过的图片大小
     * @param requestUrl 请求url
     * @param imageListener 
     * @param maxWidth 最大宽度
     * @param maxHeight 最大高度
     * @return
     */
    public static ImageLoader.ImageContainer loadImage(String requestUrl, ImageLoader.ImageListener imageListener, int maxWidth, int maxHeight) {
        return mImageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
    }
    
    /**
     * 加载Image
     * @param view 显示的view
     * @param defaultImageDrawable 默认的view 
     * @param errorImageDrawable 错误的view
     * @return
     */
    public static ImageLoader.ImageListener getImageListener(final ImageView view, final Drawable defaultImageDrawable, final Drawable errorImageDrawable) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageDrawable != null) {
                    view.setImageDrawable(errorImageDrawable);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    if (!isImmediate && defaultImageDrawable != null) {
                        TransitionDrawable transitionDrawable = new TransitionDrawable(
                                new Drawable[]{
                                        defaultImageDrawable,
                                        new BitmapDrawable(MyApplication.getContext().getResources(),
                                                response.getBitmap())
                                }
                        );
                        transitionDrawable.setCrossFadeEnabled(true);
                        view.setImageDrawable(transitionDrawable);
                        transitionDrawable.startTransition(100);
                    } else {
                        view.setImageBitmap(response.getBitmap());
                    }
                } else if (defaultImageDrawable != null) {
                    view.setImageDrawable(defaultImageDrawable);
                }
            }
        };
    }
}
