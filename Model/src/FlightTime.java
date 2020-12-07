public class FlightTime {
    private int Year;
    private int Month;
    private int Day;
    public FlightTime(){}
    public FlightTime(String Input){
        Year= Integer.parseInt(Input.substring(0,4));
        Month = Integer.parseInt(Input.substring(4,6));
        Day = Integer.parseInt(Input.substring(6));
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
