package com.shreyes.mapreduce.Predict;

import com.shreyes.mapreduce.App;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class PredictJob {
    public static void initiate (String[] args) throws Exception{
        Configuration conf = new Configuration();

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        if (otherArgs.length < 2) {
            System.err.println("Please check the number of arguments!");
            System.exit(2);
        }

        Job job = Job.getInstance(conf, "Image Classification");

        job.addCacheFile(new Path(otherArgs[0] + "/Train/Output/part-r-00000").toUri());
        job.setJarByClass(App.class);
        job.setMapperClass(PredictMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setNumReduceTasks(0);

        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i] + "/Predict/Input"));
        }

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1] + "/Predict/Output"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
