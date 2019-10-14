package com.prs.coppertino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.prs.coppertino.adapters.SongsOfAlbum;
import com.prs.coppertino.models.Song;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumInfoActivity extends AppCompatActivity {

    @BindView(R.id.infoAlbumToolbar)
            Toolbar toolbar;
    @BindView(R.id.infoAlbumAppbar)
            AppBarLayout appBarLayout;
    @BindView(R.id.infoToolbarLayout)
            LinearLayout layout;
    @BindView(R.id.infoAlbumCollapsingToolbar)
            CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.infoAlbumImg)
            ImageView albumCover;
    @BindView(R.id.infoAlbumTitle)
            TextView albumTitle;
    @BindView(R.id.infoAlbumArtist)
            TextView albumArtist;
    @BindView(R.id.infoAlbumTrackCount)
            TextView albumTrackCount;
    @BindView(R.id.infoAlbumYear)
            TextView albumYear;
    @BindView(R.id.rvAlbumInfoSongs)
            RecyclerView rvInfoSongs;

    String mAlbumId;
    String mAlbumArt;
    String mAlbumTitle;
    String mAlbumArtist;
    String mAlbumNoTrack;
    String mAlbumYear;

    List<Song> songList;
    SongsOfAlbum songsAdapter;

    public static final String TAG = "INFO_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configAppBarLayout();

        songList = new ArrayList<>();
        songsAdapter = new SongsOfAlbum(this,songList);

        rvInfoSongs.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvInfoSongs.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rvInfoSongs.setAdapter(songsAdapter);

        mAlbumId = getIntent().getStringExtra("album_id");
        getDataFromId();

    }

    private void getDataFromId() {

        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums.FIRST_YEAR
        };

        Cursor cursor = getContentResolver().query(uri,projection,MediaStore.Audio.Albums._ID+ "=?",
                new String[] {String.valueOf(mAlbumId)},
                null);

        if (cursor!=null && cursor.moveToFirst()) {
            mAlbumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            mAlbumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            mAlbumArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            mAlbumNoTrack = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            mAlbumYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            cursor.close();
            // do whatever you need to do
            bindDataToViews();
        }
    }

    private void bindDataToViews() {
        Glide.with(this)
                .load(mAlbumArt)
                .placeholder(R.drawable.album_placeholder)
                .into(albumCover);
        albumTitle.setText(mAlbumTitle);
        albumArtist.setText(mAlbumArtist);
        albumYear.setText(mAlbumYear);
        String track;
        if(Integer.parseInt(mAlbumNoTrack) > 0)
            track = mAlbumNoTrack+" track";
        else
            track = mAlbumNoTrack+" tracks";
        albumTrackCount.setText(track);

        //getting song data from another thread
        new getSongAsync().execute(mAlbumId);
    }

    private void configAppBarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float offset = Math.abs(i / (float) appBarLayout.getTotalScrollRange());
                layout.setAlpha(1.0f - offset);
                if (offset < 1)
                    toolbarLayout.setTitle("");
                else
                    toolbarLayout.setTitle("ALBUM_NAME_HERE");

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class getSongAsync extends AsyncTask<String,Void,Cursor>{

        @Override
        protected Cursor doInBackground(String... strings) {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String[] projection = new String[]{
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.TRACK,
                    MediaStore.Audio.Media.DURATION
            };
            String order = MediaStore.Audio.Media.TRACK+" ASC";

            return getContentResolver().query(uri,projection,MediaStore.Audio.Media.ALBUM_ID+ "=?",
                    new String[] {String.valueOf(strings[0])}, order);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            List<Song> list = new ArrayList<>();
            if(cursor!=null && cursor.moveToFirst()){
                do{
                    Song song = new Song();
                    song.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    song.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    song.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    song.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    song.setTrackNumber(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK)));
                    song.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                    list.add(song);

                }while (cursor.moveToNext());
                cursor.close();
                songsAdapter.addListToAdapter(list);
            }
        }
    }

}
