package com.kuroko.heathyapi.feature.weight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.weight.dto.WeightDto;
import com.kuroko.heathyapi.feature.weight.dto.WeightUpdatedDto;
import com.kuroko.heathyapi.feature.weight.service.WeightServiceImpl;

@RestController
@RequestMapping("/v1/weights")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeightController {

    @Autowired
    private WeightServiceImpl weightService;

}
