import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Output {
    public int Numberoutput;
    public Output(){
        Numberoutput = 0;
    }
    public int ReadFile(){
        File file = new File(Parameters.PathDefault+"Output/Index.txt");
        try {
            Scanner scan = new Scanner(file);
            if(scan.hasNext()){
                Numberoutput = scan.nextInt()+1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Numberoutput;
    }

    public void Reset(int num){
        File file = new File(Parameters.PathDefault+"Output/Index.txt");
        try {
            PrintWriter printer = new PrintWriter(file);
            printer.println(num);
            printer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void OutFile(){
        File file = new File(Parameters.PathDefault+"Output/Index.txt");
        try {
            PrintWriter printer = new PrintWriter(file);
            printer.println(Numberoutput);
            printer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Output output = new Output();
        output.ReadFile();
        System.out.println(output.Numberoutput);
    }
}
