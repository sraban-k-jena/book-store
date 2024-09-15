package org.jt.book_store.service;

import java.util.List;

import org.jt.book_store.dto.BookDetailsResponse;
import org.jt.book_store.dto.HomePageResponse;
import org.jt.book_store.model.BookStore;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookStoreServiceImpl1 implements BookStoreService {
    private final JdbcClient jdbcClient;

    @Override
    public void createBook(BookStore bookStore, String filePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBook'");
    }

    @Override
    public BookDetailsResponse getBook(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBook'");
    }

    @Override
    public String findImageById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findImageById'");
    }

    @Override
    public List<HomePageResponse> getBooksNameAndId() {
        String sql = "select book_id,book_name from book_store";
        return jdbcClient.sql(sql).query(HomePageResponse.class).list();
    }

    @Override
    public void deleteBookById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBookById'");
    }

    @Override
    public void updateBook(int bookId, BookStore bookStore) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBook'");
    }

    @Override
    public void updateImage(int bookId, MultipartFile bookImage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateImage'");
    }

}
