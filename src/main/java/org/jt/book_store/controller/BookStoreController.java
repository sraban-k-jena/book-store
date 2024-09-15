package org.jt.book_store.controller;

import java.io.IOException;

import org.jt.book_store.dto.BookDetailsResponse;
import org.jt.book_store.model.BookStore;
import org.jt.book_store.service.BookStoreService;
import org.jt.book_store.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookStoreController {

    public final BookStoreService bookStoreService;
    private final FileService fileService;

    @GetMapping({ "/", "/home" })
    public String home(Model model) {

        model.addAttribute("books", bookStoreService.getBooksNameAndId());
        return "home";
    }

    @GetMapping("/new-book")
    public String newBook(Model model) {
        model.addAttribute("book", new BookDetailsResponse());
        return "new-book";
    }

    @GetMapping("/details")
    public String details(@RequestParam int id, Model model) {
        var book = bookStoreService.getBook(id);
        model.addAttribute("book", book);
        return "book-details";
    }

    @PostMapping("/create-update-book")
    public String addBook(@ModelAttribute BookStore bookStore, @RequestParam int bookId) throws IOException {

        // (@RequestParam String bookName,
        // @RequestParam("authorName") String author,
        // @RequestParam("totalPage") int pages) {

        // String name = request.getParameter("bookName");
        // String author = request.getParameter("authorName");
        // String totalpage = request.getParameter("totalPage");

        // int pages = Integer.parseInt(totalpage);

        // System.out.println(bookName + "\t" + author + "\t" + pages);
        if (bookId == 0) {
            String path = fileService.uploadImage(bookStore.getBookImage());
            bookStoreService.createBook(bookStore, path);
            // System.out.println(bookStore);
        } else {
            bookStoreService.updateBook(bookId, bookStore);
            bookStoreService.updateImage(bookId, bookStore.getBookImage());
        }

        return "redirect:/home";
    }

    @GetMapping(path = "/book/image/{id}", produces = { "image/jpeg", "image/jpg", "image/png" })
    @ResponseBody
    public byte[] getImage(@PathVariable int id) throws IOException {
        String imageName = bookStoreService.findImageById(id);
        byte[] image = fileService.getImage(imageName);
        System.out.println(image);
        return image;
    }

    @GetMapping("/remove-book")
    public String removeBook(@RequestParam int id) {
        bookStoreService.deleteBookById(id);
        return "redirect:/home";
    }

    @GetMapping("/update-book")
    public String updateBook(@RequestParam int id, Model model) {
        var book = bookStoreService.getBook(id);
        model.addAttribute("book", book);
        return "new-book";
    }

}
