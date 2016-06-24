package com.example.administrator.cbkproject.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/6/13.
 * 内存缓存
 *
 */
public class MemoryCache {
    private LruCache<String,Bitmap> lruCache;
    public MemoryCache(){
        int maxSize;
        //1;获得本机的内存大小。
        long maxMemory = Runtime.getRuntime().maxMemory();

        //将内存的一部分交于管理。
        maxSize = (int) (maxMemory/12);
        lruCache = new LruCache<String,Bitmap>(maxSize){
            //返回一张图片的字节总数
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }
    //将图片放到内存中。
    public void putImage(String path,Bitmap bitmap){

        lruCache.put(path,bitmap);
    }
    //从内存中取出图片。
    public Bitmap getImage(String path){
        return lruCache.get(path);
    }
}
