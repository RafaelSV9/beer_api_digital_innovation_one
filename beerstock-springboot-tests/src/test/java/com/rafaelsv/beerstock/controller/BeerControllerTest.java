package com.rafaelsv.beerstock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelsv.beerstock.dto.BeerDTO;
import com.rafaelsv.beerstock.service.BeerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturn201() throws Exception {
        BeerDTO req = new BeerDTO(null, "IPA", "BrewCo", 50, 10);
        BeerDTO resp = new BeerDTO(1L, "IPA", "BrewCo", 50, 10);

        Mockito.when(beerService.create(any(BeerDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/v1/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("IPA"));
    }

    @Test
    void list_shouldReturn200() throws Exception {
        Mockito.when(beerService.listAll()).thenReturn(Collections.singletonList(new BeerDTO(1L, "IPA", "BrewCo", 50, 10)));
        mockMvc.perform(get("/api/v1/beers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("IPA"));
    }

    @Test
    void findByName_shouldReturn200() throws Exception {
        Mockito.when(beerService.findByName("IPA")).thenReturn(new BeerDTO(1L, "IPA", "BrewCo", 50, 10));
        mockMvc.perform(get("/api/v1/beers/byName").param("name", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("BrewCo"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/beers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void increment_shouldReturn200() throws Exception {
        Mockito.when(beerService.increment(1L, 5)).thenReturn(new BeerDTO(1L, "IPA", "BrewCo", 50, 15));
        mockMvc.perform(patch("/api/v1/beers/1/increment").param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    void decrement_shouldReturn200() throws Exception {
        Mockito.when(beerService.decrement(1L, 3)).thenReturn(new BeerDTO(1L, "IPA", "BrewCo", 50, 7));
        mockMvc.perform(patch("/api/v1/beers/1/decrement").param("quantity", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(7));
    }
}
