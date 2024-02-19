package com.kuroko.heathyapi.feature.water.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.feature.water.WaterRepository;
import com.kuroko.heathyapi.feature.water.dto.WaterDto;

@Service
public class WaterServiceImpl implements WaterService {
    @Autowired
    private WaterRepository waterRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Water> getWaterByUserAndDate(User user, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atStartOfDay().plusDays(1).minusSeconds(1);
        return waterRepository.findByUserAndTimeRange(user, start, end);
    }

    @Override
    public WaterDto addWaterIntake(String email, WaterDto waterDto) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        Water water = mapToWater(waterDto);
        water.setUser(user);
        waterRepository.save(water);
        return mapToWaterDto(water);
    }

    @Override
    public WaterDto deleteWaterIntake(String email) { // delete all water in current date
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();

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
