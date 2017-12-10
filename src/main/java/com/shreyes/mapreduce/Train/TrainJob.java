package com.shreyes.mapreduce.Train;

import com.shreyes.mapreduce.App;
import com.shreyes.mapreduce.HyperPlane;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TrainJob {
    public static void initiate (String[] args) throws Exception{
        Configuration conf = new Configuration();

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        if (otherArgs.length < 2) {
            System.err.println("Please check the number of arguments!");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "Image Classification");

        job.setJarByClass(App.class);
        job.setMapperClass(TrainMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(HyperPlane.class);
        job.setReducerClass(TrainReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(1);

        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i] + "/Train/Input"));
        }

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1] + "/Train/Output"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
