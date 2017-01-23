package soselab.mpg.codegen.client;

import soselab.mpg.codegen.model.CodeSnippet;

public interface CodeGenClient {
    CodeSnippet getCodeSnippet(String httpMethod, String path);
}
