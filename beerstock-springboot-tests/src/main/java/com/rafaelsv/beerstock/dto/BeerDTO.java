package com.rafaelsv.beerstock.dto;

public class BeerDTO {
    private Long id;
    private String name;
    private String brand;
    private int max;
    private int quantity;

    public BeerDTO() {}

    public BeerDTO(Long id, String name, String brand, int max, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.max = max;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
