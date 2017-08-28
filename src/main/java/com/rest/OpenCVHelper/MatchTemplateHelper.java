package com.rest.OpenCVHelper;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class MatchTemplateHelper {

    private static final String TEMPLATE_FILE = "C:\\Users\\I863509\\IdeaProjects\\IntuitAssesment\\src\\sample_files\\perfect_cat_image.txt";

    public static Mat fileToOpenCVMatrix(String inputfile, String thresholdvalue) {
        int[][] filematrix = new int[0][0];
        TextToMatrix texttomatrixobj = new TextToMatrix();
        try {
            filematrix = texttomatrixobj.create2DIntMatrixFromFile(inputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[][] templatematrix = new int[0][0];
        try {
            templatematrix = texttomatrixobj.create2DIntMatrixFromFile(TEMPLATE_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //texttomatrixobj.printMatrix(filematrix);



       // texttomatrixobj.printMatrix(templatematrix);

        float[] fileMat1D = convert2Dto1D(filematrix);
        float[] templateMat1D = convert2Dto1D(templatematrix);

        for(Float a:fileMat1D){
            System.out.print(a+ " ");
        }

        System.out.println("==========================================================================");
        System.out.println("==========================================================================");
        System.out.println("==========================================================================");
        System.out.println("==========================================================================");

        for(Float a:templateMat1D){
            System.out.print(a+ " ");
        }

        Mat fileMat = floatToMat(filematrix);
        Mat templateMat = floatToMat(templatematrix);

        System.out.println("fileMat " + fileMat);
        System.out.println("templateMat " + templateMat);

        Mat processedmatrices = processOpenCVMatrices(fileMat, templateMat, Imgproc.TM_SQDIFF);
        return processedmatrices;
    }

    private static float[] convert2Dto1D(int[][] templatematrix) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < templatematrix.length; i++) {
            // tiny change 1: proper dimensions
            for (int j = 0; j < templatematrix[i].length; j++) {
                // tiny change 2: actually store the values
                list.add(templatematrix[i][j]);
            }
        }

        float[] vector = new float[list.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = list.get(i);
        }
        return vector;
    }

    private static Mat floatToMat(int[][] floatmatrix) {
        Mat opencvMat = new Mat(floatmatrix.length, floatmatrix[0].length, CvType.CV_32FC1); //HxW 4x2
        for (int i = 0; i < floatmatrix.length; i++) {
            for (int j = 0; j < floatmatrix[0].length; j++) {
                opencvMat.put(i, j, floatmatrix[i][j]);
            }
        }
        return opencvMat;
//        Mat mat = new Mat(0, floatmatrix.length, CvType.CV_32F);
//        mat.put(0, 0, floatmatrix);
//        return mat;
    }


    public static Mat processOpenCVMatrices(Mat fileMat, Mat templateMat, int match_method) {
        System.out.println("Running Template Matching");
        Mat output = matchTempalteWithFile(fileMat, templateMat, match_method);
        System.out.println("output" + output);
        return output;
    }

    private static Mat matchTempalteWithFile(Mat fileMat, Mat templateMat, int match_method) {
//        printMat(fileMat);
//        printMat(templateMat);

        int resultCols = fileMat.cols() - (templateMat.cols() + 1);
        int resultRows = fileMat.rows() - (templateMat.rows() + 1);
        System.out.println("resultCols: "+resultCols);
        System.out.println("resultRows: "+resultRows);
        Mat result = new Mat(resultRows, resultCols, CvType.CV_32FC1);
        Imgproc.matchTemplate(fileMat, templateMat, result, match_method);

        //minMaxLoc find the minimum and maximum element values and their positions
        Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(result);

        Point matchLoc = null;
        /// For SQDIFF and SQDIFF_NORMED, the best matches are lower values. For all the other methods, the higher the better
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = minMaxLocResult.minLoc;
        } else {
            matchLoc = minMaxLocResult.maxLoc;
        }

        Mat img_display = new Mat();
        fileMat.copyTo(img_display);
        Core.rectangle(img_display, matchLoc, new Point(matchLoc.x + templateMat.cols(), matchLoc.y + templateMat.rows()), new Scalar(0, 255, 0));

        System.out.println(minMaxLocResult.minVal);
        System.out.println(minMaxLocResult.minLoc);
        System.out.println(minMaxLocResult.maxVal);
        System.out.println(minMaxLocResult.maxLoc);
        return img_display;
//        Imgproc.threshold(result, result, 0.1, 1, Imgproc.THRESH_TOZERO);
//        double maxval;
//        Mat dst;
//        Point maxop;
//        while(true)
//        {
//            Core.MinMaxLocResult maxr = Core.minMaxLoc(result);
//            Point maxp = maxr.maxLoc;
//            maxval = maxr.maxVal;
//            maxop = new Point(maxp.x + templateMat.width(), maxp.y + templateMat.height());
//            dst = fileMat.clone();
//            if(maxval >= 0.15) {
//                System.out.println("Template Matches with input image");
//
//                Core.rectangle(fileMat, maxp, new Point(maxp.x + templateMat.cols(),
//                        maxp.y + templateMat.rows()), new Scalar(0, 255, 0),5);
//                Core.rectangle(result, maxp, new Point(maxp.x + templateMat.cols(),
//                        maxp.y + templateMat.rows()), new Scalar(0, 255, 0),-1);
//            }else{
//                break;
//            }
//
//        }
//        System.out.println("Point: "+maxop);
//
//        return dst;
    }

}