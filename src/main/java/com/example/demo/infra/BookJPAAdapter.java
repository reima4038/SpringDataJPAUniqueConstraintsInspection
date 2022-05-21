package com.example.demo.infra;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookJPAAdapter implements BookRepository {
    private final BookJPARepository repos;

    @Autowired
    public BookJPAAdapter(BookJPARepository repos){
        this.repos = repos;
    }

    @Override
    public void register(Book book) {
        repos.save(book);
    }

    @Override
    public String update(Book book) {
        repos.save(book);
        return book.id();
    }

    @Override
    public Optional<Book> findById(String id) {
        return repos.findById(id);
    }
}
