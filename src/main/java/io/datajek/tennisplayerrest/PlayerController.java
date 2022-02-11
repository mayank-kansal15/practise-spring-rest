package io.datajek.tennisplayerrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to tennis world";
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return this.playerService.getAllPlayers();
    }

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable("playerId") int id){
        return this.playerService.getPlayerById(id);
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player){
        return this.playerService.addPlayer(player);
    }

    @PutMapping("/players/{playerId}")
    public Player updatePlayer(@PathVariable("playerId") int playerId, @RequestBody Player player){
        return this.playerService.updatePlayer(playerId, player);
    }

    @PatchMapping("/players/{playerId}")
    public Player patchPlayer(@PathVariable("playerId") int id, @RequestBody Map<String, Object> playerPatch){
        return this.playerService.patchPlayer(id, playerPatch);
    }

    @PatchMapping("/players/{playerId}/titles")
    public void updateTitles(@PathVariable("playerId") int id, @RequestBody int titles){
        this.playerService.updateTitlesForPlayer(id, titles);
    }

    @DeleteMapping("/players/{playerId}")
    public void deletePlayerById(@PathVariable("playerId") int id){
        this.playerService.deletePlayerById(id);
    }
}
