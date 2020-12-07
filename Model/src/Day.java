public class Day {
    private int day;
    private int population;
    private int infected;
    private int exposed;
    private int activecases;
    private int resolved;
    private int death;
    private int immuned;
    private double LOCKDOWN;
    public Day(int day,int population, int infected, int exposed, int activecases,int resolved,int death, double lockdown,int immuned){
        this.day = day;
        this.population = population;
        this.infected = infected;
        this.exposed = exposed;
        this.activecases = activecases;
        this.resolved = resolved;
        this.death = death;
        this.LOCKDOWN = lockdown;
        this.immuned = immuned;
        //Hour[] hours = new Hour[24];
    }
    public Day(){
        this.day = 0;
        this.population = 1;
        this.infected = 1;
        this.exposed = 1;
        this.activecases = 1;
        this.resolved = 1;
        this.death = 1;
        this.LOCKDOWN = 1;
    }
    public int getDay(){return day; }
    public int getInfected(){ return infected; }
    public int getPopulation(){return population; }
    public int getExposed(){return exposed; }
    public int getActivecases(){return activecases; };
    public int getResolved(){return resolved; }
    public int getDeath(){return death; }
    public double getLockdown(){return LOCKDOWN; }
    public int getImmuned(){return immuned; }

}
