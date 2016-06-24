package com.example.administrator.httplib;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/6/20.
 */
public class RequestQuee {
    private static RequestQuee requestQuee;

    private RequestQuee() {

    }

    private BlockingDeque<Request> mQuee = new LinkedBlockingDeque<Request>();
    private final int MAX_SIZE = 3;
    NetWorkDispatcher[] dispatchers = new NetWorkDispatcher[MAX_SIZE];

    public void initDispatcher() {
        for (int i = 0; i < dispatchers.length; i++) {
            dispatchers[i] = new NetWorkDispatcher(mQuee);
            dispatchers[i].start();
        }
    }

    public void addRequest(Request request) {
        mQuee.add(request);

    }

    public void cancelDispatcher() {
        for (int i = 0; i < dispatchers.length; i++) {
            dispatchers[i].flag = false;
            dispatchers[i].interrupt();
        }
    }

    public static RequestQuee getInstance(){
        if(requestQuee==null){
            requestQuee = new RequestQuee();
        }

        return requestQuee;
    }
}
