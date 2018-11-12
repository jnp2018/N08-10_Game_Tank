
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TC
 */
public class BulletHandler extends Thread {

    int gameID;

    public BulletHandler(int gameID) {
        this.gameID = gameID;
    }
    
    
    @Override
    public void run() {
        while(true){
            for(int i=0;i<Data.games.get(gameID).bullets.size();i++){
                //System.out.println(Data.games.get(gameID).bullets.get(i).x +" "+Data.games.get(gameID).bullets.get(i).y);
                
                int direction = Data.games.get(gameID).bullets.get(i).direction;
                switch (direction) {
                    case 1:
                        Data.games.get(gameID).bullets.get(i).y -= 10;
                        break;
                    case 2:
                        Data.games.get(gameID).bullets.get(i).x += 10;
                        break;
                    case 3:
                        Data.games.get(gameID).bullets.get(i).y += 10;
                        break;
                    case 4:
                        Data.games.get(gameID).bullets.get(i).x -= 10;
                        break;
                    default:
                        break;
                }
                
                int _u = 2;
                if(Data.games.get(gameID).bullets.get(i).u==2){
                    _u = 1;
                }
                
                
                if(Data.games.get(gameID).x_user[_u]*60 <= Data.games.get(gameID).bullets.get(i).x && 
                   Data.games.get(gameID).x_user[_u]*60+60 >= Data.games.get(gameID).bullets.get(i).x && 
                   Data.games.get(gameID).y_user[_u]*60 <= Data.games.get(gameID).bullets.get(i).y &&
                   Data.games.get(gameID).y_user[_u]*60+60 >= Data.games.get(gameID).bullets.get(i).y){
                    System.out.println("kill");
                    if(_u == 1){
                        MessageHandler.sendWin(Data.games.get(gameID).user2);
                        MessageHandler.sendLoose(Data.games.get(gameID).user1);
                    } else {
                        MessageHandler.sendWin(Data.games.get(gameID).user1);
                        MessageHandler.sendLoose(Data.games.get(gameID).user2);
                    }
                    
                    Data.rooms.get(Data.UserID_RoomID.get(Data.games.get(gameID).user1)).start();
                            
                    Data.games.get(gameID).bullets.remove(i);
                    i--;
                }
                
                
                if(Data.games.get(gameID).map[Data.games.get(gameID).bullets.get(i).x/60][Data.games.get(gameID).bullets.get(i).y/60] == 1){
//                    Data.games.get(gameID).bullets.remove(i);
//                    i--;
                    System.out.println("cay");

                }
                
                if(Data.games.get(gameID).bullets.get(i).x > 600 || Data.games.get(gameID).bullets.get(i).x < -60 || Data.games.get(gameID).bullets.get(i).y > 600 || Data.games.get(gameID).bullets.get(i).y < -60){
                    Data.games.get(gameID).bullets.remove(i);
                    i--;
                }
                
            }
            
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(BulletHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
