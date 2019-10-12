package com.prs.coppertino.models;

public class Artist {
    private String artistName;
    private String artistNumberOfAlbums;
    private String artistId;

    public Artist() {
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistNumberOfAlbums() {
        return artistNumberOfAlbums;
    }

    public void setArtistNumberOfAlbums(String artistNumberOfAlbums) {
        this.artistNumberOfAlbums = artistNumberOfAlbums;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
}
