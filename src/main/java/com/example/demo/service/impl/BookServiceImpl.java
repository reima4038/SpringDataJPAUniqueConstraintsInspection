package com.example.demo.service.impl;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repos;

    @Autowired
    public BookServiceImpl(BookRepository repos) {
        this.repos = repos;
    }

    @Override
    public void register(String storeName) {
        repos.register(new Book(storeName));
    }


    @Override
    public void update(String id, String storeName) {
        final Function<Book, Book> modifyName = bs -> {
            bs.modify(storeName);
            return bs;
        };
        Optional<Book> entity = this.repos.findById(id);
        entity.map(modifyName)
                .ifPresent(bs -> repos.update(bs));
    }

    @Override
    public void evilUpdate(String id, String storeName) {
        String entityId = this.repos.findById(id)
                .get()
                .id();
        // evil code
        Book entity = new Book(id, storeName);
        repos.update(entity);
    }

}
