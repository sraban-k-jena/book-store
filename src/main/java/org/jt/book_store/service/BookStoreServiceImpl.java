package org.jt.book_store.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.jt.book_store.dto.BookDetailsResponse;
import org.jt.book_store.dto.HomePageResponse;
import org.jt.book_store.model.BookFormat;
import org.jt.book_store.model.BookStore;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class BookStoreServiceImpl implements BookStoreService {
    private final JdbcTemplate jdbcTemplate;
    private final FileService fileService;

    public void createBook(BookStore bookStore, String filePath) {
        String sql = """
                insert into book_store(
                book_name,author_name,total_page,publication_date,book_format,catagory,availability,book_description,image
                )VALUES(?,?,?,?,?,?,?,?,?)
                """;

        jdbcTemplate.update(sql,
                bookStore.getBookName(),
                bookStore.getAuthorName(),
                bookStore.getTotalPage(),
                bookStore.getPublicationDate(),
                bookStore.getBookFormat().name(),
                bookStore.getCatagory(),
                listToCSV(bookStore.getAvailability()),
                bookStore.getDescription(), filePath

        );
    }

    public List<HomePageResponse> getBooksNameAndId() {
        String sql = "select book_id,book_name from book_store";

        return jdbcTemplate.query(sql, homePageRowMapper());
    }

    public BookDetailsResponse getBook(int id) {
        String sql = "select * from book_store where book_id= ?";
        return jdbcTemplate.queryForObject(sql, getBookDetailsRowMapper(), id);
    }

    private RowMapper<BookDetailsResponse> getBookDetailsRowMapper() {
        return (resultSet, rowNumber) -> {

            int bookId = resultSet.getInt("book_id");
            String bookName = resultSet.getString("book_name");
            String authorName = resultSet.getString("author_name");
            int totalPage = resultSet.getInt("total_page");
            String catagory = resultSet.getString("catagory");
            Date publicationDate = resultSet.getDate("publication_date");
            String bookFormat = resultSet.getString("book_format");
            String availability = resultSet.getString("availability");
            String description = resultSet.getString("book_description");

            return new BookDetailsResponse(bookId, bookName, authorName, totalPage, catagory,
                    sqlDateToLocalDate(publicationDate), BookFormat.valueOf(bookFormat), csvToList(availability),
                    description);
        };
    }

    private LocalDate sqlDateToLocalDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return LocalDate.parse(formatter.format(date));
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

    private List<String> csvToList(String string) {
        return Arrays.stream(string.split(",")).toList();
    }

    public String findImageById(int id) {
        String sql = "select image from book_store where book_id=?";
        RowMapper<String> rowMapper = (resultSet, rn) -> resultSet.getString("image");
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteBookById(int id) {
        String sql = "delete from book_store where book_id=?";
        jdbcTemplate.update(sql, id);
    }

    public void updateBook(int bookId, BookStore bookStore) {
        String sql = """
                update book_store set book_name=?,author_name=?,total_page=?,publication_date=?,book_format=?,catagory=?,availability=?,book_description=? where book_id=?
                """;
        jdbcTemplate.update(sql,
                bookStore.getBookName(),
                bookStore.getAuthorName(),
                bookStore.getTotalPage(),
                bookStore.getPublicationDate(),
                bookStore.getBookFormat().name(),
                bookStore.getCatagory(),
                listToCSV(bookStore.getAvailability()),
                bookStore.getDescription(), bookId

        );
    }

    public void updateImage(int bookId, MultipartFile bookImage) {
        if (bookImage.isEmpty()) {
            try {
                String path = fileService.uploadImage(bookImage);
                String sql = "UPDATE book_store SET image=? where book_id=?";
                jdbcTemplate.update(sql, path, bookId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
