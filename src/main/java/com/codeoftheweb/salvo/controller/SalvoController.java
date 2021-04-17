package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    private boolean isGuest(Authentication authentication){
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }


    @GetMapping("/games")
    public Map<String, Object> getAllGames(Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        if (isGuest(authentication)) {
            map.put("player", "Guest");
        }else{
            map.put("player", playerRepository.findByUserName(authentication.getName()).makePlayerDTO());
        }
        map.put("games", gameRepository.findAll().stream().map(game -> game.makeGameDTO()).collect(Collectors.toList()));
        return map;
    }

    @PostMapping("/games")
    public ResponseEntity<Map<String, Object>> createdGame(Authentication authentication) {
        ResponseEntity<Map<String, Object>> response;

        if (isGuest(authentication)){
            response = new ResponseEntity<>(makeMap("error", "no player logger in"), HttpStatus.UNAUTHORIZED);

        } else {
            Game createdGame = new Game(LocalDateTime.now());
            gameRepository.save(createdGame);
            Player newPlayer = playerRepository.findByUserName(authentication.getName());
            playerRepository.save(newPlayer);
            GamePlayer newGamePlayer = new GamePlayer(createdGame, newPlayer);
            gamePlayerRepository.save(newGamePlayer);

            response = new ResponseEntity<>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
        }
        return response;
    }

    @RequestMapping(path = "/game/{gameId}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long gameId, Authentication authentication){
        ResponseEntity<Map<String, Object>> response;
        Optional<Game> game = gameRepository.findById(gameId);

        if(isGuest(authentication)){
            response = new ResponseEntity<>(makeMap("error", "player in Unauthorized"), HttpStatus.UNAUTHORIZED);
        }else if(game.isEmpty()){
            response = new ResponseEntity<>(makeMap("error", "game doesnt exist"), HttpStatus.FORBIDDEN);
        }else if(game.get().getGamePlayers().size() > 1){
            response = new ResponseEntity<>(makeMap("error", "sorry the game is full"), HttpStatus.FORBIDDEN);
        }else{
            Player player = playerRepository.findByUserName(authentication.getName());
            GamePlayer gamePlayer = new GamePlayer(game.get(), player);
            gamePlayerRepository.save(gamePlayer);
            response = new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()), HttpStatus.CREATED);
        }
        return response;
    }

    @GetMapping("/players")
    private List<Map<String, Object>> getAllPlayers() {
        return playerRepository.findAll().stream().map(player -> player.makePlayerDTO()).collect(Collectors.toList());
    }

    @PostMapping("/players")
    public ResponseEntity <Map<String, Object>> register (@RequestParam String email, @RequestParam String password){
        if(email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Missing data"), HttpStatus.FORBIDDEN);
        }
        if(playerRepository.findByUserName(email) != null) {
            return new ResponseEntity<>(makeMap("error", "Name already in use"), HttpStatus.FORBIDDEN);
        }
        playerRepository.save(new Player(email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(makeMap("message", "Success! Player created"), HttpStatus.CREATED);
    }

     private Map<String, Object> makeMap(String key, Object value){
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
     }

    @RequestMapping("/games/players/{gamePlayerId}/ships")
    public ResponseEntity<Map<String, Object>> addShips(@PathVariable long gamePlayerId, @RequestBody Set<Ship> ships, Authentication authentication) {

        ResponseEntity<Map<String, Object>> response;
        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        Player player = playerRepository.findByUserName(authentication.getName());

        if(isGuest(authentication)){
            response = new ResponseEntity<>(makeMap("error", "Player in Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
           else if (gamePlayer.isEmpty()){
               response = new ResponseEntity<>(makeMap("error", "GamePlayer not found"), HttpStatus.FORBIDDEN);
            }
            else if(gamePlayer.get().getPlayer().getId() != player.getId()){
                response = new ResponseEntity<>(makeMap("error", "Player in Unauthorized"), HttpStatus.UNAUTHORIZED);
            }
            else if (gamePlayer.get().getShip().size()>0){
                response = new ResponseEntity<>(makeMap("error", "Locations era full"), HttpStatus.FORBIDDEN);}
            else if (ships.size() > 0){
                gamePlayer.get().addShip(ships);
                gamePlayerRepository.save(gamePlayer.get());
               response = new ResponseEntity<>(makeMap("OK", "Ships saved"), HttpStatus.CREATED);
        } else {
                response = new ResponseEntity<>(makeMap("error", "Ships are empty"), HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @RequestMapping("/games/players/{gamePlayerId}/salvoes")
    public ResponseEntity<Map<String, Object>> addSalvos(@PathVariable long gamePlayerId, @RequestBody Salvo salvo, Authentication authentication) {
        ResponseEntity<Map<String, Object>> response;
        Player player = playerRepository.findByUserName(authentication.getName());
        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        GamePlayer gPlayer1 = gamePlayer.get().getOpponent();

        if(isGuest(authentication)){
            response = new ResponseEntity<>(makeMap("error", "Player in Unauthorized"), HttpStatus.UNAUTHORIZED);
        }else if (gamePlayer.isEmpty()){
            response = new ResponseEntity<>(makeMap("error", "GamePlayer not found"), HttpStatus.FORBIDDEN);
        }else if (gamePlayer.get().getPlayer().getId() != player.getId()) {
       response = new ResponseEntity<>(makeMap("error", "Player in Unauthorized"), HttpStatus.UNAUTHORIZED);
        }else if(gPlayer1.getId() == 0){
            response = new ResponseEntity<>(makeMap("error", "no opponent"), HttpStatus.UNAUTHORIZED);
        } else if(gamePlayer.get().getSalvo().size()<= gPlayer1.getSalvo().size()){
            salvo.setTurn(gamePlayer.get().getSalvo().size() + 1);
            salvo.setGamePlayer(gamePlayer.get());
            gamePlayer.get().addSalvo(salvo);
            salvoRepository.save(salvo);
            response = new ResponseEntity<>(makeMap("OK", "Salvo created"), HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(makeMap("error", "Cant created salvos"), HttpStatus.FORBIDDEN);
        }
        return response;
    }

    }



