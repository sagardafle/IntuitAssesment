package com.rest.CustomHelper;

/**
 * Class that manages the User input data from client(Postman)
 */
public class UserInput {
    /**
     *
     * @return returns the name of the inputfile
     */
    public String getInputfile() {
        return inputfile;
    }

    /**
     * Sets the name of inputfile
     * @param inputfile
     */
    public void setInputfile(String inputfile) {
        this.inputfile = inputfile;
    }

    String inputfile;
    String thresholdvalue;

    /**
     *
     * @return The input Threshold value
     */
    public String getThresholdvalue() {
        return thresholdvalue;
    }

    /**
     * Sets the Threshold value
     * @param thresholdvalue
     */
    public void setThresholdvalue(String thresholdvalue) {
        this.thresholdvalue = thresholdvalue;
    }
}
