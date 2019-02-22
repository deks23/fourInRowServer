package pl.damiankotynia.fourinrow.server.service;

import pl.damiankotynia.fourinrow.model.GameField;
import pl.damiankotynia.fourinrow.model.Player;
import pl.damiankotynia.fourinrow.server.Main;

public class Game {
    private Player player1;
    private Player player2;
    private Player whosTurn;
    private GameField gameField;

    public Game(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.whosTurn = player1;
        this.gameField = new GameField(Main.width, Main.height);
        this.player1.setPlayerSign(1);
        this.player2.setPlayerSign(2);
    }

    public void initConnection(){
        RequestExecutorImpl requestExecutor1 = (RequestExecutorImpl)player1.getRequestExecutor();
        RequestExecutorImpl requestExecutor2 = (RequestExecutorImpl)player2.getRequestExecutor();
        requestExecutor1.setOponentsRequestExecutor(requestExecutor2);
        requestExecutor2.setOponentsRequestExecutor(requestExecutor1);
    }

    public void disconnect(){
        RequestExecutorImpl requestExecutor1 = (RequestExecutorImpl)player1.getRequestExecutor();
        RequestExecutorImpl requestExecutor2 = (RequestExecutorImpl)player2.getRequestExecutor();
        requestExecutor1.setOponentsRequestExecutor(null);
        requestExecutor2.setOponentsRequestExecutor(null);
        requestExecutor1.setGame(null);
        requestExecutor2.setGame(null);

    }

    public boolean won(){
        return EndGameCheck.checkForIdenticalFour(gameField.getGameBoard());
    }


    public void makeMove(GameField gameField){
        this.gameField = gameField;
    }


}
