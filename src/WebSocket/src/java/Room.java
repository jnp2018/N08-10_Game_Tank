/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TC
 */
public class Room {
    private String room_name = "";
    private String user1 = "";
    private String user2 = "";
    private int gameID =  -1;

    public Room(String room_name, String user1) {
        this.room_name = room_name;
        this.user1  = user1;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
    
    
    
    
    public void start(){
        Data.games.add(new Game(user1, user2));
        int gID = Data.games.size()-1;
        this.gameID = gID;
        Data.games.get(gID).start(gID);
    }
    
    
    
    public boolean isFull(){
        if(user1 != "" && user2 != ""){
            return true;
        }
        return false;
    }
}
