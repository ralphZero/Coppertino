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
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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
import com.prs.coppertino.models.Album;
import com.prs.coppertino.models.Artist;
import com.prs.coppertino.models.Song;

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
    @BindView(R.id.bottomSheetInnerContainer)
            RelativeLayout innerContainer;

    BottomSheetBehavior sheetBehavior;
    LinearLayout mTabsLinearLayout;
    ViewPagerAdapter adapter;

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

        viewPageInit();
        bottomSheetInit();

    }



    public List<Song> getAllSongData() {
        if(hasPermission){
            Log.d(TAG,"Songs has permission");

            List<Song> list = new ArrayList<>();

            String[] projection = new String[]{
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.ALBUM_ID
            };

            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
            String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
            ContentResolver resolver = getContentResolver();
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor songCursor = resolver.query(uri,projection,selection,null,sortOrder);

            if(songCursor!=null && songCursor.moveToFirst()){
                int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int songAlbumId = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int songPath = songCursor.getColumnIndex("_data");

                do{
                    //todo: call a function that gonna fetch albumArt with the provided albumId (done in listview)
                    //then we add the albumArt string to the Song object
                    //String songAlbumArt = getAlbumArtFromId(songCursor.getString(songAlbumId));
                    Song song = new Song();
                    song.setTitle(songCursor.getString(songTitle));
                    song.setArtist(songCursor.getString(songArtist));
                    song.setAlbum(songCursor.getString(songAlbum));
                    song.setPath(songCursor.getString(songPath));
                    song.setAlbumId(songCursor.getString(songAlbumId));
                    //song.setAlbumArt(songAlbumArt);
                    list.add(song);

                }while (songCursor.moveToNext());
                songCursor.close();

                Log.d(TAG,"SongList: "+list.get(0).getAlbum());

                return list;
            }
        }
        return null;
    }

    public List<Album> getAllAlbumData(){
        if(hasPermission){
            List<Album> albumList = new ArrayList<>();

            ContentResolver resolver = getContentResolver();
            String[] projection = new String[] {
                    MediaStore.Audio.Albums._ID,
                    MediaStore.Audio.Albums.ALBUM,
                    MediaStore.Audio.Albums.ARTIST,
                    //MediaStore.Audio.Albums.ALBUM_ART
            };

            String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
            Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
            if(cursor!=null && cursor.moveToFirst()){
                int albumId = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                int albumTitle = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                int albumArtist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
                //int albumArt = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

                do{

                    Album album = new Album();
                    album.setAlbumId(cursor.getString(albumId));
                    album.setAlbumTitle(cursor.getString(albumTitle));
                    album.setAlbumArtist(cursor.getString(albumArtist));
                    //album.setAlbumArt(cursor.getString(albumArt));
                    albumList.add(album);

                }while (cursor.moveToNext());
                cursor.close();

                Log.d(TAG,"AlbumList: "+albumList.get(0).getAlbumTitle()+"\n"+albumList.get(0).getAlbumArtist());

                return albumList;
            }
        }
        return null;
    }

    public List<Artist> getAllArtistData(){
        if(hasPermission){

            List<Artist> artistList = new ArrayList<>();
            ContentResolver resolver = getContentResolver();

            String[] projection = new String[] {
                    MediaStore.Audio.Artists._ID,
                    MediaStore.Audio.Artists.ARTIST,
                    MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
            };

            String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC";
            Cursor cursor = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
            if(cursor!=null && cursor.moveToFirst()){
                int artistKey = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
                int artistName = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
                int artistNumberOfAlbum = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);

                do{

                    Artist artist = new Artist();
                    artist.setArtistId(cursor.getString(artistKey));
                    artist.setArtistName(cursor.getString(artistName));
                    artist.setArtistNumberOfAlbums(cursor.getString(artistNumberOfAlbum));

                    artistList.add(artist);

                }while (cursor.moveToNext());
                cursor.close();

                Log.d(TAG,"ArtistList: "+artistList.get(0).getArtistName()+"\n"+artistList.get(0).getArtistNumberOfAlbums());

                return artistList;
            }
        }
        return null;
    }

    private void bottomSheetInit() {
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
        innerContainer.setAlpha(offset);

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

    private void viewPageInit() {
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