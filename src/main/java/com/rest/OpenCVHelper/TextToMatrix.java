package com.rest.OpenCVHelper;

import java.io.BufferedReader;
import java.io.FileReader;

public class TextToMatrix {

    public int[][] create2DIntMatrixFromFile(String filename) throws Exception {
        int[][] matrix = null;
        BufferedReader buffer = new BufferedReader(new FileReader(filename));

        String line;
        int row = 0;
        int size = 0;
        while ((line = buffer.readLine()) != null) {
            String[] vals = line.split("");

            System.out.println();
            size = vals.length;

            if (matrix == null) {
                matrix = new int[size][size];
            }

            for (int col = 0; col < size; col++) {
                if(vals[col].equals("+")) {
                    matrix[row][col] = 1;
                } else if(vals[col].equals("//s+")) {
                    matrix[row][col] = 0;
                }
            }
            row++;
        }
        return matrix;
    }

    public static void printMatrix(float[][] matrix) {
        String str = "";
        int size = matrix.length;

        if (matrix != null) {
            for (int row = 0; row < size; row++) {
                str += " ";
                for (int col = 0; col < size; col++) {
                    str += String.format("%f",  matrix[row][col]);
                    if (col < size - 1) {
                        str += " | ";
                    }
                }
                if (row < size - 1) {
                    str += "\n";
                    for (int col = 0; col < size; col++) {
                        for (int i = 0; i < 4; i++) {
                            str += "-";
                        }
                        if (col < size - 1) {
                            str += "+";
                        }
                    }
                    str += "\n";
                } else {
                    str += "\n";
                }
            }
        }
        System.out.println(str);
    }
}
