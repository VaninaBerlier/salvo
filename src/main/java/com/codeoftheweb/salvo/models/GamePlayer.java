package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native") //
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();

    @OrderBy
    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Salvo> salvo;

    private LocalDateTime date;


    public GamePlayer() {
        this.date = LocalDateTime.now();
        this.ships = new HashSet<>();
        this.salvo = new HashSet<>();
    }

    public GamePlayer(Game game, Player player) {
        this.date = LocalDateTime.now();
        this.game = game;
        this.player = player;
        this.ships = new HashSet<>();
        this.salvo = new HashSet<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Set<Ship> getShip() {
        return  ships;
    }

    public void setShip(Set<Ship> ship) {
        this.ships = ship;
    }

    public Set<Salvo> getSalvo() {
        return salvo;
    }

    public void setSalvo(Set<Salvo> salvo) {
        this.salvo = salvo;
    }

    public Score get_Score() {
       return player.getScores(this.game);
    }

    public void addShip(Set<Ship> ships){
        ships.forEach(ship -> {
            ship.setGamePlayer(this);
            this.ships.add(ship);
        });
    }

    public void addSalvo(Salvo salvo){
       salvo.setGamePlayer(this);
       getSalvo().add(salvo);
    }

    public GamePlayer getOpponent(){
        return this.getGame().getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getId() != this.getId()).findFirst().orElse(new GamePlayer());
      }


    public Map<String,Object> makeGamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("gpid", getId());
        dto.put("id", id);
        dto.put("player", player.makePlayerDTO());
        return dto;
    }
}