package com.kuroko.heathyapi.feature.weight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/weight")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class WeightController {

    @Autowired
    private WeightService weightService;

    @PostMapping
    public ResponseEntity<WeightUpdatedDto> create(@RequestAttribute("email") String email,
            @RequestBody WeightDto weightDto) {
        WeightUpdatedDto weight = weightService.createWeight(email, weightDto);
        return ResponseEntity.ok().body(weight);
    }

}
