package com.example.administrator.cbkproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.cbkproject.R;
import com.example.administrator.cbkproject.activity.DetailActivity;
import com.example.administrator.cbkproject.adapter.ContentListViewAdapter;
import com.example.administrator.cbkproject.bean.Info;
import com.example.administrator.cbkproject.utils.Constance;
import com.example.administrator.httplib.Request;
import com.example.administrator.httplib.RequestQuee;
import com.example.administrator.httplib.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ContentFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    private ListView listview;
    private ContentListViewAdapter adapter;
    private long id;
    private final String baseURL = "http://www.tngou.net/api/info/list?id=";
    ;
    private final int pageCount = 20;
    private int currentPage = 1;
    private List<Info> list;
    private boolean isBottom;
    private PtrClassicFrameLayout mRefreshView;
    private LinearLayout footer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getContext(), R.layout.containtfrag_layout, null);
        init(view, savedInstanceState);
        return view;
    }

    private void init(View view, Bundle savedInstanceState) {

        listview = (ListView) view.findViewById(R.id.content_lv);
        adapter = new ContentListViewAdapter();
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("id");
        }
        if (savedInstanceState != null) {
            Info[] infos = (Info[]) savedInstanceState.getParcelableArray("key");
            if (infos == null || infos.length == 0) {
                getInfoFromNet();

            }//网络状况差，未获取数据
            else {
                list = Arrays.asList(infos);
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
            }

        } else {
            getInfoFromNet();
        }

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        listview.setOnScrollListener(this);
        footer = (LinearLayout) view.findViewById(R.id.contentfrag_footer);
        mRefreshView = (PtrClassicFrameLayout) view.findViewById(R.id.content_ptr);
        mRefreshView.setResistance(1.7f);
        mRefreshView.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshView.setDurationToClose(200);
        mRefreshView.setDurationToCloseHeader(1000);
        // default is false
        mRefreshView.setPullToRefresh(true);
        // default is true
        mRefreshView.setKeepHeaderWhenRefresh(true);

        mRefreshView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                getInfoFromNet();
            }

        });
    }


    private void getInfoFromNet() {
        StringRequest request = new StringRequest(baseURL + (id + 1) + "&page=" + currentPage +
                "&rows=" + pageCount, Request.Method.GET, new Request.CallBack<String>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResult(String result) {
                //adapter.addAll(result);
                try {
                    list = parseJson2List(new JSONObject(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (list == null) {
                    return;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (mRefreshView.getHeaderView().isShown()) {
                            List<Info> tempInfos = adapter.getAll();
                           if(tempInfos!=null&&tempInfos.size()>0) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (tempInfos.contains(list.get(i))) {
                                        list.remove(i);
                                        i--;
                                    }
                                }
                            }
                            tempInfos = null;
                            mRefreshView.refreshComplete();
                        }
                        if (footer.isShown()) {
                            footer.setVisibility(View.GONE);
                        }

                        adapter.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        RequestQuee quee = RequestQuee.getInstance();
        quee.initDispatcher();
        quee.addRequest(request);
    }

    private List<Info> parseJson2List(JSONObject jsonObject) throws JSONException {

        if (jsonObject == null) return null;
        JSONArray array = jsonObject.getJSONArray("tngou");
        if (array == null || array.length() == 0) return null;

        List<Info> list = new ArrayList<>();
        int len = array.length();
        JSONObject obj = null;
        Info info = null;
        for (int i = 0; i < len; i++) {
            obj = array.getJSONObject(i);
            info = new Info();
            info.setDescription(obj.optString("description"));
            info.setRcount(obj.optString("rcount"));
            long time = obj.getLong("time");
            info.setTime(time);
            info.setImage(obj.optString("img"));
            info.setId(obj.optInt("id"));
            list.add(info);
        }

        return list;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (list == null || list.size() == 0) {
            return;
        }
        Info[] infos = new Info[list.size()];
        for (int i = 0; i < infos.length; i++) {
            infos[i] = list.get(i);
        }
        outState.putParcelableArray("key", infos);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Info info = (Info) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("url", Constance.DETAIL_BASE_URL + info.getId());
        Log.i("url", "http://www.tngou.net/api/info/show?id=" + info.getId());
        startActivity(intent);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isBottom) {
            currentPage++;
            footer.setVisibility(View.VISIBLE);
            getInfoFromNet();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        isBottom = (firstVisibleItem + visibleItemCount == totalItemCount);
    }
}
