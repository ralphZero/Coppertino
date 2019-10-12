package com.prs.coppertino.adapters;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {

    private Context context;
    private List<Song> songList;

    public SongsAdapter(Context context, List<Song> list) {
        this.context = context;
        this.songList = list;
    }

    @NonNull
    @Override
    public SongsAdapter.SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new SongsViewHolder(inflater.inflate(R.layout.song_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.songCover.setImageResource(R.drawable.song_placeholder);
        holder.songTitle.setText(song.getTitle());

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent));
        SpannableStringBuilder ssb = new SpannableStringBuilder(song.getArtist());
        ssb.setSpan(foregroundColorSpan,0,ssb.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" • ");
        ssb.append(song.getAlbum());

        holder.songArtistAlbum.setText(ssb, TextView.BufferType.EDITABLE);
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



    class SongsViewHolder extends RecyclerView.ViewHolder {

        ImageView songCover;
        TextView songTitle;
        TextView songArtistAlbum;
        RelativeLayout songCard;

        SongsViewHolder(View itemView) {
            super(itemView);
             songCover = itemView.findViewById(R.id.songCardImg);
             songTitle = itemView.findViewById(R.id.songCardTitle);
             songArtistAlbum = itemView.findViewById(R.id.songCardArtistAlbum);
             songCard = itemView.findViewById(R.id.songCard);
        }
    }
}
