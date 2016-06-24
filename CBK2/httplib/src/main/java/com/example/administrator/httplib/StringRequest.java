package com.example.administrator.httplib;

/**
 * Created by Administrator on 2016/6/21.
 */
public   class StringRequest extends Request<String>  {
    private CallBack<String> callBack;
    public StringRequest(String url, Method method, CallBack callBack) {
        super(url, method, callBack);
        this.callBack = callBack;
    }

    @Override
    public void onGetResult(byte[] result) {
        String str = new String(result);
        callBack.onResult(str);
    }

}
