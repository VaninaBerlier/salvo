package com.codeoftheweb.salvo;

public class GameState {

    private String placeShips = "POSICIONAR";
    private String waitingForOppononent = "ESPERANDO_OPONENTE";
    private String wait = "ESPERE";
    private String play = "JUGAR";
    private String won = "GANASTE";
    private String lost= "PERDISTE";
    private String tie= "EMPATASTE";


    public GameState() {
    }

    public String getPlaceShips() {
        return placeShips;
    }

    public void setPlaceShips(String placeShips) {
        this.placeShips = placeShips;
    }

    public String getWaitingForOppononent() {
        return waitingForOppononent;
    }

    public void setWaitingForOppononent(String waitingForOppononent) {
        this.waitingForOppononent = waitingForOppononent;
    }

    public String getWait() {
        return wait;
    }

    public void setWait(String wait) {
        this.wait = wait;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getTie() {
        return tie;
    }

    public void setTie(String tie) {
        this.tie = tie;
    }
}
