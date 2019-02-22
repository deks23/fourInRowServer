package pl.damiankotynia.fourinrow.server.connector;

import pl.damiankotynia.fourinrow.model.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import static pl.damiankotynia.fourinrow.server.service.Utils.INBOUND_CONNECTION_LOGGER;

public class Connection implements Runnable {
    private Socket socket;
    private int clientNumber;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private List<Connection> connectionList;
    private boolean running;
    private OutboundConnection outboundConnection;

    public Connection(Socket socket, int client, List<Connection> connectionList) {
        System.out.println(INBOUND_CONNECTION_LOGGER + "New Connection");
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outboundConnection = new OutboundConnection(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.clientNumber = client;
        this.socket = socket;
        this.connectionList = connectionList;
        this.running = true;
    }

    public void run() {
        while (running) {
            try {
                Object request = inputStream.readObject();
                //TODO WYWOŁANIE REQUEST EXECUTORA


            } catch (SocketException e) {
                System.out.println(INBOUND_CONNECTION_LOGGER + "Zerwano połączenie");
                running = !running;
                connectionList.remove(this);
            } catch (IOException e) {
                System.out.println(INBOUND_CONNECTION_LOGGER + "Zerwano połączenie2");
                running = !running;
                connectionList.remove(this);
            } catch (ClassNotFoundException e) {
                System.out.println(INBOUND_CONNECTION_LOGGER + "Niepoprawny format zapytania");
            }
        }


    }

}