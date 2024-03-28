package com.kuroko.heathyapi.feature.water;

import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.water.dto.WaterDto;
import com.kuroko.heathyapi.feature.water.service.WaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v1/water")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WaterController {
    @Autowired
    private WaterService waterService;

    // implement crud

}
