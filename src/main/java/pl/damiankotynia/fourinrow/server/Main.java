package pl.damiankotynia.fourinrow.server;

import pl.damiankotynia.fourinrow.model.Request;
import pl.damiankotynia.fourinrow.server.connector.Connection;
import pl.damiankotynia.fourinrow.server.connector.Connector;

public class Main {
    public static void main(String[] args){
       new Thread(new Connector(4444)).start();
    }
}
