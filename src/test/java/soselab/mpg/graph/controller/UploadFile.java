package soselab.mpg.graph.controller;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;

import java.io.File;

public class UploadFile {

    public static void uploadMdpFile(TestRestTemplate restTemplate, String filepath) {
        Resource resource = new FileSystemResource(filepath);
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        ResponseEntity<String> result = restTemplate.exchange("/api/upload", HttpMethod.POST, requestEntity, String.class);
        System.out.println(result);
    }

    public static void uploadMdpFiles(TestRestTemplate restTemplate) {
        File file = new File(UploadFile.class.getResource("/mdp").getPath());
        File[] files = file.listFiles();
        System.out.println(files);
        for (File file1 : files) {
            uploadMdpFile(restTemplate, file1.getPath());
        }
    }

    public static void uploadMdpFilesFromDir(TestRestTemplate restTemplate, String dir) {
        File file = new File(UploadFile.class.getResource("/" + dir).getPath());
        File[] files = file.listFiles();
        System.out.println(files);
        for (File file1 : files) {
            uploadMdpFile(restTemplate, file1.getPath());
        }
    }
}