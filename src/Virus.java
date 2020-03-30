import java.util.Random;
import java.util.Scanner;

public class Virus {
    private String name;
    private double infectRate;
    private double deathRate;
    private int period;
    private int latency;
    public Virus() {
        Scanner scan=new Scanner(System.in);
        System.out.println("The name of the virus is: ");
        this.name=scan.nextLine();
        System.out.println("The infect rate of the virus is: ");
        System.out.println("(Please put in a number between 0 and 1)");
        this.infectRate=Double.parseDouble(scan.nextLine());
        System.out.println("The death rate of the virus is: ");
        System.out.println("(Please put in a number between 0 and 1)");
        this.deathRate=Double.parseDouble(scan.nextLine());
        System.out.println("The average period of the virus is: ");
        this.period=Integer.parseInt(scan.nextLine());
        System.out.println("The latency of the virus is: ");
        this.latency=Integer.parseInt(scan.nextLine());
        System.out.println("Virus "+this.name+" has been initialized");
    }
    public Virus(int i) {
        this.name="a";
        this.infectRate=1;
        this.deathRate=1;
        this.period=7;
        this.latency=7;
    }
    public double getInfectRate() {
        return this.infectRate;
    }
    public int getPeriod() {
        return this.period;
    }
    public double getDeathRate() { return this.deathRate; }
    public int getLatency() {return  this.latency;}
    public boolean checkDeath() {
        Random rand=new Random();
        int x=rand.nextInt(100);
        if(x<this.deathRate*100) return true;
        else return false;
    }
}
