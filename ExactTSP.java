/*
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exacttsp;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.MAX_VALUE;
import java.util.Arrays;
import java.util.Scanner;

 class ExactTSP {

    public static int[] Path(int matrix[][]) {
        int dim = matrix.length;
        int[] currentPath = new int[dim];
        int[] bestPath = new int[dim];
        int cost = 0;
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < dim; i++) {
                currentPath[i] = i;
            }
        bestPath = currentPath.clone();
        do {
            cost = Cost(currentPath, matrix);
            if(cost < minCost ){
                minCost = cost;
                bestPath = currentPath.clone();
            }

        } while (findNextPermutation(currentPath));
        return bestPath;
        
    }
    
    public static int Cost(int[] currentPath, int[][] matrix){
        int cost =0;
        int firstCity = currentPath[0];
        int endCity = currentPath[matrix.length-1];
        
        for(int i = 1; i < matrix.length; i++){
            int start = currentPath[i-1];
            int end = currentPath[i];
            cost = cost + matrix[start][end];
        }
        cost = cost +  matrix[endCity][firstCity];
        return cost;
    }

    public static int[] swap(int data[], int left, int right) {

        // Swap the data 
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;

        // Return the updated array 
        return data;
    }

    // Function to reverse the sub-array 
    // starting from left to the right 
    // both inclusive 
    public static int[] reverse(int data[], int left, int right) {

        // Reverse the sub-array 
        while (left < right) {
            int temp = data[left];
            data[left++] = data[right];
            data[right--] = temp;
        }

        // Return the updated array 
        return data;
    }

    // Function to find the next permutation 
    // of the given integer array 
    public static boolean findNextPermutation(int data[]) {

        // If the given dataset is empty 
        // or contains only one element 
        // next_permutation is not possible 
        if (data.length <= 1) {
            return false;
        }

        int last = data.length - 2;

        // find the longest non-increasing suffix 
        // and find the pivot 
        while (last >= 0) {
            if (data[last] < data[last + 1]) {
                break;
            }
            last--;
        }

        // If there is no increasing pair 
        // there is no higher order permutation 
        if (last < 0) {
            return false;
        }

        int nextGreater = data.length - 1;

        // Find the rightmost successor to the pivot 
        for (int i = data.length - 1; i > last; i--) {
            if (data[i] > data[last]) {
                nextGreater = i;
                break;
            }
        }

        // Swap the successor and the pivot 
        data = swap(data, nextGreater, last);

        // Reverse the suffix 
        data = reverse(data, last + 1, data.length - 1);

        // Return true as the next_permutation is done 
        return true;
    }

    public static void printMat(int[][] A) {
        int dim = A.length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim - 1; j++) {
                System.out.print(A[i][j] + ",");
            }
            System.out.println(A[dim - 1][i]);
        }
    }
    
    public static int[][] loadMatrix(File inFile) throws FileNotFoundException {

        Scanner fileScanner = new Scanner(inFile);
        String oneLine = fileScanner.nextLine();
        //Get 1st row for dimensions
        String[] splitLine = oneLine.split(",");
        int dim = splitLine.length;
        int[][] M = new int[dim][dim];
        for (int j = 0; j < dim; j++) {
            M[0][j] = Integer.parseInt(splitLine[j]);
        }
        for (int i = 1; i < dim; i++) {
            oneLine = fileScanner.nextLine();
            splitLine = oneLine.split(",");
            for (int j = 0; j < dim; j++) {
                M[i][j] = Integer.parseInt(splitLine[j]);
            }
        }
        return M;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("e:/6.txt");
        int [] bestPath = Path(loadMatrix(inFile));
        printMat(loadMatrix(inFile));
        System.out.println("The best path is: " + Arrays.toString(bestPath));
        int tourCost = Cost(bestPath, loadMatrix(inFile));
        System.out.println("Minimum tour Cost: " + tourCost);
    }

}
