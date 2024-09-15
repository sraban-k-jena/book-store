package org.jt.book_store.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data

public class BookStore {
    private String bookName;
    private String authorName;
    private int totalPage;
    private LocalDate publicationDate;
    // private String bookFormat;
    private BookFormat bookFormat;
    private String catagory;
    private List<String> availability;
    private String description;
    private MultipartFile bookImage;
}
