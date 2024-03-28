package com.kuroko.heathyapi.feature.water.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.feature.water.WaterRepository;
import com.kuroko.heathyapi.feature.water.dto.WaterDto;

@Service
public class WaterServiceImpl implements WaterService {
    @Autowired
    private WaterRepository waterRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Water> getWaterByUserAndDate(User user, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atStartOfDay().plusDays(1).minusSeconds(1);
        return waterRepository.findByUserAndTimeRange(user, start, end);
    }

    @Override
    public WaterDto addWaterIntake(Long id, WaterDto waterDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with id " + id + " not found."));
        Water water = mapToWater(waterDto);
        water.setUser(user);
        waterRepository.save(water);
        return mapToWaterDto(water);
    }

    @Override
    public WaterDto deleteWaterIntake(Long id) { // delete all water in current date
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with id " + id + " not found."));

        waterRepository.deleteByUserAndTimeRange(user, LocalDate.now().atStartOfDay(),
                LocalDate.now().atStartOfDay().plusDays(1));
        return null;
    }

    // mapping
    public Water mapToWater(WaterDto waterDto) {
        Water water = new Water();
        water.setAmount(waterDto.getMl());
        water.setCreatedAt(LocalDateTime.now());
        return water;
    }

    public WaterDto mapToWaterDto(Water water) {
        WaterDto waterDto = new WaterDto();
        waterDto.setMl(water.getAmount());
        return waterDto;
    }

}
