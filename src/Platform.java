import javax.security.auth.callback.LanguageCallback;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
/* // This platform is to put a crowd on world and a virus on crowd to begin a simulation
      Author: Libert
      Date: 3/10/2020: Initial version
 */
public class Platform {
    // This class is to put Crowd on World and put virus on crowd
    private Crowd crowd;
    private World world;
    private Virus virus;
    private int date=0;
    private Random rand=new Random();
    public Platform(Crowd c, World w, Virus v) {
        this.crowd=c;
        this.world=w;
        this.virus=v;
    }
    public void setCrowd() {
        int p;
        NPC[] NPCs=this.crowd.getNPCs();
        int[][] lands=this.world.getLands();
        for(int i=0; i<NPCs.length; i++) {
            p = this.rand.nextInt(lands.length);
            NPCs[i].setPosition(lands[p][0], lands[p][1]);
        }
    }
    public void setCrowd(JLabel[][] LabelArray) {
        int p;
        int[] position=new int[2];
        NPC[] NPCs=this.crowd.getNPCs();
        int[][] lands=this.world.getLands();
        for(int i=0; i<NPCs.length; i++) {
            p = this.rand.nextInt(lands.length);
            while(this.world.islandCheck(lands[p])==true){
                p = this.rand.nextInt(lands.length);
            }
            position[0]=lands[p][0];
            position[1]=lands[p][0];
            NPCs[i].setPosition(lands[p][0], lands[p][1]);
            try{
                Thread.sleep(50);
            }catch(Exception e){ }
            LabelArray[lands[p][0]][lands[p][1]].setText("A");
            LabelArray[lands[p][0]][lands[p][1]].setForeground(Color.blue);
        }
    }
    public int hasNPCAlive(int[] position) {
        NPC[] npcs=this.crowd.getNPCs();
        int b=0;
        for(int i=0; i<npcs.length; i++) {
            if(!npcs[i].getStatus().contentEquals("Removed")){
                if(npcs[i].getPosition()[0]==position[0] && npcs[i].getPosition()[1]==position[1]) b++;
            }
        }
        return b;
    }
    public JLabel[][] show() {
        JLabel[][] LabelArray=this.world.show();
        int[] position=new int[2];
        for(int i=0; i<this.world.getSize()[0]; i++) {
            for(int j=0; j< this.world.getSize()[1]; j++) {
                position[0]=i;
                position[1]=j;
                if(this.hasNPCAlive(position)!=0){
                    LabelArray[i][j].setText("A*"+this.hasNPCAlive(position));
                    LabelArray[i][j].setForeground(Color.blue);
                    if(this.crowd.checkInfectionAt(position)) LabelArray[i][j].setBackground(Color.red);
                }
                else{
                    LabelArray[i][j].setText("");
                }
            }
        }

        return LabelArray;
    }
    public JLabel[][] show(JLabel[][] LabelArray) {
        int[] position=new int[2];
        for(int i=0; i<this.world.getSize()[0]; i++) {
            for(int j=0; j< this.world.getSize()[1]; j++) {
                position[0]=i;
                position[1]=j;
                if(this.hasNPCAlive(position)!=0){
                    LabelArray[i][j].setText("A*"+this.hasNPCAlive(position));
                    LabelArray[i][j].setForeground(Color.blue);
                    if(this.crowd.checkInfectionAt(position)) LabelArray[i][j].setForeground(Color.red);
                }
                else{
                    LabelArray[i][j].setText("");
                }
            }
        }
        return LabelArray;
    }
    public int walkInWorld(NPC npc){
        int num=0;
        int d;
        boolean[] direction=new boolean[4];
        for(int i=0; i<4; i++) direction[i]=false;
        int[] position=npc.getPosition();
        if(position[0]-1>=0) {
            if(world.getMatrix()[position[0]-1][position[1]]>0) {
                direction[0]=true;
                num++;
            }
        }
        if(position[1]-1>=0) {
            if(world.getMatrix()[position[0]][position[1]-1]>0) {
                direction[1]=true;
                num++;
            }
        }
        if(position[0]+1<this.world.getSize()[0]) {
            if(world.getMatrix()[position[0]+1][position[1]]>0) {
                direction[2]=true;
                num++;
            }
        }
        if(position[1]+1<this.world.getSize()[1]) {
            if(world.getMatrix()[position[0]][position[1]+1]>0) {
                direction[3]=true;
                num++;
            }
        }
        int x=this.rand.nextInt(4);
        if(num>0){
            while(direction[x]==false) x=this.rand.nextInt(4);
            if(x==0) npc.setPosition(position[0]-1, position[1]);
            else if(x==1) npc.setPosition(position[0], position[1]-1);
            else if(x==2) npc.setPosition(position[0]+1, position[1]);
            else if(x==3) npc.setPosition(position[0],position[1]+1);
            return 1;
            // if the npc moved we return 0 to show it moved successfully
        }
        else{
            return 0;
            //if it returns 0, the npc is on the sea
        }
    }
    public void Spread(){
        int[][] land=this.world.getLands();
        int[] position=new int[2];
        double x;
        for(int i=0; i<land.length; i++) {
            position[0]=land[i][0];
            position[1]=land[i][1];
            NPC[] people=this.crowd.getNPCByPosition(position);
            if(people.length>1) {
                for(int k=0; k<people.length; k++) {
                    if(people[k].getStatus().contentEquals("Infected")){
                        for(int l=0; l<people.length; l++) {
                            x=this.rand.nextInt(100);
                            x=x/100;
                            if(x<this.virus.getInfectRate()) {
                                if(people[l].getStatus().contentEquals("Healthy")) {
                                    people[l].setInfected();
                                    this.crowd.infected++;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    public void setInfection() {
        int x=this.rand.nextInt(this.crowd.getNPCs().length);
        while(this.crowd.getNPCs()[x].getStatus().contentEquals("Removed")) {
            x=this.rand.nextInt(this.crowd.getNPCs().length);
        }
        this.crowd.getNPCs()[x].setInfected();
        this.crowd.infected++;
    }
    /*public void timePass(int time) {
        for(int j=0; j<time; j++) {
            NPC[] npcs = this.crowd.getNPCs();
            for (int i = 0; i < npcs.length; i++) {
                if (!npcs[i].getStatus().contentEquals("Removed")) this.walkInWorld(npcs[i]);
            }
            this.Spread();
            this.updatePeriod();
        }
    }*/
    public void timePass(int time, JLabel[][] LabelArray, JLabel[] labels) {
        for(int j=0; j<time*4; j++) {
            this.updateInfoPanel(labels);
            NPC[] npcs=this.crowd.getNPCs();
            for(int i=0; i<npcs.length; i++) {
                if(!npcs[i].getStatus().contentEquals("Removed"))  this.walkInWorld(npcs[i]);
            }
            this.Spread();
            if((j+1)%3==0) this.updatePeriod();
            for(int i=0; i<npcs.length; i++) {
                if(npcs[i].getStatus().contentEquals("Infected") && npcs[i].getPeriod()==(this.virus.getPeriod()+this.virus.getLatency())) {
                    if(this.virus.checkDeath()) {
                        this.crowd.remove(npcs[i].remove());
                    }
                    else {
                        npcs[i].setRecover();
                        if(this.crowd.infected>0) this.crowd.infected--;
                        this.crowd.recovered++;
                    }
                }
            }
            this.show(LabelArray);
        }

    }
    public void updatePeriod() {
        this.date++;
        NPC[] npcs=this.crowd.getNPCs();
        for(int i=0; i<npcs.length; i++) {
            if(npcs[i].getStatus().contentEquals("Infected")) {
                npcs[i].prolong();
            }
        }
    }
    public JLabel[] infoPanel() {
        JFrame infoPanel = new JFrame("Infomation");
        infoPanel.setSize(380,200);
        infoPanel.setLayout(new GridLayout(1, 1));
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(5, 1));
        infoPanel.add(grid);
        JLabel[] labels=new JLabel[5];
        for(int i=0; i<5; i++) {
            JLabel current=new JLabel("",SwingConstants.CENTER);
            current.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            current.setOpaque(true);
            if(i==0) current.setText("Date: "+ this.date);
            else if(i==1) current.setText("Population: "+this.crowd.population);
            else if(i==2) current.setText("Infected: "+this.crowd.infected);
            else if(i==3) current.setText("Dead: "+this.crowd.removed);
            else if(i==4) current.setText("Recovered: "+this.crowd.recovered);
            labels[i]=current;
            grid.add(labels[i]);
        }
        infoPanel.setVisible(true);
        return labels;
    }
    public void updateInfoPanel(JLabel[] labels) {
        for(int i=0; i<4; i++) {
            if(i==0) labels[i].setText("Date: "+this.date);
            else if (i == 1) labels[i].setText("Population: " + this.crowd.population);
            else if (i == 2) labels[i].setText("Infected: " + this.crowd.infected);
            else if (i == 3) labels[i].setText("Dead: " + this.crowd.removed);
            else if (i == 4) labels[i].setText("Recovered: " + this.crowd.recovered);
        }
    }
    public static void main(String[] args) {
        Crowd c=new Crowd(100);
        World w=new World(30, 56, 13);
        Virus v=new Virus();
        JLabel[][] LabelArray=w.show();
        for(int i=0; i<15; i++) w.VolcanoMethod(150, LabelArray);
        w.clean(LabelArray);
        Platform p=new Platform(c, w, v);
        JLabel[] labels=p.infoPanel();
        p.setCrowd(LabelArray);
        for(int i=0; i<10; i++) p.setInfection();
        p.timePass(5000, LabelArray, labels);
    }
}
