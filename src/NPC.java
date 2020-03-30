import javax.swing.*;

public class NPC {
    private int speed;
    //this is the movement speed of the npc
    private int[] position=new int[2];
    //this is the position of npc
    private int code;
    //code should be start from 1. code is to record how many npc objects have been created in total
    private String status="Healthy";
    private int period=0;
    public NPC(int x, int y, int speed, int code) {
        this.speed=speed;
        this.position[0]=x;
        this.position[1]=y;
        this.code=code;
    }
    //this is to create a npc object with detailed information
    public NPC(int code) {
        this.code=code;
    }
    public int[] getPosition() {
        return this.position;
    }
    public int getCode() {
        return this.code;
    }
    public void setSpeed(int speed) {
        this.speed=speed;
    }
    public int getSpeed() {
        return this.speed;
    }
    public void setPosition(int x, int y) {
        this.position[0]=x;
        this.position[1]=y;
    }
    public int remove() {
        this.status="Removed";
        return this.code;
    }
    public String getStatus(){
        return this.status;
    }
    public void setInfected() {
        this.status="Infected";
    }
    public void setRecover() {this.status="Recover";}
    public int getPeriod() {
        return this.period;
    }
    public void prolong() {
        this.period++;
    }
}
