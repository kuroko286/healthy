package com.kuroko.heathyapi.feature.weight;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.service.JwtService;

@Service
public class WeightService implements IWeightService {
    @Autowired
    private WeightRepository weightRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public WeightUpdatedDto createWeight(String email, WeightDto weightDto) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        user.setWeight(weightDto.getWeight());
        userRepository.save(user);
        Weight weight = mapToWeight(weightDto);
        weight.setUser(user);
        weightRepository.save(weight);
        return new WeightUpdatedDto(weightDto.getWeight(), user);
    }

    public Weight mapToWeight(WeightDto weightDto) {
        Weight weight = new Weight();
        weight.setWeight(weightDto.getWeight());
        weight.setCreatedAt(LocalDateTime.now());
        return weight;
    }

    public WeightDto mapToWeightDto(Weight weight) {
        WeightDto weightDto = new WeightDto();
        weightDto.setWeight(weight.getWeight());
        return weightDto;
    }

}
