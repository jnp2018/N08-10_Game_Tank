
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TC
 */
public class Data {

    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Game> games = new ArrayList<>();
        
    public static Map<String, Integer> UserID_RoomID = new HashMap<>();
    public static Map<String, Session> sessions = new HashMap<>();
    public static Map<String, User> UserID_User = new HashMap<>();

    public static Session getClient(String id) {
        return sessions.get(id);
    }

    public static User getUser(String id) {
        return UserID_User.get(id);
    }
    
    public static Room getRoom(int id){        
        return rooms.get(id);
    }
}