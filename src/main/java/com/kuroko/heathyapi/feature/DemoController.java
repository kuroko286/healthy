package com.kuroko.heathyapi.feature;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/demo")
public class DemoController {
    @GetMapping
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured endpoint change xcaASDAS");
    }
}
