package com.shreyes.mapreduce.Train;

import com.shreyes.mapreduce.HyperPlane;
import com.shreyes.mapreduce.Learn.SVM;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainMapper extends Mapper<Object, Text, Text, HyperPlane> {
    private List<double[]> input = new ArrayList<double[]>();
    private Boolean valid1 = false, valid2 = false;

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String lineElements[] = value.toString().split(",");
        double[] input_i = new double[lineElements.length];

        for(int i = 0; i < lineElements.length; i++) {
            input_i[i] = (Double.parseDouble(lineElements[i]));
        }

        if(!valid1 && input_i[input_i.length - 1] < 0.9)
            valid1 = true;

        if(input_i[input_i.length - 1] > 0.01)
            valid2 = true;

        input.add(input_i);
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        if(valid1 && valid2) {
            HyperPlane hp = SVM.smo(input);
            context.write(new Text("dummy"), hp);
        }
    }
}
