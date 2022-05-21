package com.example.demo.service;

public interface BookService {

    public void register(String storeName);
    public void update(String id, String storeName);
    public void evilUpdate(String id, String storeName);

}
