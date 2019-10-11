package com.prs.coppertino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.prs.coppertino.adapters.ViewPagerAdapter;
import com.prs.coppertino.models.Song;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 123;
    public static final String TAG = "MyActivity";
    @BindView(R.id.toolbar)
            Toolbar toolbar;
    @BindView(R.id.vwPager)
            ViewPager viewPager;
    @BindView(R.id.pageHeader)
            PagerSlidingTabStrip tabStrip;
    @BindView(R.id.bottomSheet)
            RelativeLayout bottomSheetLayout;
    @BindView(R.id.nowPlayingToolbar)
            Toolbar nowPlayingToolbar;
    @BindView(R.id.nowPlayingButtons)
            LinearLayout nowPlayingButtonsLayout;
    @BindView(R.id.sheetCollapseToggle)
            ImageButton sheetCollapseToggle;

    BottomSheetBehavior sheetBehavior;
    LinearLayout mTabsLinearLayout;
    ViewPagerAdapter adapter;
    List<String> list;
    private boolean hasPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        checkPermissions();

        ViewPageInit();
        BottomSheetInit();

        list = new ArrayList<>();

        if(hasPermission){
            Log.d(TAG,"Has permission");
            GetAllMusicData();
        }

    }

    private void GetAllMusicData() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = resolver.query(uri,null,selection,null,null);

        if(songCursor!=null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songPath = songCursor.getColumnIndexOrThrow("_data");

            do{
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentPath = songCursor.getString(songPath);
                list.add(currentTitle+"\n"+currentArtist+" path: "+currentPath);

            }while (songCursor.moveToNext());
            songCursor.close();
            Log.d(TAG,"List: "+list.get(0).toString());
        }
    }

    private void BottomSheetInit() {
        sheetCollapseToggle.setVisibility(View.GONE);

        nowPlayingButtonsLayout.setVisibility(View.VISIBLE);
        nowPlayingButtonsLayout.setAlpha(1f);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i==BottomSheetBehavior.STATE_EXPANDED){
                    if(sheetCollapseToggle.getVisibility()==View.VISIBLE){
                        sheetCollapseToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                bottomSheetButtonsAnimationHandler(v);
            }
        });

        nowPlayingToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private void bottomSheetButtonsAnimationHandler(float offset){
        float value = 1 - offset;
        nowPlayingButtonsLayout.setAlpha(value);

        if(value > 0f)
            nowPlayingButtonsLayout.setVisibility(View.VISIBLE);
        else
            nowPlayingButtonsLayout.setVisibility(View.GONE);


        sheetCollapseToggle.setAlpha(offset);
        if (offset < 1f)
            sheetCollapseToggle.setVisibility(View.GONE);
        else
            sheetCollapseToggle.setVisibility(View.VISIBLE);
    }

    private void ViewPageInit() {
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

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Permission is not granted
            //should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showMessageOKCancel("Coppertino works with your music library, access to your storage is needed for a better user experience. \n Do you want to allow Coppertino to read your storage?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST);
                    }
                });
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_ACCESS_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {
            //Permission has already been granted.
            hasPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    hasPermission = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    hasPermission = false;
                }
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("Allow", okListener)
                .setNegativeButton("Deny", null)
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
