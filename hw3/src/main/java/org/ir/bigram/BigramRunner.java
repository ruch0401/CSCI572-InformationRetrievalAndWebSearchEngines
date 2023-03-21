package org.ir.bigram;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.ir.unigram.UnigramMapper;
import org.ir.unigram.UnigramReducer;
import org.ir.unigram.UnigramRunner;

import java.io.IOException;

public class BigramRunner {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "IR-HW3 Bigram");

        job.setJarByClass(BigramRunner.class);
        job.setMapperClass(BigramMapper.class);
        job.setCombinerClass(BigramReducer.class);
        job.setReducerClass(BigramReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
