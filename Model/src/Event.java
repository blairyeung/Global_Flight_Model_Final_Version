public class Event {
    private String Name;
    private int Death;
    private int Speed;
    public Event(){
        this.Name = null;
        this.Death = 0;
        this.Speed = 0;
    }
    public Event(String name,int death, int speed){
        this.Name = name;
        this.Death = death;
        this.Speed = speed;
    }
    public String getName(){
        return Name;
    }
    public int getDeath(){
        return Death;
    }
    public int getSpeed(){
        return Speed;
    }
    public void setName(String name){
        this.Name = name;
    }
    public void setDeath(int death){
        this.Death = death;
    }
    public  void setSpeed(int speed){
        this.Speed = speed;
    }
}
