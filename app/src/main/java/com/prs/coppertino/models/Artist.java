package com.prs.coppertino.models;

public class Artist {
    private String artistName;
    private String artistNumberOfAlbums;
    private String artistKey;

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

    public String getArtistKey() {
        return artistKey;
    }

    public void setArtistKey(String artistKey) {
        this.artistKey = artistKey;
    }
}
