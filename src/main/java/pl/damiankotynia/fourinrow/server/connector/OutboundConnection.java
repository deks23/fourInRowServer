package pl.damiankotynia.fourinrow.server.connector;

import pl.damiankotynia.fourinrow.model.Response;

import java.io.*;

import static pl.damiankotynia.fourinrow.server.service.Utils.OUTBOUND_CONNECTION_LOGGER;

public class OutboundConnection{

    private ObjectOutputStream outputStream;
    private boolean isRunning;

    /**
     * Constructor for OutboundConnection class
     * @param outputStream output stream to client
     * @throws IOException
     */
    public OutboundConnection(ObjectOutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
    }

    /**
     * Write object on output stream
     * @param object object to write
     * @return  return true if object has been written properly
     */

    public synchronized boolean writeObject(Object object){
        try {
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(OUTBOUND_CONNECTION_LOGGER + " sending " + ((Response)object).getResponseStatus());
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

