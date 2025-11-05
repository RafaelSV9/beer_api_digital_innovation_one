package com.rafaelsv.beerstock.exception;

public class BeerStockExceededException extends RuntimeException {
    public BeerStockExceededException(int quantity, int max) {
        super("Cannot increment " + quantity + " - it would exceed max stock " + max);
    }
}
