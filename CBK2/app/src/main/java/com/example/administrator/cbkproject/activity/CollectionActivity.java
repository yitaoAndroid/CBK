package com.example.administrator.cbkproject.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.cbkproject.R;
import com.example.administrator.cbkproject.adapter.CollectionAdapter;
import com.example.administrator.cbkproject.bean.CollectionBean;
import com.example.administrator.cbkproject.utils.Constance;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/22.
 */
public class CollectionActivity extends Activity {
    private ListView listView;
    private CollectionAdapter adapter;
    private ArrayList<CollectionBean> list;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_layout);
        init();

        listView.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    private CollectionBean bean;

    private void init() {

        listView = (ListView) findViewById(R.id.collection_lv);
        adapter = new CollectionAdapter();
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");
            intent = null;
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this, DetailActivity.class);
                bean = (CollectionBean) adapter.getItem(position);
                intent.putExtra("url", Constance.DETAIL_BASE_URL + bean.getId());
                startActivity(intent);
                bean = null;
                intent = null;
            }
        });

        setInfo();
    }

    private void setInfo() {
        list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = null;
        if ("history".equals(type)) {
            cursor = resolver.query(Uri.parse("content://com.cbk.pro/collection"), null, null, null, null);
        } else if ("collection".equals(type)) {
            cursor = resolver.query(Uri.parse("content://com.cbk.pro/collection"), null, "hasread=?", new String[]{"" + Constance.HASREAD}, null);
        }

        while (cursor.moveToNext()) {
            CollectionBean bean = new CollectionBean();
            bean.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex("_id"))));
            bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            bean.setKeywords(cursor.getString(cursor.getColumnIndex("key")));
            bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            bean.setTime(Long.parseLong(cursor.getString(cursor.getColumnIndex("time"))));
            bean.setHasRead(Integer.parseInt(cursor.getString(cursor.getColumnIndex("hasread"))));
            bean.setImage(cursor.getString(cursor.getColumnIndex("imagepath")));
            list.add(bean);
            bean = null;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(list);
                for (CollectionBean bean : list) {
                    System.out.println(bean);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private AdapterView.AdapterContextMenuInfo acm;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.collection_contextmenu_item, menu);
        acm = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.collection_menu_delete) {
            final ContentResolver resolver = getContentResolver();
            bean = (CollectionBean) adapter.getItem(acm.position);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final int result = resolver.delete(Uri.parse("content://com.cbk.pro/collection"), "_id=?", new String[]{bean.getId() + ""});
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result > 0) {
                                Toast.makeText(CollectionActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CollectionActivity.this, "删除出错", Toast.LENGTH_SHORT).show();
                            }
                            adapter.remove(bean);
                            adapter.notifyDataSetChanged();
                            bean = null;
                        }
                    });
                }
            }).start();
            return true;
        }

        return true;
    }
}

