package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;


@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime date;

    @OneToMany(mappedBy = "game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch=FetchType.EAGER)
    private  Set<Score> scores = new HashSet<>();


    public Game() {
        this.date=LocalDateTime.now();
    }

    public Game(LocalDateTime date) {
        this.date = date;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public  Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

   //@JsonIgnore
    public List<Player> getPlayers() {
       return gamePlayers.stream().map(sub-> sub.getPlayer()).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Map<String, Object> makeGameDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", getId());
        dto.put("created", getDate());
        dto.put("gamePlayers", gamePlayers.stream().map(gamePlayer -> gamePlayer.makeGamePlayerDTO()).collect(Collectors.toSet()));
        dto.put("scores", scores.stream().map(Score::makeScoreDTO).collect(Collectors.toList()));
        return dto;
    }

}
