package com.rafaelsv.beerstock.exception;

public class BeerAlreadyRegisteredException extends RuntimeException {
    public BeerAlreadyRegisteredException(String name) {
        super("Beer already registered with name: " + name);
    }
}
