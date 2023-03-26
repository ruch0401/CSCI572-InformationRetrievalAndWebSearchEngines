package org.ir.runner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.ir.mapper.UnigramMapper;
import org.ir.reducer.Reducer;

import java.io.IOException;

public class UnigramRunner {
    private static final String HADOOP_JOB_NAME = "IR-HW3 Unigram Inverted Index";
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, HADOOP_JOB_NAME);

        job.setJarByClass(UnigramRunner.class);
        job.setMapperClass(UnigramMapper.class);
        job.setCombinerClass(Reducer.class);
        job.setReducerClass(Reducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
