import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class KMean {

    class Point{
        public double Xcoord;
        public double YCoord;
        public int label;
        public double distance;   
        
        public Point(double x, double y){
            Xcoord = x; 
            YCoord = y;
            label = 0;
            distance = 99999.00;
        }

    }

    int k;
    int numPts;
    int numRows;
    int numCols;

    Point pointSet[];
    int displayAry[][];
    Point KCentroidAry[];

    Boolean checkIfPrior[];
    
    int change;

    public KMean(int rows, int cols, int kVal, int numOfPts){
        numRows = rows;
        numCols = cols;
        k = kVal;
        numPts = numOfPts;

        pointSet = new Point[numPts];
        displayAry = new int[numRows][numCols];

        KCentroidAry = new Point[k+1];

        for(int i = 0; i < k + 1; i++){
            KCentroidAry[i] = new Point(0.0, 0.0);
        }

        checkIfPrior = new Boolean[numPts];

        for(int i = 0; i < numPts; i++){
            checkIfPrior[i] = false;
        }

        change = 0;
    }

    public void loadPointSet(Scanner scanner){
        double x = 0.0;
        double y = 0.0;

        for(int i = 0; i < numPts; i++){
            if(scanner.hasNextDouble()){
                x = scanner.nextDouble();
                if(scanner.hasNextDouble()){
                    y = scanner.nextDouble();
                    pointSet[i] = new Point(x, y);
                }
            }
        }

    }

    public void kMeansClustering() throws IOException{
        String fileName = "outFile1";
        int iteration = 0;

        selectKCentroids();

        change = 2;

        while(change >= 2){
            int index = 0;
            iteration++;
    
            PlotDisplayAry();
            PrettyPrint(fileName, iteration);
    
            change = 0;
    
            while(index < numPts){ // numpts = 399
                Point pt = pointSet[index]; //gets the first point 41 56
                double minDist = pointSet[index].distance;  // by default distance should be 99999.00
        
                int minLabel = DistanceMinLabel(pt, KCentroidAry, minDist);
        
                if(pointSet[index].label != minLabel){
                    pointSet[index].label = minLabel;
                    // System.out.println(pointSet[index].label);
                    pointSet[index].distance = minDist;
                    change++;
                }
                index++;
            }

            if(change > 2){
                computeCentroids();                
            }
        }

    



    }

    public void computeCentroids(){
        double sumX[] = new double[k+1];
        double sumY[] = new double[k+1];
        int totalPt[] = new int[k+1];

        for(int i = 0; i < k+1; i++){
            sumX[i] = 0.0;
            sumY[i] = 0.0;
            totalPt[i] = 0;
        }

        int index = 0;
        
        while(index < numPts){
            int label = pointSet[index].label;
            sumX[label] += pointSet[index].Xcoord;
            sumY[label] += pointSet[index].YCoord;
            totalPt[label]++;
            index++;
        }

        int label = 1;
        while(label < k){
            if(totalPt[label] > 0.0){
                KCentroidAry[label].Xcoord = sumX[label]/totalPt[label];
                KCentroidAry[label].YCoord = sumY[label]/totalPt[label];
            }
            label++;
        }
    }

    public int DistanceMinLabel(Point pt, Point kCentroid[], double minDist){
        // compute the distance from a point pt to each of the K centroids. The method returns minLable
        // This algorithm may contain bugs, debugging is yours

        int minLabel = 0;
        int label = 1;
        
        while(label <= k){
            Point whichCentroid = KCentroidAry[label];
            double dist = computeDistance(pt, whichCentroid);

            // System.out.println("distance is " + dist);
    
            if(dist < minDist){
                minLabel = label;
                minDist = dist;
            }
            label++;
        }
        return minLabel;
    }

    public double computeDistance(Point pt, Point whichCentroid){
        //compute the distance between these two points using their x and y values

        //lets say the coordinates for pt is (x1,y1)
        //the coordinates for whichCentroid is (x2,y2)
        double xVal = whichCentroid.Xcoord - pt.Xcoord;
        xVal = Math.pow(xVal, 2);
        double yVal = whichCentroid.YCoord - pt.YCoord;
        yVal = Math.pow(yVal, 2);

        double distance = xVal + yVal;
        distance = Math.sqrt(distance);

        return distance;

    }

    public void selectKCentroids(){
        //we want to make sure we aren't selecting the same centroid twice
        int Kcnt = 0;
        Random rand = new Random();

        while(Kcnt < k){
            int index = rand.nextInt(numPts);
            Boolean repeatYN = checkRepeat(index);

            while(repeatYN){//if its true that means we visited it already 
                index = rand.nextInt(index);
                repeatYN = checkRepeat(index);
            }
            Kcnt++;

            // System.out.println("k cluster is " + index);

            KCentroidAry[Kcnt].Xcoord = pointSet[index].Xcoord;
            KCentroidAry[Kcnt].YCoord = pointSet[index].YCoord;
            KCentroidAry[Kcnt].label = Kcnt;
            KCentroidAry[Kcnt].distance = 0.0;
            // System.out.println("xcoord is " + KCentroidAry[Kcnt].Xcoord );
            // System.out.println("ycoord is " + KCentroidAry[Kcnt].YCoord);


        }

    
    }

    public Boolean checkRepeat(int index){

        if(checkIfPrior[index] == true){
            return true;
        }else{  // means we haven't check it before so set it equal to true
            checkIfPrior[index] = true;
            return false;
        }
    }

    public void PlotDisplayAry(){
        //for each point i, in pointSet, plot the pointSet[i].Label
        // onto the displayAry at the location of pointSet[i]’s Xcoord and //Ycoord; need to convert pointSet[i]’s
        // Xcoord and Ycoord to integer //to get the 2D coordinates

        for(int i = 0; i < numPts; i++){
            int x = (int)pointSet[i].Xcoord;
            int y = (int)pointSet[i].YCoord;
            displayAry[x][y] = pointSet[i].label;
            // System.out.println(x + " " + y);

        }
    }

    public void PrettyPrint(String fileName, int iteration) throws IOException{//include the outFile
        PrintWriter writer = new PrintWriter(new FileWriter(fileName,true));

        writer.println("***Result of iteration " + iteration + " ***");
        //this will be different depending on the K value
        writer.println("Centroid A: " + KCentroidAry[1].Xcoord + " " + KCentroidAry[1].YCoord);
        writer.println("Centroid B: " + KCentroidAry[2].Xcoord + " " + KCentroidAry[2].YCoord);
        writer.println("Centroid C: " + KCentroidAry[3].Xcoord + " " + KCentroidAry[3].YCoord);

        writer.println();

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(displayAry[i][j] >= 1){
                    writer.write(displayAry[i][j] + " ");
                }else{
                    writer.write(" ");
                }
            }
            writer.println();
        }

        writer.close();


    }

    public void print(){
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                System.out.print(displayAry[i][j] + " ");
            }
            System.out.println();
        }
    }

    
}
