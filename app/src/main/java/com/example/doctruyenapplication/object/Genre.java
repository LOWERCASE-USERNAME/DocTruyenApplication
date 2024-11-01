package com.example.doctruyenapplication.object;

import java.io.Serializable;

public class Genre implements Serializable {
    private int genreId;
    private String genreName;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int _genreId) {
        genreId = _genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String _genreName) {
        genreName = _genreName;
    }
}
