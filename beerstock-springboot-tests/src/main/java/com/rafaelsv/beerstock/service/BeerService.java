package com.rafaelsv.beerstock.service;

import com.rafaelsv.beerstock.dto.BeerDTO;
import com.rafaelsv.beerstock.exception.*;
import com.rafaelsv.beerstock.mapper.BeerMapper;
import com.rafaelsv.beerstock.model.Beer;
import com.rafaelsv.beerstock.repository.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeerService {

    private final BeerRepository beerRepository;

    public BeerService(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public BeerDTO create(BeerDTO toCreate) {
        beerRepository.findByNameIgnoreCase(toCreate.getName()).ifPresent(b -> {
            throw new BeerAlreadyRegisteredException(toCreate.getName());
        });
        Beer saved = beerRepository.save(BeerMapper.toEntity(toCreate));
        return BeerMapper.toDTO(saved);
    }

    public List<BeerDTO> listAll() {
        return beerRepository.findAll().stream().map(BeerMapper::toDTO).collect(Collectors.toList());
    }

    public BeerDTO findByName(String name) {
        Beer beer = beerRepository.findByNameIgnoreCase(name).orElseThrow(() -> new BeerNotFoundException(name));
        return BeerMapper.toDTO(beer);
    }

    public void deleteById(Long id) {
        Beer beer = beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
        beerRepository.delete(beer);
    }

    public BeerDTO increment(Long id, int quantityToIncrement) {
        Beer beer = beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
        int newQty = beer.getQuantity() + quantityToIncrement;
        if (newQty > beer.getMax()) {
            throw new BeerStockExceededException(quantityToIncrement, beer.getMax());
        }
        beer.setQuantity(newQty);
        return BeerMapper.toDTO(beerRepository.save(beer));
    }

    public BeerDTO decrement(Long id, int quantityToDecrement) {
        Beer beer = beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
        int newQty = beer.getQuantity() - quantityToDecrement;
        if (newQty < 0) {
            throw new BeerStockInsufficientException(quantityToDecrement);
        }
        beer.setQuantity(newQty);
        return BeerMapper.toDTO(beerRepository.save(beer));
    }
}
