package pl.damiankotynia.fourinrow.server.database;



import pl.damiankotynia.fourinrow.model.Player;
import pl.damiankotynia.fourinrow.server.exceptions.LackOfPlayersException;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Database {

    private static Database instance;
    private List<Player> waitingPlayers;

    private Database(){
        waitingPlayers = new LinkedList<>();
    }

    public static Database getInstance(){
        if (instance==null) {
            instance = new Database();
        }
        return instance;
    }

    public synchronized boolean save(Player player){
      return waitingPlayers.add(player);
    }


    public synchronized List<Player> getAll(){
        return waitingPlayers;
    }

    public synchronized boolean remove(Player service){
        return waitingPlayers.remove(service);
    }

    public synchronized Player getRandomPlayer(Player firstPlayer) throws LackOfPlayersException {
        if(waitingPlayers.size()<2){
            throw new LackOfPlayersException();
        }
        int firstIndex = waitingPlayers.indexOf(firstPlayer);
        Random random = new Random();
        int secondIndex = random.nextInt(waitingPlayers.size());
        while(firstIndex == secondIndex){
            secondIndex = random.nextInt(waitingPlayers.size());
        }
        Player secondPlayer = waitingPlayers.get(secondIndex);
        waitingPlayers.remove(firstIndex);
        waitingPlayers.remove(secondIndex);
        return secondPlayer;
    }

    private String getUUID(){
        return UUID.randomUUID().toString();
    }

}
