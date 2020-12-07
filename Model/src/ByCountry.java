import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ByCountry {
    public static void main(String[] args) throws Exception{
        File file = new File("E:/Matrix/Max9.csv");
        Scanner scan = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();
        while (scan.hasNext()) {
            String line = scan.nextLine();
            lines.add(line);
        }
      ArrayList<String> CODES = new ArrayList<>();
      ArrayList<String> Band1 = new ArrayList<>();
      ArrayList<String> Band2 = new ArrayList<>();
      ArrayList<Double> Frequencies = new ArrayList<>();
      for (int i = 0; i < lines.size(); i++){
        int ind = i / 16;
        String line = lines.get(i);
        String Code = line.substring(0, line.indexOf(","));
        line = line.substring(line.indexOf(",") + 1);
        String contactband = line.substring(0, line.indexOf(","));
        line = line.substring(line.indexOf(",") + 1);
        String contacteeband = line.substring(0, line.indexOf(","));
        line = line.substring(line.indexOf(",") + 1);
        line = line.substring(line.indexOf(",") + 1);
        String Frequency = line;
        double F = Double.parseDouble(Frequency);
        CODES.add(Code);
        Band1.add(contactband);
        Band2.add(contacteeband);
        Frequencies.add(F);
        }

        ArrayList<String> Country = new ArrayList<>();
        for (int i = 0; i < lines.size(); i+=256) {
            String buff = lines.get(i).substring(0,lines.get(i).indexOf(","));
            Country.add(buff);
        }
        File[] files = new File[Country.size()];
        PrintWriter Printers[] = new PrintWriter[Country.size()];
        String per = "E:/Matrix/Countries NonR/";
        String sur = "SocialMixing.csv";
        String[] groups = {"0 to 4","5 to 9","10 to 14","15 to 19","20 to 24","25 to 29","30 to 34","35 to 39","40 to 44","45 to 49","50 to 54","55 to 59","60 to 64","65 to 69","70 to 74","75+"};
        for (int i = 0; i < Country.size(); i++) {
            String path = per + Country.get(i) + sur;
            files[i] = new File(path);
            Printers[i] = new PrintWriter(files[i]);
            Printers[i].println("Contactant/Contactee,0 to 4,5 to 9,10 to 14,15 to 19,20 to 24,25 to 29,30 to 34,35 to 39,40 to 44,45 to 49,50 to 54,55 to 59,60 to 64,65 to 69,70 to 74,75+");
            for (int i1 = 0; i1 < groups.length; i1++) {
                Printers[i].print(groups[i1]+",");
                for (int i2 = 0; i2 < groups.length; i2++) {
                    int place = i*groups.length*groups.length + i1*groups.length + i2;
                    Printers[i].print(Frequencies.get(place)+",");
                }
                Printers[i].println();
            }
            Printers[i].close();
        }
    }
}
