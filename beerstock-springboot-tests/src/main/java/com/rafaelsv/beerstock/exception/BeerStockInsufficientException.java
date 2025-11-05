package com.rafaelsv.beerstock.exception;

public class BeerStockInsufficientException extends RuntimeException {
    public BeerStockInsufficientException(int quantity) {
        super("Cannot decrement " + quantity + " - it would result in negative stock");
    }
}
