package com.example.administrator.cbkproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.example.administrator.cbkproject.R;
import com.example.administrator.cbkproject.adapter.ContentPagerAdapter;
import com.example.administrator.httplib.RequestQuee;

public class HomeActivity extends AppCompatActivity {
private TabLayout tabLayout;
    private ViewPager viewPager;
    private ContentPagerAdapter adapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void init() {

        viewPager = (ViewPager) findViewById(R.id.pager_main);
        adapter = new ContentPagerAdapter(getSupportFragmentManager());
        toolbar = (Toolbar) findViewById(R.id.main_indictotr_tl);
        tabLayout = (TabLayout) findViewById(R.id.main_tl);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setSelectedTabIndicatorColor(Color.LTGRAY);

        toolbar.inflateMenu(R.menu.menu_toolbar_main);
        toolbar.setOnMenuItemClickListener(new MyListener());
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setTitle("茶百科");
    }

    class MyListener implements Toolbar.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId() == R.id.main_more){
                Intent  intent= new Intent(HomeActivity.this,CollectionMore.class);
                startActivity(intent);
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestQuee.getInstance().cancelDispatcher();
    }
}
