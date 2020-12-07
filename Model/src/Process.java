public class Process {
    public static void main(String[] args) throws Exception{
        IO io = new IO();
        TraceO t = new TraceO();
        for (int i = 0; i < Parameters.Count; i++) {
            System.out.println("File Code: " + (i+1));
            ToCountry T= new ToCountry(i+1);
        }
        ToDaily to = new ToDaily();
        GraphMaker G = new GraphMaker();
        READCSV read = new READCSV();
    }
}
