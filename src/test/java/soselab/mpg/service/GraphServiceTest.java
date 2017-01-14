package soselab.mpg.service;

import org.junit.Test;

/**
 * Created by bernie on 2017/1/14.
 */
public class GraphServiceTest {

    @Test
    public void testRex() {
        String target = "easylearn-webback endpoint /note POST";
        String[] split = target.split(" ");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            System.out.println(s);
        }
    }

}