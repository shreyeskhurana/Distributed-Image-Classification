package com.shreyes.mapreduce;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class Util {
//    public static Instances generateInstances(List<double[]> input) {
//        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
//
//        for(int i = 0; i < input.get(0).length; i++) {
//            attributes.add(new Attribute(""+ i));
//        }
//
//        Instances dataRaw = new Instances("TrainInstances", attributes , 0);
//        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
//
//        for (double[] i: input) {
//            dataRaw.add(new DenseInstance(1.0, i));
//        }
//
//        return dataRaw;
//    }

    public static Double[][] matMultiply(double[][] A, double[][] B) {
        int L = A.length, M = A[0].length, N = B[0].length;

        if(A.length == 0 || B.length == 0 || A[0].length != B.length)
            return null;

        Double[][] C = new Double[L][N];

        for(int i = 0; i < L; i++) {
            for(int j = 0; j < N; j++) {
                C[i][j] = 0.0;

                for(int k = 0; k < M; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
}
