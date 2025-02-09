import java.io.*;
import java.util.ArrayList;

public class ExternalSort {
    private static final int memorySize = 100;
    private static final int numWays = 10;
    private int count = 0;
    private int swap = 0;

    public void split(File arquivo) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));

        String line = reader.readLine();
        while(line != null) {

            double[] arr1 = new double[memorySize];

            for(int i = 0; i < memorySize; i++) {
                if(line != null) {
                    arr1[i] = Double.parseDouble(line);
                }
                line = reader.readLine();
            }
            HeapSort.heapSort(arr1);

            this.saveFile(arr1, "arq" + count + ".txt");

            if(count == 7  || line == null) {
                mergeFile();
            }

            count++;
            if(count > 7) {
                count = 0;
            }
        }
    }

    private void saveFile(double[] arr, String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));

        for (double line : arr) {
            writer.write(String.valueOf(line));
            writer.newLine();
        }

        writer.close();
    }

    private void mergeFile() throws IOException {

        if (swap == 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("merged0.txt"));
            File folder = new File("arquivo\\");
            File[] files = folder.listFiles();

            ArrayList<BufferedReader> readers = new ArrayList<>();
            for (int i = 0; i <= count; i++) {
                readers.add(new BufferedReader(new FileReader("arq" + i + ".txt")));
            }
            if (count != 0) {
                readers.add(new BufferedReader(new FileReader("merged1.txt")));
            }

            ArrayList<Double> numbers = new ArrayList<>();

            for (BufferedReader reader : readers) {
                String line = reader.readLine();
                if (line != null) {
                    try {
                        numbers.add(Double.parseDouble(line));
                    } catch (NumberFormatException e) {
                        System.out.println(line);
                    }
                } else {
                    numbers.add(null);
                }
            }

            while (!readers.isEmpty()) {

                Result result = min(numbers);
                if (result == null) {
                    break; // Evita acessar null
                }
                double number = result.getNumber();
                int index = result.getIndex();

                writer.write(String.valueOf(number) + "\n");

                String nextLine = readers.get(index).readLine();

                if (nextLine != null) {
                    numbers.set(index, Double.parseDouble(nextLine));
                } else {
                    numbers.remove(index);
                    readers.remove(index);
                }
            }

            writer.close();
        } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter("merged1.txt"));
            File folder = new File("arquivo\\");
            File[] files = folder.listFiles();

            ArrayList<BufferedReader> readers = new ArrayList<>();
            for (int i = 0; i <= count; i++) {
                readers.add(new BufferedReader(new FileReader("arq" + i + ".txt")));
            }

            if (count != 0) {
                readers.add(new BufferedReader(new FileReader("merged0.txt")));
            }

            ArrayList<Double> numbers = new ArrayList<>();

            for (BufferedReader reader : readers) {
                String line = reader.readLine();
                if (line != null) {
                    try {
                        numbers.add(Double.parseDouble(line));
                    } catch (NumberFormatException e) {
                        System.out.println(line);
                    }
                } else {
                    numbers.add(null);
                }
            }

            while (!readers.isEmpty()) {

                Result result = min(numbers);
                if (result == null) {
                    break; // Evita acessar null
                }
                double number = result.getNumber();
                int index = result.getIndex();

                writer.write(String.valueOf(number) + "\n");

                String nextLine = readers.get(index).readLine();

                if (nextLine != null) {
                    numbers.set(index, Double.parseDouble(nextLine));
                } else {
                    numbers.remove(index);
                    readers.remove(index);
                }

            }

            writer.close();
        }

        makeSwap();
    }

    private Result min (ArrayList<Double> numbers) {

        if (!numbers.isEmpty()) {
            if (numbers.get(0) != null) {
                double number = numbers.get(0);
                int index = 0;


                for (int i = 0; i < numbers.size(); i++) {
                    if (numbers.get(i) != null) {
                        if (number > numbers.get(i)) {
                            number = numbers.get(i);
                            index = i;
                        }
                    }
                }

                Result result = new Result(number, index);
                return result;
            }

        }
        return null;
    }

    private void makeSwap() {
        if (swap == 0) {
            swap = 1;
        } else {
            swap = 0;
        }
    }
}
