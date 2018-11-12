
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@ServerEndpoint("/ws123")
public class WS {

    @OnOpen
    public void open(Session session) {
        System.out.println("new connection");

//        int gameID = Game.getPendingGame();
//        if (gameID == -1) {
//            Data.games.add(new Game(session.getId()));
//            gameID = Data.games.size() - 1;
//        } else {
//            Data.games.get(gameID).addUser(session.getId());
//        }

//        Data.UserID_GameID.put(session.getId(), gameID);

        System.out.println(session.getId());
        //put session theo id va map
        Data.sessions.put(session.getId(), session);
        Data.UserID_User.put(session.getId(), new User());
        Data.UserID_RoomID.put(session.getId(), -1);
        
    }

    @OnClose
    public void close(Session session) {

        System.out.println("close connection");

        Data.sessions.remove(session.getId());
        Data.UserID_User.remove(session.getId());
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try {
            JSONParser parser = new JSONParser();

            JSONObject obj = (JSONObject) parser.parse(message.trim());

            
            if (obj.get("type").equals("login")) {
                
                String name = "", tank_color = "", bullet_color = "";
                name = (String) obj.get("name");
                tank_color = (String) obj.get("tank_color");
                bullet_color = (String) obj.get("bullet_color");
                
                //System.out.println("login "+name);
                Data.getUser(session.getId()).setName(name);
                Data.getUser(session.getId()).setTank_color(Integer.parseInt(tank_color));
                Data.getUser(session.getId()).setBullet_color(Integer.parseInt(bullet_color));
                
                
                MessageHandler.sendListRoom(session.getId());
                
            } else if (obj.get("type").equals("room_crate")) {
            
                String room_name = "";
                room_name = (String) obj.get("room_name");
                Data.rooms.add(new Room(room_name, session.getId()));

                
                int roomID = (Data.rooms.size()-1);
                System.out.println(Data.rooms.get(roomID).getUser1());
                
                Data.UserID_RoomID.put(session.getId(), roomID);
                
                //gui lai list room cho cac user
                MessageHandler.broadcastListRoom();
                
            } else if (obj.get("type").equals("join_room")) {
                String roomID = "";
                roomID = (String) obj.get("roomID");
                
                if(Data.getRoom(Integer.parseInt(roomID)) != null && !Data.getRoom(Integer.parseInt(roomID)).isFull() && Data.UserID_RoomID.get(session.getId()) == -1){
                    
                    Data.getRoom(Integer.parseInt(roomID)).setUser2(session.getId());
                    Data.UserID_RoomID.put(session.getId(), Integer.parseInt(roomID));
                    
                    System.out.println("join room "+roomID);
                    
                    MessageHandler.sendJoinRoom(session.getId(), Data.getRoom(Integer.parseInt(roomID)).getRoom_name());
                    
                    String user1 = "...", user2 = "...";
                    if(!Data.getRoom(Integer.parseInt(roomID)).getUser1().equals("")){
                        user1= Data.getUser(Data.getRoom(Integer.parseInt(roomID)).getUser1()).getName();
                    }
                    if(!Data.getRoom(Integer.parseInt(roomID)).getUser1().equals("")){
                        user2= Data.getUser(Data.getRoom(Integer.parseInt(roomID)).getUser2()).getName();
                    }
                    
                    MessageHandler.roomUpdate(Data.getRoom(Integer.parseInt(roomID)).getUser1(), user1+" - "+user2);
                    MessageHandler.roomUpdate(Data.getRoom(Integer.parseInt(roomID)).getUser2(), user1+" - "+user2);
                    
                    if(Data.getRoom(Integer.parseInt(roomID)).isFull()){
                        Data.getRoom(Integer.parseInt(roomID)).start();
                    }
                    
                } else {
                    //room is full
                }
                
                
            } else if (obj.get("type").equals("leave_room")) {
                
                Integer roomID = Data.UserID_RoomID.get(session.getId());
                if(roomID != -1){
                    if(Data.getRoom(roomID).getUser1().equals(session.getId())){
                        Data.getRoom(roomID).setUser1(Data.getRoom(roomID).getUser2());
                        Data.getRoom(roomID).setUser2("");
                    } else {
                        Data.getRoom(roomID).setUser2("");
                    }
                    Data.UserID_RoomID.put(session.getId(), -1);

                    //xu ly su kien roi phong, neu phong trong thi xoa phong va gui lai list room cho cac user
                    System.out.println("leave room");
                }
                
            } else if (obj.get("type").equals("keypress")) {
                int roomID = Data.UserID_RoomID.get(session.getId());
                System.out.println(roomID);
                int gameID = Data.rooms.get(roomID).getGameID();
                System.out.println(gameID);
                String key = (String) obj.get("key");
                Data.games.get(gameID).action(session.getId(), key);
                
            }
            
        } catch (Exception ex) {
            System.out.println("error " + ex);
        }

        //System.out.println(message);
        
    }
}
