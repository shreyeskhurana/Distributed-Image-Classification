package com.shreyes.mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Util {
    public static Double dotProduct(double[] u, double[] v) {
        if(u == null || v == null || u.length != v.length)
            return 0.0;

        Double p = 0.0;

        for(int i = 0; i < u.length; i++) {
            p += u[i] * v[i];
        }

        return p;
    }

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

    public static HyperPlane readModelFromFile(String[] args) {
        Double[] w = new Double[0];
        Double b = 0.0;

         try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(new File(args[0] + "/Train/Output/*.txt")));

            String lineElements[] = reader.readLine().split(",");
            w = new Double[lineElements.length];

            for(int i = 0; i < lineElements.length; i++) {
                w[i] = (Double.parseDouble(lineElements[i]));
            }

            b = Double.parseDouble(reader.readLine());

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HyperPlane(w, b);

    }
}
