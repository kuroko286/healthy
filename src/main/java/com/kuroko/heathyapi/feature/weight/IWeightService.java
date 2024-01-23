package com.kuroko.heathyapi.feature.weight;

public interface IWeightService {
    WeightUpdatedDto createWeight(String email, WeightDto weightDto);
}
