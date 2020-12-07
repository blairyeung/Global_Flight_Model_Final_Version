public class FlightData {
    private String Code;
    private String Depart;
    private String Arrive;
    private String Time;
    private int Year;
    private int Month;
    private int Day;
    public FlightData(){

    }
    public FlightData(String CODE, String DEPART, String ARRIVE,String TIME){
        this.Code = CODE;
        this.Depart = DEPART;
        this.Arrive = ARRIVE;
        this.Time = TIME;
        Year= Integer.parseInt(TIME.substring(0,4));
        Month = Integer.parseInt(TIME.substring(4,6));
        Day = Integer.parseInt(TIME.substring(6));
    }
    public String getCode(){
        return Code;
    }
    public String getDepart(){
        return Depart;
    }
    public String getArrive(){
        return Arrive;
    }
    public String getTime(){
        return Time;
    }

    public int getYear(){
        return Year;
    }
    public int getMonth(){
        return Month;
    }
    public int getDay(){
        return Day;
    }

}
