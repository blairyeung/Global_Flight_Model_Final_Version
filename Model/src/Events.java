import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Events {
    public static ArrayList<Event> Even = new ArrayList<>();
    public static ArrayList<String> Ind = new ArrayList<>();
    Scanner Scan = null;
    public Events() {
        File EVENT = new File(Parameters.ReadPath+"FlightCity/EB.csv");
        try {
            Scan = new Scanner(EVENT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> Line = new ArrayList<>();
        while (Scan.hasNext()) {
            String buffer = Scan.nextLine();
            //System.out.println(buffer);
            Line.add(buffer);
            String name = buffer.substring(0,buffer.indexOf(","));
            buffer = buffer.substring(buffer.indexOf(",")+1);
            int death = Integer.parseInt(buffer.substring(0,buffer.indexOf(",")));
            buffer = buffer.substring(buffer.indexOf(",")+1);
            int speed = Integer.parseInt(buffer);
            System.out.print(name+",");
            System.out.print(death+",");
            System.out.println(speed);
            Even.add(new Event(name,death,speed));
            Ind.add(name);
        }
    }
    public static void main(String[] args){
        Events event = new Events();
    }
}


