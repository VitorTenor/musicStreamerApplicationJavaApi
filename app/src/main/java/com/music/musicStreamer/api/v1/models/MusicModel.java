package com.music.musicStreamer.api.v1.models;

import com.music.musicStreamer.entities.image.Image;
import com.music.musicStreamer.entities.music.Music;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Setter
@Getter
@Table(name = "musics")
public class MusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String artist;

    @Column(nullable = false, length = 100)
    private String album;

    @Column(nullable = false, length = 100)
    private String genre;

    @Column(name = "path_name",nullable = false, length = 100)
    private String pathName;

    @Column(nullable = false, length = 100)
    private Date created_at;

    @Column(nullable = false, length = 100)
    private Date updated_at;

    public MusicModel() {
    }

    public Music toEntity() {
        return new Music(id, name, artist, album,genre, null, pathName);
    }
}
