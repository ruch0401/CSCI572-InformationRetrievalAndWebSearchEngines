package org.ir.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.ir.Constants;

import java.io.IOException;

public class BigramMapper extends Mapper<Object, Text, Text, Text> {
    private final Text docInfo = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] doc = value.toString().split(Constants.TAB_CHARACTER, 2);
        String docId = doc[0];
        String line = doc[1];

        line = line.replaceAll(Constants.REGEX_FILTER_ALL_NON_ALPHABETS, Constants.SPACE_CHARACTER).toLowerCase();
        String[] words = line.split(Constants.ONE_OR_MORE_SPACE_REGEX);
        for (int i = 0; i < words.length - 1; i++) {
            String currentBigram = String.format("%s %s", words[i], words[i + 1]);
            if (Constants.CHOSEN_BIGRAMS.contains(currentBigram)) {
                docInfo.set(docId + ":" + 1);
                context.write(new Text(currentBigram), docInfo);
            }
        }
    }
}
