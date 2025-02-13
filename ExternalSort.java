import java.io.*;
import java.util.ArrayList;

public class ExternalSort {
    private static final int memorySize = 100;
    //private static final int numWays = 10;
    private int count = 0;
    private int mergCount = 0;

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

            count++;
        }
        mergeFile();
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

        while(mergCount < count) {
            File file = new File("arq" + count + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("arq" + count + ".txt"));
            count++;

            ArrayList<BufferedReader> readers = new ArrayList<>();
            int lastFiles = count - mergCount;

            if(lastFiles < 9) {
                for (int i = 0; i < lastFiles; i++) {
                    readers.add(new BufferedReader(new FileReader("arq" + mergCount + ".txt")));
                    mergCount++;
                }
            } else {
                for (int i = 0; i < 9; i++) {
                    readers.add(new BufferedReader(new FileReader("arq" + mergCount + ".txt")));
                    mergCount++;
                }

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

                if (readers.size() == 1) {
                    String line = String.valueOf(number);
                    while (line != null) {
                        writer.write(line + "\n");
                        line = readers.get(0).readLine();
                    }
                    numbers.remove(index);
                    readers.remove(index);
                    break;
                }
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
}
