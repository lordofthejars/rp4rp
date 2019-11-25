package org.acme;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PlayedSong {

    private String songName;
    private int count;

    public PlayedSong() {
    }

    public PlayedSong aggregate(String song) {
        songName = song;
        count++;

        return this;
    }

    public String getSongName() {
        return songName;
    }

    public int getCount() {
        return count;
    }

}