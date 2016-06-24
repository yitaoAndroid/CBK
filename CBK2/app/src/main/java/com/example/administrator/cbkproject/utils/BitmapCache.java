package com.example.administrator.cbkproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/13.
 */
public class BitmapCache {
    private LocalCache localCache;
    private MemoryCache memoryCache;
    private NetCache netCache;
    private static BitmapCache bitmapCache = new BitmapCache();

    public static BitmapCache getInstance() {
        return bitmapCache;
    }

    private BitmapCache() {
        localCache = new LocalCache();
        memoryCache = new MemoryCache();
        netCache = new NetCache(localCache, memoryCache);
    }

    //从缓存中取出图片。
    public void setImageViewFromCache(String path, ImageView imageView) {
        //1:先从内存中取。
        Bitmap bitmap = memoryCache.getImage(path);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            System.out.println("从内存中获取");
            return;
        }
        String filename = null;
        //http://s1.sns.maimaicha.com/images/2016/01/06/20160106110314_22333_suolue3.jpg
        filename = path.substring(path.lastIndexOf("/") + 1);

        //对地址进行处理。
        //2：从本地中取
        bitmap = localCache.getBitmap(filename);

        if (bitmap != null) {
            //获得压缩的图片。
            bitmap = decodeBitmap(filename, imageView);
            //保存到内存中。
            memoryCache.putImage(path, bitmap);
            imageView.setImageBitmap(bitmap);
            System.out.println("从local中获取");
            return;
        }
        //3：网络请求。
        netCache.bitmapFromNet(imageView,path );

    }

    private Bitmap decodeBitmap(String filename, ImageView imageView) {
        Bitmap bitmap = null;
        //从sdk读取并且放到内存中。--将图片进行压缩。
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//量边。不分配空间来存储。
        //获得图片的宽和高---第一次采样
        BitmapFactory.decodeFile(LocalCache.derictory + "/" + filename, options);
        int width = options.outWidth;
        int height = options.outHeight;
        //获得ImageView的宽度和高度。
        //  int image_width = imageView.getWidth();
        int image_width = 100;
        System.out.println("宽:" + image_width);
        // int image_height = imageView.getHeight();
        int image_height = 100;
        System.out.println("高:" + image_height);
        //计算比例。
        int sample = Math.min(width / image_width, height / image_height);
        options.inJustDecodeBounds = false;//要分配内存。
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //第二次采样。
        bitmap = BitmapFactory.decodeFile(LocalCache.derictory + "/" + filename, options);
        System.out.println(bitmap.getByteCount() + "压缩后的");
        return  bitmap;
    }
}
