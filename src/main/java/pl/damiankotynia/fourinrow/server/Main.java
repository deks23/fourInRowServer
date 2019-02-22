package pl.damiankotynia.fourinrow.server;

import pl.damiankotynia.fourinrow.model.Request;
import pl.damiankotynia.fourinrow.server.connector.Connection;
import pl.damiankotynia.fourinrow.server.connector.Connector;
import pl.damiankotynia.fourinrow.server.service.Configuration;

public class Main {
    public static void main(String[] args){
        try {
            Configuration.getInstance();
        } catch (Exception e) {
            System.out.println("Nie udało się wczytać konfiguracji");
            System.exit(-1);
        }
        new Thread(new Connector(4444)).start();
    }
}
