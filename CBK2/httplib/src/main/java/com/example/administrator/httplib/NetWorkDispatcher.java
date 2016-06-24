package com.example.administrator.httplib;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingDeque;

/**
 * Created by Administrator on 2016/6/20.
 */
public class NetWorkDispatcher extends Thread {

    public boolean flag = true;
    private BlockingDeque<Request> mQuee;
    private Request request;

    public NetWorkDispatcher(BlockingDeque<Request> mQuee) {
        this.mQuee = mQuee;
    }

    @Override
    public void run() {
        while (flag && !isInterrupted()) {
            try {
                request = mQuee.take();
                byte[] result = null;
                result = getResult(request.getUrl(), request.getMethod());

               /* if (request.getCallBack() != null && result != null) {
                    request.getCallBack().onResult(new String(result));
                }*/
                if(result!=null) {
                    request.onGetResult(result);
                }
            } catch (Exception e) {
                flag = false;
                request.getCallBack().onError(e);
            }
        }
    }

    private byte[] getResult(String path, Request.Method method) throws Exception {
        if (method == Request.Method.GET) {
            return getStringResponseByGet(path);
        } else {
            return getStringResponseByPost(request);
        }

    }

    private byte[] getStringResponseByPost(Request request) throws Exception {
        URL url = null;
        String path = request.getUrl();
        HttpURLConnection connection = null;
        if (path == null) {
            throw new IllegalArgumentException("URL Must Not Be Null  ");
        }
        url = new URL(path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        System.out.println("POST");
        connection.setDoOutput(true);
        String params = getPostParams(request);

        if (params != null && !("").equals(params)) {
            byte[] content = params.getBytes();
            connection.setRequestProperty("content-length", content.length + "");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(content, 0, content.length);
            outputStream.flush();
            outputStream.close();
        }

        connection.connect();
        InputStream inputStream = null;
        byte[] buff = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = -1;
        if (connection.getResponseCode() == 200) {
            inputStream = connection.getInputStream();
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
                outputStream.flush();
            }
            inputStream.close();
            inputStream = null;

        }
        return outputStream.toByteArray();
    }

    private byte[] getStringResponseByGet(String path) throws Exception {
        System.out.println(path);
        URL url = null;
        HttpURLConnection connection = null;
        if (path == null) {
            throw new IllegalArgumentException("URL Must Not Be Null  ");
        }
        url = new URL(path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.connect();
        InputStream inputStream = null;
        byte[] buff = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = -1;
        if (connection.getResponseCode() == 200) {
            inputStream = connection.getInputStream();
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
                System.out.println(len);
                outputStream.flush();
            }
            inputStream.close();
            inputStream = null;

        }
        return outputStream.toByteArray();
    }


    private String getPostParams(Request request) {
        // http://www.tngou.net/api/news/news
        StringBuffer buffer = new StringBuffer();
        HashMap<String, String> map = request.getPostParams();
        if (map != null) {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            int i = 0;
            for (Map.Entry<String, String> entry : entrySet) {
                if (i > 0) {
                    buffer.append("&");
                }
                buffer.append(entry.getKey());
                buffer.append(entry.getValue());
                i++;
            }
        }
        return buffer.toString();
    }

}
