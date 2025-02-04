package Multithreading;

public class MatrixMultiplication {
    static int [][] result;
    static int [][] matA, matB;
    static int rowsA, colsA, colsB;
    public static void main(String [] args) {
    	matA = new int [][] {
    		{1,2,3},
    		{4,5,6},
    		{7,8,9}};
    	matB = new int [][] {
    		{1,2,3},
    		{4,5,6},
    		{7,8,9}};
    	
    rowsA = matA.length;
    colsA = matA[0].length;
    colsB = matB[0].length;
    result = new int[rowsA][colsB];
    Thread[] threads = new Thread[rowsA];
    for(int i = 0; i < rowsA; i++) {
    	threads[i] = new Thread(new RowMultiply(i));
    	threads[i].start();
    }
    for(int i = 0; i < rowsA; i++) {
    	try {
    		threads[i].join();
    	}
    	catch(InterruptedException e) {
    		  e.printStackTrace();
    	}
    }
    System.out.println("Result Matrix:");
    displayMatrix(result);
    }
    static class RowMultiply implements Runnable{
    	int rowIndex;
    	RowMultiply(int rowIndex) {
            this.rowIndex = rowIndex;
        }
    	public void run() {
    		for(int j = 0; j < colsA; j++) {
    			result[rowIndex][j] = 0;
    			for(int k = 0; k < colsB; k++) {
    				result[rowIndex][j] += matA[rowIndex][j] * matB[k][j];
    			}
    		}
    	}
    }
    static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}