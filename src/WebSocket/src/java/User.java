/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TC
 */
public class User {
    private String name;
    private int tank_color; //0: xanh, 1: do, 2: den
    private int bullet_color;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTank_color() {
        return tank_color;
    }

    public void setTank_color(int tank_color) {
        this.tank_color = tank_color;
    }

    public int getBullet_color() {
        return bullet_color;
    }

    public void setBullet_color(int bullet_color) {
        this.bullet_color = bullet_color;
    }
    
    
    
}
