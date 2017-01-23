package soselab.mpg.codegen.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.codegen.model.CodeSnippet;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CodeGenClientTest {

    @Autowired
    CodeGenClient codeGenClient;

    @Test
    public void getCodeSnippet() throws Exception {
        CodeSnippet post = codeGenClient.getCodeSnippet("POST", "http://what/");
        System.out.println(post);
    }

}