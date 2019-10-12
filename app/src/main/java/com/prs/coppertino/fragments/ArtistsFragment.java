package com.prs.coppertino.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.prs.coppertino.MainActivity;
import com.prs.coppertino.R;
import com.prs.coppertino.adapters.ArtistsAdapter;
import com.prs.coppertino.models.Artist;
import com.prs.coppertino.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    List<Artist> artistList;
    ArtistsAdapter adapter;
    RecyclerView rvArtist;

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
        artistList = new ArrayList<>();

        rvArtist = (RecyclerView) view.findViewById(R.id.rvArtist);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        adapter = new ArtistsAdapter(getActivity(),artistList);

        rvArtist.setLayoutManager(gridLayoutManager);
        rvArtist.setAdapter(adapter);

        adapter.addListToAdapter(((MainActivity) getActivity()).GetAllArtistData());
    }
}
