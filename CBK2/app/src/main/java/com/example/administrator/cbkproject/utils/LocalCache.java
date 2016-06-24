package com.example.administrator.cbkproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/6/13.
 * 本地缓存
 */
public class LocalCache {
    public static final String derictory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cbk";

    public LocalCache() {

    }

    //从本地中取出图片---根据网址
    public Bitmap getBitmap(String imageName) {
        System.out.println(imageName + "---localCache");
        Bitmap bitmap = null;
        File file = new File(derictory, imageName);
        if (file.exists()) {
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    //将图片放到sd卡中。---图片对象和网址。可以将网址的某一部分作为图片文件的名字。
    public void setBitmap(String imageName, Bitmap bitmap) {
        File file = new File(derictory, imageName);
        //1：判断父目录是否存在，如果不存在则需要创建。
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        /*
        100表示没压缩。
        30,则表示压缩了70%.
         */
        System.out.println(parentFile.getAbsolutePath() + "---父目录的路径");
        try {
            //放到内存中后，字节数仍然不变。
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
