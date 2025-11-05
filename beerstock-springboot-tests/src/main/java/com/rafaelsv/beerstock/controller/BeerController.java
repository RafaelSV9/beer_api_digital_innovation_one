package com.rafaelsv.beerstock.controller;

import com.rafaelsv.beerstock.dto.BeerDTO;
import com.rafaelsv.beerstock.service.BeerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping
    public ResponseEntity<BeerDTO> create(@RequestBody BeerDTO beerDTO) {
        BeerDTO created = beerService.create(beerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<BeerDTO> list() {
        return beerService.listAll();
    }

    @GetMapping("/byName")
    public BeerDTO findByName(@RequestParam String name) {
        return beerService.findByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        beerService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public BeerDTO increment(@PathVariable Long id, @RequestParam int quantity) {
        return beerService.increment(id, quantity);
    }

    @PatchMapping("/{id}/decrement")
    public BeerDTO decrement(@PathVariable Long id, @RequestParam int quantity) {
        return beerService.decrement(id, quantity);
    }
}
