package com.rest.CustomHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class MatchTemplateHelper {
    private final String TEMPLATE_FILE = "C:\\Users\\I863509\\IdeaProjects\\TemplateMatching\\src\\main\\resources\\sample_files\\perfect_cat_image.txt";
    private int template_row;
    private int template_column;
    private int[][] inputarr;
    private Map < List < Integer > , List < Integer >> pointsmap;
    private int unmatchcounter = 0;
    private int MINIMUM_CHARACTERS_PER_ROW_MATCH = 0;

    private int X1;
    private int X2;
    private int Y1;
    private int Y2;

    public int[][] create2DIntMatrixFromFile(String filename) throws Exception {
        int[][] matrix = null;
        BufferedReader buffer = new BufferedReader(new FileReader(filename));
        String line;
        int row = 0;
        int size = 0;
        while ((line = buffer.readLine()) != null) {
            String[] vals = line.trim().split("");

            System.out.println();
            size = vals.length;

            if (matrix == null) {
                matrix = new int[size][size];
            }

            for (int col = 1; col < size; col++) {
                if (vals[col].equals("+")) {
                    matrix[row][col] = 1;
                }
            }
            row++;
        }
        return matrix;
    }

    public Map < Integer, String > buildCatMap(String inputfile) {
        Map < Integer, String > catMap = new HashMap < Integer, String > ();
        pointsmap = new HashMap < List < Integer > , List < Integer >> ();
        try {
            int[][] catarr = create2DIntMatrixFromFile(TEMPLATE_FILE);
            inputarr = create2DIntMatrixFromFile(inputfile);

            template_column = catarr[0].length;
            System.out.println("Template_row: " + template_row);
            System.out.println("Template_column: " + template_column);
            MINIMUM_CHARACTERS_PER_ROW_MATCH = template_column/2;
            int row = 1;
            for (int i = 1; i < catarr.length; i++) {
                if (row > 13) break;
                StringBuilder sb = new StringBuilder();
                for (int j = 1; j < catarr[0].length; j++) {
                    sb.append(catarr[i][j]);
                }
                catMap.put(row++, sb.toString());
            }
            template_row = catMap.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catMap;
    }

    public Map < List < Integer > , List < Integer >> findMatch(Map < Integer, String > catMap, String thresholdvalue) {
        int mismatch = getAllowedMismatch(thresholdvalue, catMap);
        System.out.println("Minimum " + mismatch + " rows mismatch allowed.");

        /*for loop to find the match of 1st <k,V> pair as per the template hashmap.*/
        for (int i = 0; i < inputarr.length; i++) {
            StringBuilder currentrow = new StringBuilder();
            if (inputarr[i] != null) {
                for (int j = 0; j < inputarr[i].length; j++) {
                    currentrow.append(inputarr[i][j]);
                }
            }
            System.out.println("currentrow " + currentrow);
            if (currentrow.indexOf(catMap.get(1)) != -1) {
                //first row is matched,get the index of the first matched point.
                X1 = i;
                Y1 = currentrow.indexOf(catMap.get(1));

                System.out.println("X1: " + X1);
                System.out.println("Y1: " + Y1);
                /*Inner For Loop. Check for below rows*/
                int m = 2;
                unmatchcounter = 0;
                X2 = X1 + 1;
                Y2 = Y1;
                while (X2 < X1 + template_row && Y2 < Y1 + template_column) {
                    System.out.println("Current value of X2: " + X2);
                    System.out.println("Current value of Y2: " + Y2);
                    if (unmatchcounter == mismatch) {
                        System.out.println("======Exiting.Checking the next X1 and X2 point.==========");
                        break; //break if the no of mismatches have been allowed
                    }
                    String targetrowstring = getRowString(X2, Y2);
                    System.out.println("New targetrowstring: " + targetrowstring);
                    if (targetrowstring.indexOf(catMap.get(m)) != -1) {
                        //Consequent row matched
                        System.out.println("Row matched at X2: " + X2 + " Y2: " + Y2);
                    } else {
                        /*Here, the row is not matched.
                        So we check if at least 'MINIMUM_CHARACTERS_PER_ROW_MATCH' condition is true*/
                        boolean isCharacterMisMatchConditionMet = characterMatch(targetrowstring, catMap.get(m));
                        if (!isCharacterMisMatchConditionMet) {
                            ++unmatchcounter; //no match found in this row;increment the counter
                            System.out.println("Incrementing unmatch: " + unmatchcounter + " for X1: " + X1 + " and Y!: " + Y1);
                        } else{
                            System.out.println("Character condition was met for X2: "+X2+ " and Y2: "+Y2);
                        }
                    }
                    m++;
                    X2++; //go to the next row
                }
            } else {
                System.out.println("No match found in row# " + i);
            }

            System.out.println("Umatch before going to next points: " + unmatchcounter);
            populateoutputMap(mismatch);
        }

        return pointsmap;
    }

    private boolean characterMatch(String filerowstring, String templaterowstring) {

        int i = 0;
        int counter = 0;
        for (char c: filerowstring.toCharArray()) {
            if (c != templaterowstring.charAt(i++))
                counter++;
        }
        if (counter > MINIMUM_CHARACTERS_PER_ROW_MATCH) {
            System.out.println("Comparision of strings "+filerowstring+ " and "+templaterowstring+ " failed");
            return false;
        }

        return true;
    }

    private void populateoutputMap(int mismatch) {
        if (unmatchcounter < mismatch && unmatchcounter > 0) {
            /*This means the current point set can be added to answer.
            Adding X1,Y1 and X2,Y2.*/
            Points p = new Points();
            p.setX1(X1);
            p.setX2(X2);
            p.setY1(Y1);
            p.setY2(Y2);
            List < Integer > startingcoordinates = new ArrayList < Integer > ();
            startingcoordinates.add(p.getX1());
            startingcoordinates.add(p.getY1());

            List < Integer > endingcoordinates = new ArrayList < Integer > ();
            endingcoordinates.add(p.getX2());
            endingcoordinates.add(p.getY2());
            pointsmap.put(startingcoordinates, endingcoordinates);
        }
    }

    private int getAllowedMismatch(String thresholdvalue, Map < Integer, String > catMap) {
        int threshold = Integer.parseInt(thresholdvalue);
        System.out.println("Threshold: " + thresholdvalue);
        float minimummatches = (threshold * catMap.size() - 1) / 100;
        return (catMap.size() - 1 - (int) minimummatches);
    }

    private String getRowString(int X2, int Y2) {
        StringBuilder currentrow = new StringBuilder();
        if (inputarr[X2] != null) {
            for (int j = Y2; j < Y2 + template_column - 1; j++) {
                currentrow.append(inputarr[X2][j]);
            }
        }
        return currentrow.toString();
    }
}