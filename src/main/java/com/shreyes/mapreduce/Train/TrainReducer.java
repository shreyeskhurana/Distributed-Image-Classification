package com.shreyes.mapreduce.Train;

import com.shreyes.mapreduce.HyperPlane;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TrainReducer extends Reducer <Text, HyperPlane, Text, Text>{
    private Double[] w = new Double[21*21*7];
    private Double b = 0.0, count = 0.0;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);

        for(int i = 0; i < w.length; i++) {
            w[i] = 0.0;
        }
    }

    @Override
    public void reduce(Text key, Iterable<HyperPlane> values, Context context)
            throws IOException, InterruptedException {
        for (HyperPlane hyperPlane : values) {
            Text weightList = hyperPlane.getWeights();
            String[] w_ = weightList.toString().split(",");

            for(int i = 0; i < w_.length; i++) {
                w[i] += Double.parseDouble(w_[i]);
            }

            b += Double.parseDouble(hyperPlane.getBias().toString());
            count++;
        }

        b /= count;
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        HyperPlane hp = new HyperPlane(w, b);
        context.write(hp.getWeights(), hp.getBias());
    }
}
