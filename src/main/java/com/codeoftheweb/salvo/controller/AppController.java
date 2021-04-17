package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppController {

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
    private ScoreRepository scoreRepository;


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> getGameView(@PathVariable Long gamePlayerId, Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "Not allowed"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByUserName(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).orElse(null);

        if (player == null) {
            return new ResponseEntity<>(makeMap("error", "Not allowed"), HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer == null) {
            return new ResponseEntity<>(makeMap("error", "Not allowed"), HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer.getPlayer().getId() != player.getId()) {
            return new ResponseEntity<>(makeMap("error", "Paso algo"), HttpStatus.CONFLICT);
        }
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> hits = new LinkedHashMap<>();
        GamePlayer opponent = gamePlayer.getOpponent();
        if (opponent != null) {
            hits.put("self", getHitsAndSinks(gamePlayer, gamePlayer.getOpponent()));
            hits.put("opponent", getHitsAndSinks(gamePlayer.getOpponent(), gamePlayer));
        } else {
            hits.put("self", new ArrayList<>());
            hits.put("opponent", new ArrayList<>());
        }
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getDate());
        dto.put("gameState", getGameStatus(gamePlayer));

        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers()
                .stream()
                .map(gamePlayer1 -> gamePlayer1.makeGamePlayerDTO())
                .collect(Collectors.toList()));
        dto.put("ships", gamePlayer.getShip()
                .stream()
                .map(ship -> ship.makeShipDTO())
                .collect(Collectors.toList()));
        dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
                .stream()
                .flatMap(gamePlayer1 -> gamePlayer1.getSalvo()
                        .stream()
                        .map(salvo -> salvo.salvoDTO()))
                .collect(Collectors.toList()));
        dto.put("hits", hits);


        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private List<Map> getHitsAndSinks(GamePlayer self, GamePlayer opponent) {

        List<Map> dto = new ArrayList<>();


        int carrierDamage = 0;
        int battleshipDamage = 0;
        int submarineDamage = 0;
        int destroyerDamage = 0;
        int patrolboatDamage = 0;

        List<String> carrierLocations = findShipLocations(self, "carrier");
        List<String> battleshipLocations = findShipLocations(self, "battleship");
        List<String> submarineLocations = findShipLocations(self, "submarine");
        List<String> destroyerLocations = findShipLocations(self, "destroyer");
        List<String> patrolboatLocations = findShipLocations(self, "patrolboat");

        for (Salvo salvo : opponent.getSalvo()) {

            int carrierHitsInTurn = 0;
            int battleshipHitsInTurn = 0;
            int submarineHitsInTurn = 0;
            int destroyerHitsInTurn = 0;
            int patrolboatHitsInTurn = 0;

            int missedShots = salvo.getSalvoLocations().size();
            Map<String, Object> damageSPerTurn = new LinkedHashMap<>();
            Map<String, Object> hitsMapPerTurn = new LinkedHashMap<>();
            List<String> hitsCellsList = new ArrayList<>();


            for (String salvoLocations : salvo.getSalvoLocations()) {//tiros

                if (carrierLocations.contains(salvoLocations)) {
                    carrierHitsInTurn++;
                    carrierDamage++;
                    hitsCellsList.add(salvoLocations);
                    missedShots--;
                }
                if (battleshipLocations.contains(salvoLocations)) {
                    battleshipHitsInTurn++;
                    battleshipDamage++;
                    hitsCellsList.add(salvoLocations);
                    missedShots--;
                }
                if (submarineLocations.contains(salvoLocations)) {
                    submarineHitsInTurn++;
                    submarineDamage++;
                    hitsCellsList.add(salvoLocations);
                    missedShots--;
                }
                if (destroyerLocations.contains(salvoLocations)) {
                    destroyerHitsInTurn++;
                    destroyerDamage++;
                    hitsCellsList.add(salvoLocations);
                    missedShots--;
                }
                if (patrolboatLocations.contains(salvoLocations)) {
                    patrolboatHitsInTurn++;
                    patrolboatDamage++;
                    hitsCellsList.add(salvoLocations);
                    missedShots--;
                }

            }
            damageSPerTurn.put("carrierHits", carrierHitsInTurn);
            damageSPerTurn.put("battleshipHits", battleshipHitsInTurn);
            damageSPerTurn.put("submarineHits", submarineHitsInTurn);
            damageSPerTurn.put("destroyerHits", destroyerHitsInTurn);
            damageSPerTurn.put("patrolboatHits", patrolboatHitsInTurn);
            //hits total
            damageSPerTurn.put("carrier", carrierDamage);
            damageSPerTurn.put("battleship", battleshipDamage);
            damageSPerTurn.put("submarine", submarineDamage);
            damageSPerTurn.put("destroyer", destroyerDamage);
            damageSPerTurn.put("patrolboat", patrolboatDamage);

            hitsMapPerTurn.put("turn", salvo.getTurn());
            hitsMapPerTurn.put("missed", missedShots);
            hitsMapPerTurn.put("damages", damageSPerTurn);
            hitsMapPerTurn.put("hitLocations", hitsCellsList);

            dto.add(hitsMapPerTurn);

        }
        return dto;
    }

    public List<String> findShipLocations(GamePlayer gamePlayer, String type) {
        Optional<Ship> response;
        response = gamePlayer.getShip().stream().filter(ship -> ship.getType().equals(type)).findFirst();
        if (response.isEmpty()) {
            return new ArrayList<String>();
        }
        return response.get().getLocations();
    }

    private String getGameStatus(GamePlayer self) {

        if (self.getShip().size() == 0) {
            return "PLACESHIPS";
        }
        if (self.getGame().getPlayers().size() == 1) {
            return "WAITINGFOROPP";
        }

        if (self.getGame().getPlayers().size() == 2) {
            if (self.getSalvo().size() == self.getOpponent().getSalvo().size()) {
                boolean selfLost = getIfAllSunk(self, self.getOpponent());
                boolean enemyLost = getIfAllSunk(self.getOpponent(), self);

                if (selfLost && enemyLost) {
                    scoreRepository.save(new Score(0.5f, LocalDateTime.now(), self.getGame(), self.getPlayer()));

                    return "TIE";
                }

                if (selfLost && !enemyLost) {
                    scoreRepository.save(new Score(0.0f, LocalDateTime.now(), self.getGame(), self.getPlayer()));

                    return "LOST";
                }

                if (enemyLost && !selfLost) {
                    scoreRepository.save(new Score(0.1f, LocalDateTime.now(), self.getGame(), self.getPlayer()));

                    return "WON";
                }
            }
            if (self.getSalvo().size() < self.getOpponent().getSalvo().size()) {
                return "PLAY";
            }

            if (self.getSalvo().size() == self.getOpponent().getSalvo().size() && (self.getId() < self.getOpponent().getId())) {
                return "PLAY";
            }

            if (self.getSalvo().size() > self.getOpponent().getSalvo().size()) {
                return "WAIT";
            }

            if (self.getSalvo().size() == self.getOpponent().getSalvo().size() && (self.getId() > self.getOpponent().getId())) {
                return "WAIT";
            }
        }

        return "UNDEFINED";
    }

    private Boolean getIfAllSunk(GamePlayer self, GamePlayer enemy) {
        if (!enemy.getShip().isEmpty() && !self.getSalvo().isEmpty()) {
            return enemy.getSalvo().stream().flatMap(salvo -> salvo.getSalvoLocations().stream()).collect(Collectors.toList()).containsAll(self.getShip().stream()
                    .flatMap(ship -> ship.getLocations().stream()).collect(Collectors.toList()));
        }
        return false;
    }
}