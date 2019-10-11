package com.prs.coppertino.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prs.coppertino.R;
import com.prs.coppertino.adapters.AlbumAdapter;
import com.prs.coppertino.models.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;

    AlbumAdapter adapter;
    List<Song> songList;

    // newInstance constructor for creating fragment with arguments
    public static AlbumFragment newInstance(int page, String title) {
        AlbumFragment albumFragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putInt("pageNo", page);
        args.putString("pageTitle", title);
        albumFragment.setArguments(args);
        return albumFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("pageNo", 0);
        title = getArguments().getString("pageTitle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        songList = new ArrayList<>();
        adapter = new AlbumAdapter(getActivity(),songList);
    }

    public void fetchListFromParent(List<Song> list){
        if(this.isAdded()){
            songList = list;
            adapter.notifyDataSetChanged();
        }
    }
}
