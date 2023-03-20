package org.ir;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HW3Reducer extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<String, Integer> hashMap = new HashMap<>();
        for (Text value : values) {
            String[] item = value.toString().split(":");
            hashMap.put(item[0], hashMap.getOrDefault(item[0], Integer.parseInt(item[1].trim())) + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
        }
        result.set(sb.toString());
        context.write(key, result);
    }
}
