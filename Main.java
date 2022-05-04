import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args)throws IOException{

        // inputFile which is the dataFile 
        String dataString = args[0];
        FileReader dataFileReader = new FileReader(dataString);  //open and read from external .txt files
        BufferedReader dataBufferedReader = new BufferedReader(dataFileReader); // reads the input text from the input stream (e.g. File Reader) and buffers the characters into a character array
        Scanner dataScanner = new Scanner(dataBufferedReader);           //expands the functionality of the bufferedReader class

        // //retrieving the k value
        int k = Integer.parseInt(args[1]);

        int rows = dataScanner.nextInt();
        int columns = dataScanner.nextInt();
        int numbPts = dataScanner.nextInt();
 

        KMean kMean = new KMean(rows, columns, k, numbPts);
        kMean.loadPointSet(dataScanner);
        kMean.kMeansClustering();
        // kMean.print();

    }
}