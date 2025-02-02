import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        ExternalSort sort = new ExternalSort();

        File inputFile = new File("ordExt_teste.txt");
        sort.split(inputFile);



    }
}
