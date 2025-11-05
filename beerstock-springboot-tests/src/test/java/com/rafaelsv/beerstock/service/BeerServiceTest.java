package com.rafaelsv.beerstock.service;

import com.rafaelsv.beerstock.dto.BeerDTO;
import com.rafaelsv.beerstock.exception.*;
import com.rafaelsv.beerstock.model.Beer;
import com.rafaelsv.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    @InjectMocks
    private BeerService beerService;

    private Beer ipa;

    @BeforeEach
    void setup() {
        ipa = new Beer(1L, "IPA", "BrewCo", 50, 10);
    }

    @Test
    void create_shouldPersist_whenNameNotRegistered() {
        when(beerRepository.findByNameIgnoreCase("IPA")).thenReturn(Optional.empty());
        when(beerRepository.save(any(Beer.class))).thenReturn(ipa);

        BeerDTO request = new BeerDTO(null, "IPA", "BrewCo", 50, 10);
        BeerDTO created = beerService.create(request);

        assertNotNull(created.getId());
        assertEquals("IPA", created.getName());
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void create_shouldThrow_whenNameAlreadyExists() {
        when(beerRepository.findByNameIgnoreCase("IPA")).thenReturn(Optional.of(ipa));
        BeerDTO request = new BeerDTO(null, "IPA", "BrewCo", 50, 10);
        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.create(request));
        verify(beerRepository, never()).save(any());
    }

    @Test
    void listAll_shouldReturnDTOs() {
        when(beerRepository.findAll()).thenReturn(Arrays.asList(ipa));
        List<BeerDTO> list = beerService.listAll();
        assertEquals(1, list.size());
        assertEquals("IPA", list.get(0).getName());
    }

    @Test
    void findByName_shouldReturn_whenExists() {
        when(beerRepository.findByNameIgnoreCase("IPA")).thenReturn(Optional.of(ipa));
        BeerDTO found = beerService.findByName("IPA");
        assertEquals("IPA", found.getName());
    }

    @Test
    void findByName_shouldThrow_whenNotFound() {
        when(beerRepository.findByNameIgnoreCase("Lager")).thenReturn(Optional.empty());
        assertThrows(BeerNotFoundException.class, () -> beerService.findByName("Lager"));
    }

    @Test
    void deleteById_shouldDelete_whenFound() {
        when(beerRepository.findById(1L)).thenReturn(Optional.of(ipa));
        beerService.deleteById(1L);
        verify(beerRepository).delete(ipa);
    }

    @Test
    void deleteById_shouldThrow_whenNotFound() {
        when(beerRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(BeerNotFoundException.class, () -> beerService.deleteById(2L));
    }

    @Test
    void increment_shouldIncrease_whenWithinMax() {
        when(beerRepository.findById(1L)).thenReturn(Optional.of(ipa));
        when(beerRepository.save(any(Beer.class))).thenAnswer(i -> i.getArgument(0));
        BeerDTO updated = beerService.increment(1L, 10);
        assertEquals(20, updated.getQuantity());
    }

    @Test
    void increment_shouldThrow_whenExceedsMax() {
        when(beerRepository.findById(1L)).thenReturn(Optional.of(ipa));
        assertThrows(BeerStockExceededException.class, () -> beerService.increment(1L, 100));
    }

    @Test
    void decrement_shouldDecrease_whenAboveZero() {
        when(beerRepository.findById(1L)).thenReturn(Optional.of(ipa));
        when(beerRepository.save(any(Beer.class))).thenAnswer(i -> i.getArgument(0));
        BeerDTO updated = beerService.decrement(1L, 5);
        assertEquals(5, updated.getQuantity());
    }

    @Test
    void decrement_shouldThrow_whenNegative() {
        when(beerRepository.findById(1L)).thenReturn(Optional.of(ipa));
        assertThrows(BeerStockInsufficientException.class, () -> beerService.decrement(1L, 999));
    }
}
