package com.shreyes.mapreduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class HyperPlane implements Writable {

    private Text w;
    private Text b;

    public HyperPlane() {
        w = new Text();
        b = new Text();
    }

    public HyperPlane(Double[] w, Double b) {
        this.w = new Text();

        StringBuilder weights = new StringBuilder();

        if(w.length != 0) {
            for (int k = 0; k < w.length; k++) {
                weights.append(Double.toString(w[k]) + ",");
            }

            this.w.set(weights.toString().substring(0,weights.toString().length() -1));
        } else this.w = new Text();

        this.b = new Text();
        this.b.set(Double.toString(b));
    }

    public Text getWeights() {
        return w;
    }

    public Text getBias() {
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

    @Override
    public String toString() {
        return w.toString() + "\n" + b.toString();
    }
}