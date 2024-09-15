package org.jt.book_store.dto;

import java.time.LocalDate;
import java.util.List;

import org.jt.book_store.model.BookFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDetailsResponse {

        private int bookId;
        private String bookName;
        private String authorName;
        private int totalPage;
        private String catagory;
        private LocalDate publicationDate;
        private BookFormat bookFormat;
        private List<String> availability;
        private String description;

}
