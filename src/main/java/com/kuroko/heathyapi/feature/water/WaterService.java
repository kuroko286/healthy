package com.kuroko.heathyapi.feature.water;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.feature.user.User;

@Service
public class WaterService implements IWaterService {
    @Autowired
    private WaterRepository waterRepository;

    @Override
    public List<Water> getWaterByUserAndCreatedAt(User user, Date date) {
        // TODO Auto-generated method stub
        return waterRepository.findByUserAndCreatedAt(user, date);
    }

    @Override
    public void addWaterIntake(String token, WaterDto waterDto) {
        // TODO

    }

    @Override
    public void deleteWaterIntake(String token, WaterDto waterDto) {
        // TODO
    }

}
