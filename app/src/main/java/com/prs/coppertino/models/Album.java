package com.prs.coppertino.models;

public class Album {
    private String albumId;
    private String albumTitle;
    private String albumArtist;
    //todo: private String albumArt;
    private String albumNumberOfSongs;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getAlbumNumberOfSongs() {
        return albumNumberOfSongs;
    }

    public void setAlbumNumberOfSongs(String albumNumberOfSongs) {
        this.albumNumberOfSongs = albumNumberOfSongs;
    }
}
