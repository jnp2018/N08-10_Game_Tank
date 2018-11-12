
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TC
 */
public class Game {

    String user1 = null;
    String user2 = null;
    int status = 0;//0: pending, 1: playing, 2: end
    //int map[][] = new int[10][10];//0: duong, 1: cay

    int map[][] = {{0,1,1,0,0,0,0,1,0,0},
    {0,0,0,0,0,0,0,1,0,1},
   {1,1,1,1,1,1,0,1,0,0},
   {0,0,0,0,0,1,0,1,0,1},
   {0,1,1,1,0,1,0,0,0,0},
   {0,0,0,0,0,0,0,1,0,1},
   {0,0,0,0,0,0,0,0,0,0},
   {0,0,0,0,0,1,0,0,0,0},
   {0,1,0,1,0,0,0,0,0,0},
   {1,1,1,1,1,1,1,1,1,0}};
    
    ArrayList<Bullet> bullets = new ArrayList<>();
    
    int x_user[] = new int[10];
    int y_user[] = new int[10];
    int direction[] = new int[10];
    
    BulletHandler bullet_handle;

    public Game(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
        
        direction[1] = 3;
        x_user[1]  = 0;
        y_user[1]  = 0;
        
        direction[2] = 1;
        x_user[2]  = 9;
        y_user[2]  = 9;
    }
    
    private int get_tank_color(String user){
        return Data.getUser(user).getTank_color();
    }
    
    private int get_bullet_color(String user){
        return Data.getUser(user).getBullet_color();
    }
    
    public void start(int gID){
        
        String tank = "{\""+user1+"\":{\"direction\":"+direction[1]+" , \"x\":"+x_user[1]+", \"y\":"+y_user[1]+", \"tank_color\":\""+get_tank_color(user1)+"\", \"bullet_color\":\""+get_bullet_color(user1)+"\"},\""+user2+"\":{\"direction\":"+direction[2]+" , \"x\":"+x_user[2]+", \"y\":"+y_user[2]+", \"tank_color\":\""+get_tank_color(user2)+"\", \"bullet_color\":\""+get_bullet_color(user2)+"\"}}";
     
        MessageHandler.sendStart(user1, tank);
        MessageHandler.sendStart(user2, tank);
        
        bullet_handle = new BulletHandler(gID);
        bullet_handle.start();
        
    }
    
    public boolean u1u2(int u, int x, int y){
        int _u = 1;
        if(u == 1){
            _u = 2;
        }
        
        if(x_user[_u] == x && y_user[_u] == y){
            return true;
        }
        return false;
    }

    public void action(String user, String action) {

        System.out.println("action");

        System.out.println(user);
        
        int u = 2;

        if (user.equals(user1)) {
            u = 1;
        }

        System.out.println(action);

        if (action.equals("37")) {
            if (x_user[u] > 0 && map[y_user[u]][x_user[u]-1] == 0) {
                if(direction[u] == 4 && !u1u2(u, x_user[u]-1, y_user[u])){
                    x_user[u] -= 1;
                } else {
                    direction[u] = 4;
                }
                MessageHandler.sendToClient(this.user1, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
                MessageHandler.sendToClient(this.user2, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
            }
        } else if (action.equals("38")) {
            if (y_user[u] > 0 && map[y_user[u]-1][x_user[u]] == 0) {
                if(direction[u] == 1 && !u1u2(u, x_user[u], y_user[u]-1)){
                    y_user[u] -= 1;
                } else {
                    direction[u] = 1;
                }
                MessageHandler.sendToClient(this.user1, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
                MessageHandler.sendToClient(this.user2, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
            }
        } else if (action.equals("39")) {
            if (x_user[u] < 9 && map[y_user[u]][x_user[u]+1] == 0) {
                if(direction[u] == 2 && !u1u2(u, x_user[u]+1, y_user[u])){
                    x_user[u] += 1;
                } else {
                    direction[u] = 2;
                }
                MessageHandler.sendToClient(this.user1, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
                MessageHandler.sendToClient(this.user2, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
            }
        } else if (action.equals("40")) {
            if (y_user[u] < 9 && map[y_user[u]+1][x_user[u]] == 0) {
                if(direction[u] == 3 && !u1u2(u, x_user[u], y_user[u]+1)){
                    y_user[u] += 1;
                } else {
                    direction[u] = 3;
                }
                MessageHandler.sendToClient(this.user1, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
                MessageHandler.sendToClient(this.user2, "{\"type\":\"update_pos\",  \"uuid\":\""+user+"\", \"pos\":["+x_user[u]+", "+y_user[u]+", "+direction[u]+"]}");
            }
        } else if (action.equals("32")) {
            //xu ly su kien ban
            
            bullets.add(new Bullet(x_user[u], y_user[u], direction[u], u));
            
            String uid = this.user1;
            if(u==2){
                uid = this.user2;
            }
            
            MessageHandler.sendToClient(this.user1, "{\"type\":\"shoot\",  \"x\":\""+x_user[u]+"\", \"y\":\""+y_user[u]+"\", \"direction\":\""+direction[u]+"\", \"bullet_color\":\""+get_bullet_color(uid)+"\"}");
            MessageHandler.sendToClient(this.user2, "{\"type\":\"shoot\",  \"x\":\""+x_user[u]+"\", \"y\":\""+y_user[u]+"\", \"direction\":\""+direction[u]+"\", \"bullet_color\":\""+get_bullet_color(uid)+"\"}");
            
        }

    }

    public void endGame() {
        this.status = 2;
    }
}
