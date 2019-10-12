package com.prs.coppertino.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prs.coppertino.R;
import com.prs.coppertino.models.Artist;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    private Context context;
    private List<Artist> artistList;

    public ArtistsAdapter(Context context, List<Artist> list) {
        this.context = context;
        this.artistList = list;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ArtistViewHolder(inflater.inflate(R.layout.artist_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        //holder.artistImg.setImageResource(R.drawable.artist_placeholder);

        Glide.with(context)
                .load(getAlbumArtFromId(artist.getArtistId()))
                .placeholder(R.drawable.artist_placeholder)
                .into(holder.artistImg);

        holder.artisteName.setText(artist.getArtistName());
        String artistNOA;
        if(Integer.parseInt(artist.getArtistNumberOfAlbums())<= 1)
            artistNOA = artist.getArtistNumberOfAlbums()+" Album";
        else
            artistNOA = artist.getArtistNumberOfAlbums()+" Albums";
        holder.artistNumberOfAlbum.setText(artistNOA);
    }

    public void addListToAdapter(List<Artist> list){
        artistList.clear();
        artistList.addAll(list);
    }

    private String getAlbumArtFromId(String mArtistId) {
        Uri uri = MediaStore.Audio.Artists.Albums.getContentUri("external",Long.parseLong(mArtistId));
        String[] projection = new String[]{
                MediaStore.Audio.Artists.Albums.ALBUM_ART
        };
        Cursor cursor = context.getContentResolver().query(uri,projection,
                /*MediaStore.Audio.Artists.Albums.ARTIST_ID+ "=?"*/null,
                /*new String[] {String.valueOf(mArtistId)}*/null,
                MediaStore.Audio.Artists.Albums.ALBUM_KEY + " LIMIT 1");

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
        return artistList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        CircleImageView artistImg;
        TextView artisteName;
        TextView artistNumberOfAlbum;

        ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            artistImg = itemView.findViewById(R.id.artistCardImg);
            artisteName = itemView.findViewById(R.id.artistCardName);
            artistNumberOfAlbum = itemView.findViewById(R.id.artistCardNumberOfAlbum);
        }
    }
}
