package org.jt.book_store.service;

import java.util.List;

import org.jt.book_store.dto.BookDetailsResponse;
import org.jt.book_store.dto.HomePageResponse;
import org.jt.book_store.model.BookStore;
import org.springframework.web.multipart.MultipartFile;

public interface BookStoreService {
    /**
     * <h1>Create book</h1>
     */
    void createBook(BookStore bookStore, String filePath);

    BookDetailsResponse getBook(int id);

    String findImageById(int id);

    List<HomePageResponse> getBooksNameAndId();

    void deleteBookById(int id);

    void updateBook(int bookId, BookStore bookStore);

    void updateImage(int bookId, MultipartFile bookImage);
}
