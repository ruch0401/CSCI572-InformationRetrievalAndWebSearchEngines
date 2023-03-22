package org.ir.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.ir.Constants;

import java.io.IOException;
import java.util.StringTokenizer;

public class UnigramMapper extends Mapper<Object, Text, Text, Text> {
    private final Text word = new Text();
    private final Text docInfo = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] doc = value.toString().split(Constants.TAB_CHARACTER, 2);
        String docId = doc[0];
        String line = doc[1];

        line = line.replaceAll(Constants.REGEX_FILTER_ALL_NON_ALPHABETS, Constants.SPACE_CHARACTER).toLowerCase();
        StringTokenizer itr = new StringTokenizer(line);
        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            docInfo.set(docId + ":" + 1);
            context.write(word, docInfo);
        }
    }
}
