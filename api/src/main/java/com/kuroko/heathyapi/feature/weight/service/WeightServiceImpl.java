package com.kuroko.heathyapi.feature.weight.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.weight.Weight;
import com.kuroko.heathyapi.feature.weight.WeightRepository;
import com.kuroko.heathyapi.feature.weight.dto.WeightDto;
import com.kuroko.heathyapi.feature.weight.dto.WeightUpdatedDto;

@Service
public class WeightServiceImpl implements WeightService {
    @Autowired
    private WeightRepository weightRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public WeightUpdatedDto createWeight(Long id, WeightDto weightDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with id " + id + " not found."));
        user.setWeight(weightDto.getWeight());
        userRepository.save(user);
        // update or create if not exists today weight of user
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
