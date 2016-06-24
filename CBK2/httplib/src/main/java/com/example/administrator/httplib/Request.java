package com.example.administrator.httplib;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/20.
 */
abstract public class Request<T> {
    private Method method;
    private String url;
    private CallBack callBack;
    private HashMap<String, String> postParams;

    public Request(String url, Method method, CallBack callBack) {
        this.method = method;
        this.url = url;
        this.callBack = callBack;
    }

    public enum Method {
        GET, POST
    }

       public interface CallBack<T> {
        public void onError(Exception e);

        public void onResult(T result) ;

    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public HashMap<String, String> getPostParams() {
        return postParams;
    }

    public void setPostParams(HashMap<String, String> postParams) {
        this.postParams = postParams;
    }

    public abstract void onGetResult(byte[] result);
}
