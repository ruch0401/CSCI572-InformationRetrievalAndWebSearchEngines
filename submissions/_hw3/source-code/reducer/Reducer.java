package org.ir.reducer;

import org.apache.hadoop.io.Text;
import org.ir.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<Long, Integer> hashMap = new TreeMap<>();
        for (Text value : values) {
            String[] item = value.toString().split(Constants.COLON_CHARACTER);
            Long docId = Long.parseLong(item[0]);
            int docIdOccurrenceCount = Integer.parseInt(item[1].trim());
            hashMap.put(docId, hashMap.getOrDefault(docId, 0) + docIdOccurrenceCount);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, Integer> entry : hashMap.entrySet()) {
            sb.append(entry.getKey()).append(Constants.COLON_CHARACTER).append(entry.getValue()).append(Constants.TAB_CHARACTER);
        }
        result.set(sb.toString());
        context.write(key, result);
    }
}
