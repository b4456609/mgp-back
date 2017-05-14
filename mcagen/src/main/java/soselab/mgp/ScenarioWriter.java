package soselab.mgp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by bernie on 2017/5/14.
 */
public class ScenarioWriter {

    public static void write(Map<String, Set<String>> stringSetMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Feature: test\n");
        for (String s : stringSetMap.keySet()) {
            Set<String> strings = stringSetMap.get(s);
            for (String string : strings) {
                stringBuilder.append(string + "\n");
            }
            stringBuilder.append("Scenario: " + s + "\n");
        }
        try {
            Path path = Paths.get("build/feature/scenario.feature");
            Files.createDirectories(path.getParent());
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
