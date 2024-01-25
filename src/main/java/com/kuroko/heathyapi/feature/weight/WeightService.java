package com.kuroko.heathyapi.feature.weight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.user.UserRepository;

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
        Optional<Weight> optionalWeight = weightRepository.findByUserAndDate(LocalDate.now(), user);
        if (optionalWeight.isPresent()) {
            Weight weight = optionalWeight.get();
            weight.setWeight(weightDto.getWeight());
            weightRepository.save(weight);
        } else {
            Weight weight = mapToWeight(weightDto);
            weight.setUser(user);
            weightRepository.save(weight);
        }
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
