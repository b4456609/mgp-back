package soselab.mgp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import soselab.mgp.model.PactDSL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * Created by bernie on 2017/4/16.
 */
public class PactGenerator {
    public static void build(String provider, String consumer){
        PactDSL pactDSL = new PactDSL(provider, consumer);
        try {
            Path path = Paths.get("build/serviceTest/" + consumer + "-" + provider + ".json");
            Files.createDirectories(path.getParent());
            Files.write(path,
                    Collections.singletonList(JSON.toJSONString(pactDSL, SerializerFeature.PrettyFormat)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
