package br.com.fieldrent.controller;

import br.com.fieldrent.model.Player;
import br.com.fieldrent.model.Player;
import br.com.fieldrent.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by inafalcao on 1/18/16.
 */

@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public List<Player> list() {
        return playerRepository.findAll();
    }

    @RequestMapping(value = "/player", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Player Player) {
        playerRepository.save(Player);
    }

    @RequestMapping(value="/player/{id}", method = RequestMethod.GET)
    public Player getOne(@PathVariable("id") Long id) {
        return playerRepository.findOne(id);
    }


    @RequestMapping(value = "/player/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Player Player) {
        Player existingPlayer= playerRepository.findOne(id);
        Player.setId(existingPlayer.getId());
        existingPlayer = Player;
        playerRepository.save(existingPlayer);
    }

    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        playerRepository.delete(id);
    }

}
