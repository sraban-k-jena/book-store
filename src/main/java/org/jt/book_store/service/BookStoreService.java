package org.jt.book_store.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jt.book_store.dto.HomePageResponse;
import org.jt.book_store.model.BookStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookStoreService {
    private final JdbcTemplate jdbcTemplate;

    public void createBook(BookStore bookStore) {
        String sql = """
                insert into book_store(
                book_name,author_name,total_page,publication_date,book_format,catagory,availability,book_description
                )VALUES(?,?,?,?,?,?,?,?)
                """;

        jdbcTemplate.update(sql,
                bookStore.getBookName(),
                bookStore.getAuthorName(),
                bookStore.getTotalPage(),
                bookStore.getPublicationDate(),
                bookStore.getBookFormat().name(),
                bookStore.getCatagory(),
                listToCSV(bookStore.getAvailability()),
                bookStore.getDescription()

        );
    }

    public List<HomePageResponse> getBooksNameAndId() {
        String sql = "select book_id,book_name from book_store";

        return jdbcTemplate.query(sql, homePageRowMapper());
    }

    private RowMapper<HomePageResponse> homePageRowMapper() {
        return (resultSet, rowNumber) -> {
            int bookId = resultSet.getInt("book_id");
            String bookName = resultSet.getString("book_name");
            return new HomePageResponse(bookId, bookName);
        };
    }

    private String listToCSV(List<String> list) {
        return list.stream().collect(Collectors.joining(","));
    }
}
