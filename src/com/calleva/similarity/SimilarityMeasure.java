package com.calleva.similarity;

public interface SimilarityMeasure extends java.io.Serializable {

   
    public double similarity(String[] x, String[] y);
}
