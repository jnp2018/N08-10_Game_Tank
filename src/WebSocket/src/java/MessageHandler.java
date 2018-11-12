
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author TC
 */
public class MessageHandler {

    public static boolean sendToClient(String session_id, String message) {
        try {
            Data.getClient(session_id).getBasicRemote().sendText(message);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(WS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean sendListRoom(String session_id){
    
        String s = "{\"type\":\"list_room\", \"data\": [";
        
        for(int i=0;i<Data.rooms.size();i++){
        
            s += "{\"roomID\": \""+i+"\", \"name\": \""+Data.rooms.get(i).getRoom_name()+"\"}";
            if(i!=Data.rooms.size()-1){
                s+=",";
            }
            //System.out.println(Data.rooms.get(i).getRoom_name());
        }
        
        s+= "]}";
        
        System.out.println(s);
        
        return sendToClient(session_id, s);
    
    }
    
    public static boolean sendJoinRoom(String session_id, String roomName){
        String s = "{\"type\":\"join_room\", \"roomName\": \""+roomName+"\"}";
        return sendToClient(session_id, s);
    }
    
    public static boolean roomUpdate(String session_id, String roomName){
        String s = "{\"type\":\"room_update\", \"data\": \""+roomName+"\"}";
        return sendToClient(session_id, s);
    }
    
    public static boolean sendStart(String session_id, String tank){
        String s = "{\"type\":\"start\", \"tank\": "+tank+"}";
        return sendToClient(session_id, s);
    }
    
    public static boolean broadcastListRoom(){
        
        for (Map.Entry<String, Session> en : Data.sessions.entrySet()) {
            String key = en.getKey();
            Session value = en.getValue();
            sendListRoom(key);
        }
        
        return true;
    }

    public static boolean sendWin(String session_id){
    
        String s = "{\"type\":\"win\"}";
        return sendToClient(session_id, s);
        
    }
    
    public static boolean sendLoose(String session_id){
        String s = "{\"type\":\"loose\"}";
        return sendToClient(session_id, s);
    }
    
}
