package org.jt.book_store.controller;

import org.jt.book_store.model.BookStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BookStoreController {

    @GetMapping({ "/", "/home" })
    public String home() {
        return "home";
    }

    @GetMapping("/new-book")
    public String newBook() {
        return "new-book";
    }

    @GetMapping("/details")
    public String details() {
        return "book-details";
    }

    @PostMapping("/create-book")
    public String addBook(@ModelAttribute BookStore bookStore) {

        // (@RequestParam String bookName,
        // @RequestParam("authorName") String author,
        // @RequestParam("totalPage") int pages) {

        // String name = request.getParameter("bookName");
        // String author = request.getParameter("authorName");
        // String totalpage = request.getParameter("totalPage");

        // int pages = Integer.parseInt(totalpage);

        // System.out.println(bookName + "\t" + author + "\t" + pages);
        System.out.println(bookStore);

        return "new-book";
    }
}
