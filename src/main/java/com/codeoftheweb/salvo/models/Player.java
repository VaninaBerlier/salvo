package com.codeoftheweb.salvo.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native") //
    private long id;

    private String userName;

    @OneToMany(mappedBy = "player", fetch=FetchType.EAGER)
    private List<GamePlayer> gamePlayers;

    @OneToMany(mappedBy = "player", fetch=FetchType.EAGER)
    private List<Score> scores;

    private String password;

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Player() {
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(List<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }


    public Score getScores(Game game) {
        return scores.stream().filter(score -> score.getGame().getId()==game.getId()).findFirst().orElse(null);
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @JsonIgnore
    private List<Game> getGames(){
      return gamePlayers.stream().map(sub-> sub.getGame()).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setPlayer(this);
        getGamePlayers().add(gamePlayer);
    }

    public Map<String, Object> makePlayerDTO(){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", getId());
        dto.put("email", getUserName());
        return dto;
    }

}