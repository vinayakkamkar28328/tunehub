package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.entities.Playlist;
import com.tunehub.entities.Song;
import com.tunehub.services.PlaylistService;
import com.tunehub.services.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		
		//fetch song list
		List<Song> songList= songService.fetchAllSongs();
		//add to model object
		model.addAttribute("songs", songList);
		
		return "createPlaylist";
	}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		//updating playlist table
		playlistService.addPlaylist(playlist);
		
		//updating song table
		List<Song> songList = playlist.getSongs();
		for(Song s : songList) {
			s.getPlaylists().add(playlist);
			//update song object in db
			songService.updateSong(s);
		}
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		
		List<Playlist> allPlaylists= playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylists",allPlaylists);
		return "displayPlaylists";
	}

}
