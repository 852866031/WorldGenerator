public class Crowd {
    private NPC[] NPCs;
    public int population;
    public int removed=0;
    public int infected=0;
    public int recovered=0;
    public Crowd(int population) {
        this.population=population;
        this.NPCs=new NPC[population];
        for(int i=0; i<population; i++) {
            this.NPCs[i]=new NPC(i+1);
        }
    }
    public void remove(int code) {
        NPC[] npcs=new NPC[this.population-1];
        int j=0;
        for(int i=0; i<this.population;i++) {
            if(this.NPCs[i].getCode()!=code) {
                npcs[j]=this.NPCs[i];
                j++;
            }
        }
        this.NPCs=npcs;
        this.population--;
        this.codeCheck();
        this.removed++;
        if(this.infected>0) this.infected--;
    }
    public void add(int num) {
        NPC[] nNPCs=new NPC[this.NPCs.length+num];
        for(int i=0; i<this.NPCs.length; i++) nNPCs[i]=this.NPCs[i];
        for(int i=this.NPCs.length; i<this.NPCs.length+num; i++) nNPCs[i]=new NPC(i+1);
        this.NPCs=nNPCs;
        this.population+=num;
        this.codeCheck();
    }
    public void codeCheck(int code) {
        int num=0;
        for(int i=0; i<this.NPCs.length; i++) {
            if(this.NPCs[i].getCode()==code) num++;
        }
        if(num>1) throw new NPCDuplicatedError("This NPC has been created");
    }
    public void codeCheck() {
        for(int i=0; i<this.NPCs.length; i++) {
            codeCheck(this.NPCs[i].getCode());
        }
    }
    public void CrowdInformation() {
        System.out.println("There are "+this.population+" people in the crowd, alive");
        System.out.println(this.NPCs.length+" NPC objects in total have been created");
        System.out.println(this.removed+" people have been removed");
        System.out.println("Below is their status");
        for(int i=0; i<this.NPCs.length; i++) {
            System.out.println("Code number "+this.NPCs[i].getCode()+" person's status: "+this.NPCs[i].getStatus()+" at position ["+this.getNPCs()[i].getPosition()[0]+", "+this.getNPCs()[i].getPosition()[1]+"]");
        }
    }
    public NPC[] getNPCs() {
        return this.NPCs;
    }
    public boolean checkInfectionAt(int[] position) {
        for(int i=0; i<this.NPCs.length; i++) {
            if(this.NPCs[i].getPosition()[0]==position[0] && this.NPCs[i].getPosition()[1]==position[1] && this.NPCs[i].getStatus()=="Infected"){
                return true;
            }
        }
        return false;
    }
    public NPC[] getNPCByPosition(int[] position) {
        NPC[] npcs=new NPC[0];
        for(int i=0; i<this.NPCs.length; i++) {
            if(this.NPCs[i].getPosition()[0]==position[0] && this.NPCs[i].getPosition()[1]==position[1]) {
                npcs=append(npcs, this.NPCs[i]);
            }
        }
        return npcs;
    }
    public int checkInfected() {
        return this.infected;
    }
    private NPC[] append(NPC[] npcs, NPC npc){
        NPC[] npcs1=new NPC[npcs.length+1];
        for(int i=0; i<npcs.length; i++) {
            npcs1[i]=npcs[i];
        }
        npcs1[npcs1.length-1]=npc;
        return npcs1;
    }
    class NPCDuplicatedError extends RuntimeException {
        String name;

        public NPCDuplicatedError(String n) {
            name = n;
        }

        public String toString() {
            return "NPCDuplicatedError[" + name + "]";
        }
    }
    public static void main(String[] args) {
        Crowd c=new Crowd(10);
        c.remove(10);
        c.add(2);
        c.CrowdInformation();

    }

}
