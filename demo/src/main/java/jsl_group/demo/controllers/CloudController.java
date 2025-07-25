package jsl_group.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CloudController {
    private final RestTemplate restTemplate;

    public CloudController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/cloud")
    public String cloud() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://httpbin.org/post", "Hello, Cloud!", String.class);
        return responseEntity.getBody();
    }
}
