package com.rafaelsv.beerstock.exception;

public class BeerNotFoundException extends RuntimeException {
    public BeerNotFoundException(Long id) {
        super("Beer not found with id: " + id);
    }
    public BeerNotFoundException(String name) {
        super("Beer not found with name: " + name);
    }
}
