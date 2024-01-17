package com.kuroko.heathyapi.feature.water;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController("/water")
public class WaterController {
    @Autowired
    private IWaterService waterService;

    @PostMapping("/water-intake")
    public ResponseEntity<?> addWaterIntake(@RequestHeader("Authorization") String token,
            @RequestBody WaterDto waterDto) {
        waterService.addWaterIntake(token, waterDto);
        return ResponseEntity.ok().body("Water intake added successfully");
    }

    @DeleteMapping("/water-intake")
    public ResponseEntity<?> deleteWaterIntake(@RequestHeader("Authorization") String token,
            @RequestBody WaterDto waterDto) {
        waterService.deleteWaterIntake(token, waterDto);
        return ResponseEntity.ok().body("Water intake deleted successfully");
    }

}
