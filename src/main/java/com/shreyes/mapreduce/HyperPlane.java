package com.shreyes.mapreduce;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class HyperPlane implements Writable {
    public static class DoubleArrayWritable extends ArrayWritable {
        DoubleArrayWritable() {
            super(DoubleWritable.class);
        }
    }
    private DoubleArrayWritable w;
    private DoubleWritable b;

    public HyperPlane(Double[] w, Double b) {
        this.w = new DoubleArrayWritable();

        DoubleWritable[] weights = new DoubleWritable[w.length];

        for (int k = 0; k < w.length; k++) {
            weights[k] = new DoubleWritable(w[k]);
        }

        this.w.set(weights);

        this.b = new DoubleWritable(b);
    }

    public DoubleArrayWritable getWeights() {
        return w;
    }

    public DoubleWritable getBias() {
        return b;
    }

    public void readFields(DataInput in) throws IOException {
        w.readFields(in);
        b.readFields(in);
    }

    public void write(DataOutput out) throws IOException {
        w.write(out);
        b.write(out);
    }
}