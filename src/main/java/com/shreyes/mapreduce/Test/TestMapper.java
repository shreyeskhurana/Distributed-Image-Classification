package com.shreyes.mapreduce.Test;

import com.shreyes.mapreduce.Learn.SVM;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class TestMapper extends Mapper<Object, Text, Text, Text> {
    private double[] w = new double[0];
    private double b = 0.0;
    private Integer total = 0, correct = 0;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        URI[] cacheFiles = context.getCacheFiles();

        if (cacheFiles != null && cacheFiles.length > 0) {
            try {
                FileSystem fs = FileSystem.get(context.getConfiguration());
                String[] temp = cacheFiles[0].toString().split("%20");
                Path path = new Path(temp[0] +" "+ temp[1]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(path)));
                String line;

                if ((line = reader.readLine()) != null) {
                    String[] weights = line.split(",");
                    w = new double[weights.length];
                    for(int i = 0; i < weights.length - 1; i++) {
                        w[i] = Double.parseDouble(weights[i]);
                    }

                    String[] last = weights[weights.length-1].split("\t");
                    w[weights.length-1] = Double.parseDouble(last[0]);
                    b = Double.parseDouble(last[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String lineElements[] = value.toString().split(",");
        double[] test_i = new double[lineElements.length-1];

        for(int i = 0; i < lineElements.length - 1; i++) {
            test_i[i] = (Double.parseDouble(lineElements[i]));
        }

        Double actual = Double.parseDouble(lineElements[lineElements.length-1]);
        Boolean predict = SVM.predictTwoClass(w, b, test_i);

        if(actual == 0 && !predict)
            correct++;
        else if(actual == 1 && predict)
            correct++;

        total++;
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
        Configuration conf = context.getConfiguration();

        total += Integer.parseInt(conf.get("total"));
        correct += Integer.parseInt(conf.get("correct"));

        conf.set("total", total.toString());
        conf.set("correct", correct.toString());
    }
}
