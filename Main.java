import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args)throws IOException{

        //inputFile which is the dataFile 
        // String dataString = args[0];
        // FileReader dataFileReader = null;  //open and read from external .txt files
        // BufferedReader dataBufferedReader = null; // reads the input text from the input stream (e.g. File Reader) and buffers the characters into a character array
        // Scanner dataScanner = null;           //expands the functionality of the bufferedReader class

        // //retrieving the k value
        // int k = Integer.parseInt(args[2]);

   

        KMean kMean = new KMean(5, 6, 4, 140);
        kMean.selectKCentroids();

    }
}