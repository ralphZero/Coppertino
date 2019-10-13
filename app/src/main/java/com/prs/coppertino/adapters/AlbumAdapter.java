package com.prs.coppertino.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prs.coppertino.AlbumInfoActivity;
import com.prs.coppertino.R;
import com.prs.coppertino.models.Album;

import java.util.List;

import butterknife.OnClick;

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
        final Album a = albumList.get(position);
        Glide.with(context)
                .load(getAlbumArtFromId(a.getAlbumId()))
                .placeholder(R.drawable.album_placeholder)
                .into(holder.albumCover);
        //holder.albumCover.setImageResource(R.drawable.album_placeholder);
        holder.albumTitle.setText(a.getAlbumTitle());
        holder.albumArtist.setText(a.getAlbumArtist());

        View.OnClickListener listenerDetail = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlbumInfoActivity.class);
                intent.putExtra("album_id", a.getAlbumId());
                context.startActivity(intent);
            }
        };

        holder.albumCover.setOnClickListener(listenerDetail);
        holder.albumCard.setOnClickListener(listenerDetail);
    }

    public void addListToAdapter(List<Album> list){
        albumList.clear();
        albumList.addAll(list);
        notifyDataSetChanged();
    }

    private String getAlbumArtFromId(String mAlbumId) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID+ "=?",
                new String[] {String.valueOf(mAlbumId)},
                null);

        if (cursor!=null && cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            cursor.close();
            // do whatever you need to do
            return path;
        }
        return null;
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
