package com.prs.coppertino.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prs.coppertino.R;
import com.prs.coppertino.adapters.ArtistsAdapter;
import com.prs.coppertino.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    List<Song> songList;
    ArtistsAdapter adapter;

    // newInstance constructor for creating fragment with arguments
    public static ArtistsFragment newInstance(int page, String title) {
        ArtistsFragment artistsFragment = new ArtistsFragment();
        Bundle args = new Bundle();
        args.putInt("pageNo", page);
        args.putString("pageTitle", title);
        artistsFragment.setArguments(args);
        return artistsFragment;
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
        return inflater.inflate(R.layout.fragment_artists,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songList = new ArrayList<>();
        adapter = new ArtistsAdapter(getActivity(),songList);
    }

    public void fetchListFromParent(List<Song> list){
        if(isAdded()){
            songList = list;
            adapter.notifyDataSetChanged();
        }
    }
}
