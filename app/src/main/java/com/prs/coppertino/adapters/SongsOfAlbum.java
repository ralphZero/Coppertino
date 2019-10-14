package com.prs.coppertino.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prs.coppertino.R;
import com.prs.coppertino.models.Song;
import com.prs.coppertino.models.TimeConverter;
import com.prs.coppertino.models.TrackDataConverter;

import java.util.List;

public class SongsOfAlbum extends RecyclerView.Adapter<SongsOfAlbum.SongHolder> {

    private Context context;
    private List<Song> songList;

    public SongsOfAlbum(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongHolder(LayoutInflater.from(context).inflate(R.layout.song_album_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        Song song = songList.get(position);
        holder.trackNumber.setText(TrackDataConverter.Convert(song.getTrackNumber()));
        holder.trackTitle.setText(song.getTitle());
        holder.trackDuration.setText(TimeConverter.ConvertToSeconds(song.getDuration()));
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

    class SongHolder extends RecyclerView.ViewHolder {

        TextView trackNumber,trackTitle,trackDuration;

        SongHolder(@NonNull View itemView) {
            super(itemView);
            trackNumber = itemView.findViewById(R.id.songAlbumTrackNumber);
            trackTitle = itemView.findViewById(R.id.songAlbumTitle);
            trackDuration = itemView.findViewById(R.id.songAlbumDuration);
        }
    }
}
