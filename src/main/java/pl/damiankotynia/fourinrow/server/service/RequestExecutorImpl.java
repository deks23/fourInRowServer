package pl.damiankotynia.fourinrow.server.service;

import pl.damiankotynia.fourinrow.model.*;
import pl.damiankotynia.fourinrow.server.connector.OutboundConnection;
import pl.damiankotynia.fourinrow.server.database.Database;
import pl.damiankotynia.fourinrow.server.exceptions.LackOfPlayersException;

public class RequestExecutorImpl implements RequestExecutor {
    private RequestExecutorImpl oponentsRequestExecutor;
    private OutboundConnection outboundConnection;
    private Database database;
    private Player player;
    private Game game;



    public RequestExecutorImpl(OutboundConnection outboundConnection){
        this.outboundConnection = outboundConnection;
    }

    /**
     * Executes request recieved from player
     * @param request request to execute
     */
    @Override
    public void executeRequest(Request request){
        Response response;
        if(player==null){
            setPlayer(request.getPlayer());
            player.setRequestExecutor(this);
        }
        switch (request.getRequestType()){

            case MESSAGE:
                response = requestToResponse(request);
                if (response==null)
                    break;
                oponentsRequestExecutor.sendResponse(response);
                break;
            case MOVE:
                MoveRequest moveRequest = (MoveRequest)request;
                game.makeMove(moveRequest.getGameField());
                if(!game.won()){
                    response = new MoveResponse(ResponseStatus.MOVE, game.getGameField());
                    oponentsRequestExecutor.sendResponse(response);
                }else{
                    oponentsRequestExecutor.sendResponse(new Response(ResponseStatus.LOST));
                    game.disconnect();
                    sendResponse(new Response(ResponseStatus.WON));
                }
                break;
            case FIND_GAME:
                database = Database.getInstance();
                database.save(this.player);
                Player oponent = null;
                try {
                    oponent = database.getRandomPlayer(player);
                    game = new Game(this.player, oponent);
                    game.initConnection();
                    outboundConnection.writeObject(new StartGameResponse(ResponseStatus.START,1 ));
                    getOponentsRequestExecutor().sendResponse(new StartGameResponse(ResponseStatus.START,2));
                } catch (LackOfPlayersException e) {
                    response = new Response();
                    response.setResponseStatus(ResponseStatus.NO_PLAYERS);
                    outboundConnection.writeObject(response);
                }

                break;
        }
    }


    /**
     * Sends response to RequestExecutor owner
     * @param response response to send
     */


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

    /**
     * Convert recieved request to response
     * @param request Request to convert
     * @return  Response
     */
    private Response requestToResponse(Request request){

        switch (request.getRequestType()){
            case MESSAGE:
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

                break;

        }
        return null;
    }


    /**
     * Converts Recieved response to request
     * @param response  response to convert
     * @return  request to send
     */
    private Request responseToRequest(Response response){

        switch (response.getResponseStatus()){
            case MESSAGE:
                MessageResponse messageResponse = (MessageResponse)response;
                MessageRequest request = new MessageRequest(player);
                request.setRequestType(RequestType.MESSAGE);
                if (Utils.isEmpty(messageResponse.getMessage())){
                    request.setMessage(" ");
                }else{
                    request.setMessage(messageResponse.getMessage());
                }

                return request;

            case MOVE:

                break;


        }
        return null;
    }

    public void disconetFromOponent(){
        oponentsRequestExecutor = null;
        game = null;
    }

    public RequestExecutorImpl getOponentsRequestExecutor() {
        return oponentsRequestExecutor;
    }

    public void setOponentsRequestExecutor(RequestExecutorImpl oponentsRequestExecutor) {
        this.oponentsRequestExecutor = oponentsRequestExecutor;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
