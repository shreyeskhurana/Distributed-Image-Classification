package com.shreyes.mapreduce.Train;

import com.shreyes.mapreduce.HyperPlane;
import com.shreyes.mapreduce.Util;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

import libsvm.svm_parameter;
import libsvm.svm_problem;

import java.util.List;

public class SVM {
    /**
     * Sequential Minimum Optimization or S.M.O. is an algorithm
     * used to generate the equation of the hyperplane which
     * separates/classifies the training data and generates a
     * trained model which can be used for testing.
     *
     * @param inputData: Training data in the format [x1, x2, ... , xm, y]
     * @return Hyperplane with its coefficients and intercepts.
     */
    public static HyperPlane smo(List<double[]> inputData) {
        svm_problem prob = new svm_problem();

        int dataCount = inputData.size();

        prob.l = dataCount;
        prob.x = new svm_node[dataCount][];
        prob.y = new double[dataCount];

        for (int i = 0; i < dataCount; i++){
            double[] features = inputData.get(i);

            prob.x[i] = new svm_node[features.length-1];
            for (int j = 0; j < features.length - 1; j++) {
                svm_node node = new svm_node();
                node.index = j;
                node.value = features[j];
                prob.x[i][j] = node;
            }

            prob.y[i] = features[features.length-1];
        }

        svm_parameter param = new svm_parameter();
        param.svm_type = svm_parameter.C_SVC;
        param.kernel_type = svm_parameter.LINEAR;
        param.C = 1;
        param.eps = 0.001;
        param.gamma = 0.5;
        param.nu = 0.5;
        param.probability = 1;
        param.cache_size = 20000;

        svm_model model = svm.svm_train(prob, param);

        Double[][] W = generateWeightVector(model.SV, model.sv_coef);
        Double[] w = W != null ? W[0]: new Double[0];

        Double b = -model.rho[0];

        return new HyperPlane(w, b);
    }

    private static Double[][] generateWeightVector(svm_node[][] SV, double[][] C) {
        if (SV == null)
            return null;

        double[][] S = new double[SV.length][SV[0].length];

        for(int i = 0; i < SV.length; i++) {
            for(int j = 0; j < SV[0].length; j++) {
                S[i][j] = SV[i][j].value;
            }
        }

        return Util.matMultiply(C, S);
    }
}
