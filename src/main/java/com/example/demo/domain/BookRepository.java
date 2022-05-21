package com.example.demo.domain;

import java.util.Optional;

public interface BookRepository {
    public void register(Book book);
    public String update(Book book);

    public Optional<Book> findById(String id);
}
