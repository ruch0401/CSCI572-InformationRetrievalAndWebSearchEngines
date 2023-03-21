package org.ir.bigram;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class BigramMapper extends Mapper<Object, Text, Text, Text> {
    private final Text docInfo = new Text();

    private final List<String> chosenBigrams = List.of("computer science", "information retrieval", "power politics", "los angeles", "bruce willis");

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] doc = value.toString().split("\t", 2);
        String docId = doc[0];
        String line = doc[1];

        line = line.replaceAll("[^a-zA-Z\\s]", " ").toLowerCase();
        String[] words = line.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            String currentBigram = words[i] + " " + words[i + 1];
            if (chosenBigrams.contains(currentBigram)) {
                docInfo.set(docId + ":" + 1);
                context.write(new Text(currentBigram), docInfo);
            }
        }
    }
}
