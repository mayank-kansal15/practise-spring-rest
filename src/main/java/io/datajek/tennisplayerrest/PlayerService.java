package io.datajek.tennisplayerrest;

import io.datajek.tennisplayerrest.exceptions.NotFoundException;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers(){
        return this.playerRepository.findAll();
    }

    public Player getPlayerById(int id){
        Optional<Player> givenPlayerOption = this.playerRepository.findById(id);
        if(givenPlayerOption.isEmpty()){
            throw new NotFoundException("Player with id "+id+" not found");
        }
        return givenPlayerOption.get();
    }

    public Player addPlayer(Player player){
        return this.playerRepository.save(player);
    }

    public Player updatePlayer(int id, Player player){
        Optional<Player> givenPlayerOption = this.playerRepository.findById(id);
        if(givenPlayerOption.isEmpty()){
            throw new NotFoundException("Player with id "+id+" not found");
        }
        player.setId(id);
        return this.playerRepository.save(player);
    }

    public Player patchPlayer(int id, Map<String, Object> playerPatch){
        Optional<Player> givenPlayerOption = this.playerRepository.findById(id);
        if(givenPlayerOption.isEmpty()){
            throw new NotFoundException("Player with id "+id+" not found");
        }
            Player givenPlayer = givenPlayerOption.get();
            playerPatch.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Player.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, givenPlayer, value);
            });
            return this.playerRepository.save(givenPlayer);
    }

    public void updateTitlesForPlayer(int id, int titles){
        Optional<Player> playerOptional = this.playerRepository.findById(id);
        if(playerOptional.isEmpty()){
            throw new NotFoundException("Player with id "+id+" not found");
        }
        this.playerRepository.updateTitles(id, titles);
    }

    public void deletePlayerById(int id){
        Optional<Player> playerOptional = this.playerRepository.findById(id);
        if(playerOptional.isEmpty()){
            throw new NotFoundException("Player with id "+id+" not found");
        }
        this.playerRepository.delete(playerOptional.get());
    }
}
