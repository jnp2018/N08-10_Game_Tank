/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TC
 */
public class Bullet {
    int x;
    int y;
    int direction;
    int u;

    public Bullet(int x, int y, int direction, int u) {
        this.x = x*60+26;
        this.y = y*60+26;
        this.direction = direction;
        this.u = u;
    }
    
    
}
