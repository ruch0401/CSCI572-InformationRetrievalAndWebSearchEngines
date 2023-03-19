package org.ir;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class HW3Mapper extends Mapper<Object, Text, Text, Text> {
    private final Text word = new Text();
    private final Text docInfo = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] doc = value.toString().split("\t", 2);
        String docId = doc[0];
        String line = doc[1];

        line = line.replaceAll("[^a-zA-Z\\s]", " ").toLowerCase();
        StringTokenizer itr = new StringTokenizer(line);
        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            docInfo.set(docId + ":" + 1);
            context.write(word, docInfo);
        }
    }
}
