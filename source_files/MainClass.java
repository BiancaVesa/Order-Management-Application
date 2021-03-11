import java.io.FileNotFoundException;
import presentation.*;

public class MainClass {
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.readAndParseFile(args[0]);
        } catch (FileNotFoundException e1) {
            System.out.println(e1.getMessage());
        }
    }
}
