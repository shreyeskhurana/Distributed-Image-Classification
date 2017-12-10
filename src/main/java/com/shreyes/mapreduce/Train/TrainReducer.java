package com.shreyes.mapreduce.Train;

import com.shreyes.mapreduce.HyperPlane;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TrainReducer extends Reducer <Text, HyperPlane, Text, HyperPlane>{
    private Double[] w = new Double[21*21*7];
    private Double b = 0.0, count = 0.0;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);

        for(Double wi: w) {
            wi = 0.0;
        }
    }

    public void reduce(Text key, Iterable<HyperPlane> values, Context context)
            throws IOException, InterruptedException {
        for (HyperPlane hyperPlane : values) {
            double[] w_ = (double[]) hyperPlane.getWeights().toArray();

            for(int i = 0; i < w_.length; i++) {
                w[i] += w_[i];
            }

            b += hyperPlane.getBias().get();
            count++;
        }

        b /= count;
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text(), new HyperPlane(w, b));
    }
}
