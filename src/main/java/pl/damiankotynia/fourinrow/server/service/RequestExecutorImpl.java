package pl.damiankotynia.fourinrow.server.service;

import pl.damiankotynia.fourinrow.model.*;
import pl.damiankotynia.fourinrow.server.connector.OutboundConnection;
import pl.damiankotynia.fourinrow.server.database.Database;
import pl.damiankotynia.fourinrow.server.exceptions.LackOfPlayersException;

public class RequestExecutorImpl implements RequestExecutor {
    private RequestExecutorImpl oponentsRequestExecutor;
    private OutboundConnection outboundConnection;
    private Database database;
    //TODO USTAWIANIE GRACZA
    private Player player;



    public RequestExecutorImpl(OutboundConnection outboundConnection){
        this.outboundConnection = outboundConnection;
    }

    @Override
    public void executeRequest(Request request){
        Response response;
        switch (request.getRequestType()){
            case MESSAGE:
                response = requestToResponse(request);
                if (response==null)
                    break;
                this.sendResponse(response);
                break;
            case MOVE:
                //TODO WYKONANIE RUCHU


                break;
            case FIND_GAME:
                database = Database.getInstance();
                database.save(this.player);
                try {
                    database.getRandomPlayer(player);
                } catch (LackOfPlayersException e) {
                    response = new Response();
                    response.setResponseStatus(ResponseStatus.NO_PLAYERS);
                }

                break;
        }
    }



    @Override
    public void sendResponse(Response response) {
        outboundConnection.writeObject(response);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    //TODO zamiany request <=> response
    public Response requestToResponse(Request request){

        switch (request.getRequestType()){
            case MESSAGE:
                //TODO WYSYŁKA WIDOMOŚCI DO DRUGIEGO GRACZA
                MessageRequest messageRequest = (MessageRequest)request;
                MessageResponse response = new MessageResponse();
                response.setResponseStatus(ResponseStatus.MESSAGE);
                if(Utils.isEmpty(messageRequest.getMessage())){
                    response.setMessage(" ");
                }else {
                    response.setMessage(messageRequest.getMessage());
                }
                return response;

            case MOVE:
                //TODO WYKONANIE RUCHU
                break;

        }
        return null;
    }

    public Request responseToRequest(Response response){

        switch (response.getResponseStatus()){
            case MESSAGE:
                //TODO WYSYŁKA WIDOMOŚCI DO DRUGIEGO GRACZA
                MessageResponse messageResponse = (MessageResponse)response;
                MessageRequest request = new MessageRequest();
                request.setRequestType(RequestType.MESSAGE);
                if (Utils.isEmpty(messageResponse.getMessage())){
                    request.setMessage(" ");
                }else{
                    request.setMessage(messageResponse.getMessage());
                }

                return request;

            case MOVE:
                //TODO WYKONANIE RUCHU
                break;

        }
        return null;
    }


}
