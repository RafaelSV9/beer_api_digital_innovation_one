package com.rafaelsv.beerstock.mapper;

import com.rafaelsv.beerstock.dto.BeerDTO;
import com.rafaelsv.beerstock.model.Beer;

public class BeerMapper {
    public static Beer toEntity(BeerDTO dto) {
        return new Beer(dto.getId(), dto.getName(), dto.getBrand(), dto.getMax(), dto.getQuantity());
    }
    public static BeerDTO toDTO(Beer entity) {
        return new BeerDTO(entity.getId(), entity.getName(), entity.getBrand(), entity.getMax(), entity.getQuantity());
    }
}
