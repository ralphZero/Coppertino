package com.prs.coppertino.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prs.coppertino.models.Song;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    private Context context;
    private List<Song> songList;

    public ArtistsAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
