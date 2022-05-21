package com.example.demo.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Persistence;

@Entity
public class Book implements Persistable<String> {

    public Book(String storeName) {
        this.storeName = storeName;
    }
    public Book(){};
    // evil Constructor
    public Book(String id, String storeName){}

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String storeName;

    public String id() {
        return this.id;
    }
    public void modify(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String getId() {
        return id();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
