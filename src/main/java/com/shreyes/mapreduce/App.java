package com.shreyes.mapreduce;

import com.shreyes.mapreduce.Train.TrainingJob;

public class App {
    public static void main(String[] args) {
        try {
            TrainingJob.initiate(args);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
