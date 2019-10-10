package com.prs.coppertino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.prs.coppertino.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vwPager)
    ViewPager viewPager;
    @BindView(R.id.pageHeader)
    PagerSlidingTabStrip tabStrip;

    LinearLayout mTabsLinearLayout;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabStrip.setViewPager(viewPager);

        mTabsLinearLayout = ((LinearLayout) tabStrip.getChildAt(0));
        mTabsLinearLayout.setPadding(0,0,0,0);
        for(int i=0; i < mTabsLinearLayout.getChildCount(); i++){
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            tv.setPadding(0,0,0,0);
            tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto__light.ttf"));
            if(i == 0){
                tv.setTextSize(22f);
            } else {
                tv.setTextSize(14f);
            }
        }
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i < mTabsLinearLayout.getChildCount(); i++){
                    TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
                    tv.setPadding(0,0,0,0);
                    tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto__light.ttf"));
                    if(i == position){
                        tv.setTextSize(22f);
                    } else {
                        tv.setTextSize(14f);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
