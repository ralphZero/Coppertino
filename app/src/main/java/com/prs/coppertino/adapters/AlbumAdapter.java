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
import com.prs.coppertino.models.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private Context context;
    private List<Album> albumList;

    public AlbumAdapter(Context context, List<Album> list) {
        this.context = context;
        this.albumList = list;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new AlbumViewHolder(inflater.inflate(R.layout.album_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album a = albumList.get(position);
        holder.albumCover.setImageResource(R.drawable.album_placeholder);
        holder.albumTitle.setText(a.getAlbumTitle());
        holder.albumArtist.setText(a.getAlbumArtist());
    }

    public void addListToAdapter(List<Album> list){
        albumList.clear();
        albumList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
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
