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

    public void kMeansClustering(){
        int iteration = 0;
        selectKCentroids();

        int index = 0;


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

            // KCentroidAry[Kcnt].Xcoord = pointSet[index].Xcoord;
            // KCentroidAry[Kcnt].YCoord = pointSet[index].YCoord;
            // KCentroidAry[Kcnt].label = Kcnt;
            // KCentroidAry[Kcnt].distance = 0.0;
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

    public void PlotDisplayAry(Point pSet[],int display[][]){
        //for each point i, in pointSet, plot the pointSet[i].Label
        // onto the displayAry at the location of pointSet[i]’s Xcoord and //Ycoord; need to convert pointSet[i]’s
        // Xcoord and Ycoord to integer //to get the 2D coordinates

        for(int i = 0; i < numPts; i++){
            int x = (int)pointSet[i].Xcoord;
            int y = (int)pointSet[i].YCoord;
            display[x][y] = pointSet[i].label;
        }
    }

    public void PrettyPrint(int display[][], int iteration){//include the outFile


    }

    
}
