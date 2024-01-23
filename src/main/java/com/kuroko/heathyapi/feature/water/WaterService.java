package com.kuroko.heathyapi.feature.water;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.service.JwtService;

@Service
public class WaterService implements IWaterService {
    @Autowired
    private WaterRepository waterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Water> getWaterByUserAndCreatedAt(User user, Date date) {
        return waterRepository.findByUserAndCreatedAt(user, date);
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

        waterRepository.deleteByUserAndCreatedAt(user, LocalDate.now().atStartOfDay(),
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
