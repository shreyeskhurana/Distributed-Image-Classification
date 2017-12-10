package com.shreyes.mapreduce;

import com.shreyes.mapreduce.Predict.PredictJob;
import com.shreyes.mapreduce.Test.TestJob;
import com.shreyes.mapreduce.Train.TrainJob;

public class App {
    public static void main(String[] args) {
        try {
            TrainJob.initiate(args);
            Double accuracy = TestJob.initiate(args);
            PredictJob.initiate(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
