package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.api.v1.models.dtos.AddMusicPlaylistDTO;
import com.music.musicStreamer.api.v1.models.dtos.CreatePlaylistDTO;
import com.music.musicStreamer.api.v1.models.dtos.PlaylistDTO;
import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.Playlist;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import com.music.musicStreamer.usecases.playlist.AddMusicPlaylistUseCase;
import com.music.musicStreamer.usecases.playlist.CreatePlaylistUseCase;
import com.music.musicStreamer.usecases.playlist.GetPlaylistByIdUseCase;
import com.music.musicStreamer.usecases.playlist.GetPlaylistByUserIdUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlists")
public class PlaylistController {
    private final CreatePlaylistUseCase createPlaylistUseCase;
    private final AddMusicPlaylistUseCase addMusicPlaylistUseCase;
    private final GetPlaylistByIdUseCase getPlaylistByIdUseCase;
    private final GetPlaylistByUserIdUseCase getPlaylistByUserIdUseCase;


    @ApiOperation(value = "Create playlist")
    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody CreatePlaylistDTO createPlaylist) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(createPlaylistUseCase.execute(createPlaylist.toRequest()));
    }

    @ApiOperation(value = "Add music to playlist")
    @PostMapping("/addSong")
    public ResponseEntity<String> addSongToPlaylist(@RequestBody AddMusicPlaylistDTO addMusicPlaylist){
        return ResponseEntity.status(HttpStatus.OK).body(addMusicPlaylistUseCase.execute(addMusicPlaylist.toRequest()));
    }

    @ApiOperation(value = "Get playlist")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPlaylistById(@PathVariable("id") int playlistId) {
            return ResponseEntity.status(HttpStatus.OK).body(getPlaylistByIdUseCase.execute(playlistId));
    }

    @ApiOperation(value = "Get all playlists from user")
    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getAllPlaylistsByUserId(@PathVariable("id") int userId) {
            return ResponseEntity.status(HttpStatus.OK).body(getPlaylistByUserIdUseCase.execute(userId));
    }

}