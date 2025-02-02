import java.io.*;
import java.util.*;

public class ExternalSort {
    private static final int memorySize = 2;
    private static final int numWays = 10;
    private int offset = 0;

    public void split(File arquivo) throws IOException{
        FileReader fileReader = new FileReader(arquivo);
        BufferedReader reader = new BufferedReader(fileReader);

        boolean end = false;
        while(!end) {

            double[] arr1 = createArray(reader);
            HeapSort.heapSort(arr1);
            // salvar em um arquivo

            this.saveFile(arr1, "arq1.txt");
        }


    }

    private double[] createArray(BufferedReader reader) throws IOException {
        double[] records = new double[memorySize];
        String line;

        for (int i = 0; i < memorySize; i++) {
            line = reader.readLine();
            records[i] = Double.parseDouble(line);
            offset++;
        }
        return records;
    }

    private void saveFile(double[] arr, String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));

        for (double line : arr) {
            writer.write(String.valueOf(line));
            writer.newLine();
        }
    }

    private void mergeFile(String fileName1, String fileName2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(fileName1));
        BufferedReader reader2 = new BufferedReader(new FileReader(fileName2));
        BufferedWriter writer = new BufferedWriter(new FileWriter("merged.txt"));

        String fileOneLine = reader1.readLine();
        String fileTwoLine = reader2.readLine();
        boolean execute = true;
        while (execute) {

            if (fileOneLine == null) {
                String line2 = fileTwoLine;

                execute = line2 != null;

                while (line2 != null) {
                    writer.write(line2);
                    writer.newLine();

                    line2 = reader2.readLine();
                };
            }
            if (fileTwoLine == null) {
                String line1 = fileOneLine;

                execute = line1 != null;

                while (line1 != null) {
                    writer.write(line1);
                    writer.newLine();

                    line1 = reader1.readLine();
                };
            }

            // caminho feliz
            assert fileOneLine != null;
            assert fileTwoLine != null;
            if (Double.parseDouble(fileOneLine) < Double.parseDouble(fileTwoLine)) {
                writer.write(fileOneLine);
                writer.newLine();
                fileOneLine = reader1.readLine();
            } else {
                writer.write(fileTwoLine);
                writer.newLine();
                fileTwoLine = reader2.readLine();
            }
        }
    }

}






















