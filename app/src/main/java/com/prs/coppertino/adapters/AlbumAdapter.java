package com.prs.coppertino.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prs.coppertino.R;
import com.prs.coppertino.models.Song;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private Context context;
    private List<Song> songList;

    public AlbumAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AlbumViewHolder(inflater.inflate(R.layout.album_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.albumCover.setImageResource(R.drawable.placeholder);
        holder.albumTitle.setText(song.getAlbum());
        holder.albumArtist.setText(song.getArtist());
    }

    public void addListToAdapter(List<Song> list){
        songList.clear();
        songList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    class AlbumViewHolder extends RecyclerView.ViewHolder {

        ImageView albumCover;
        TextView albumTitle;
        TextView albumArtist;
        RelativeLayout albumCard;

        AlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            albumCover = itemView.findViewById(R.id.albumCardImg);
            albumTitle = itemView.findViewById(R.id.albumCardTitle);
            albumArtist = itemView.findViewById(R.id.albumCardArtist);
            albumCard = itemView.findViewById(R.id.albumCard);

        }
    }
}
