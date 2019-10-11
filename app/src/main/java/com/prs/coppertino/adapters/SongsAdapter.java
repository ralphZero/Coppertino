package com.prs.coppertino.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prs.coppertino.models.Song;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {

    private Context context;
    private List<Song> songList;

    public SongsAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongsAdapter.SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapter.SongsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class SongsViewHolder extends RecyclerView.ViewHolder {
        public SongsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
