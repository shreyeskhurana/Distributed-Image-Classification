package com.shreyes.mapreduce.TestAndValidation;

import com.shreyes.mapreduce.HyperPlane;
import com.shreyes.mapreduce.Learn.SVM;
import com.shreyes.mapreduce.Util;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TestMapper extends Mapper<Object, Text, Text, Text> {
    private HyperPlane trainedModel;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String lineElements[] = value.toString().split(",");
        double[] test_i = new double[lineElements.length];

        for(int i = 0; i < lineElements.length - 1; i++) {
            test_i[i] = (Double.parseDouble(lineElements[i]));
        }

        Boolean predict = SVM.predictTwoClass(trainedModel, test_i);
    }

    //Context.write remaining
}
