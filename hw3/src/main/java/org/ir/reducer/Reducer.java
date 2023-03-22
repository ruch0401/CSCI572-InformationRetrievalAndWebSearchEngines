package org.ir.reducer;

import org.apache.hadoop.io.Text;
import org.ir.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<String, Integer> hashMap = new HashMap<>();
        for (Text value : values) {
            String[] item = value.toString().split(Constants.COLON_CHARACTER);
            String word = item[0];
            String wordOccurrenceDefaultCount = item[1].trim();
            hashMap.put(word, hashMap.getOrDefault(word, Integer.parseInt(wordOccurrenceDefaultCount)) + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            sb.append(entry.getKey()).append(Constants.COLON_CHARACTER).append(entry.getValue()).append(Constants.SPACE_CHARACTER);
        }
        result.set(sb.toString());
        context.write(key, result);
    }
}
