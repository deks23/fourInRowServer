package pl.damiankotynia.fourinrow.server;

import pl.damiankotynia.fourinrow.model.Request;
import pl.damiankotynia.fourinrow.server.connector.Connection;
import pl.damiankotynia.fourinrow.server.connector.Connector;
import pl.damiankotynia.fourinrow.server.service.Configuration;

public class Main {
    public static int width;
    public static int height;
    public static void main(String[] args){
        Configuration configuration=null;
        try {
            configuration = Configuration.getInstance();
        } catch (Exception e) {
            System.out.println("Nie udało się wczytać konfiguracji");
            System.exit(-1);
        }
        width = configuration.getWidth();
        height= configuration.getHeight();
        new Thread(new Connector(configuration.getPort())).start();
    }



}
