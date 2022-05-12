package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.BookForm;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class DemoController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    Environment env;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

//    @GetMapping("")
//    public ModelAndView userPage(){
//        return new ModelAndView("listBook");
//        ModelAndView modelAndView = new ModelAndView("listBook");
//        modelAndView.addObject("book", bookService.findAll());
//        return modelAndView;
//    }
    @GetMapping("/admin")
    public ModelAndView adminPage(){
        return new ModelAndView("admin");
    }

//    @GetMapping("")
//    public ModelAndView showList(){
//        ModelAndView modelAndView = new ModelAndView("listBook");
//        modelAndView.addObject("book", bookService.findAll());
//        return modelAndView;
//    }

    @GetMapping("/user")
    public ResponseEntity<Iterable<Book>> getAll(){
        Iterable<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Book> saveBook(@ModelAttribute BookForm bookForm){
        MultipartFile multipartFile = bookForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("upload.path");
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Book book = new Book(bookForm.getName(),bookForm.getPrice(),bookForm.getAuthor(),fileName,bookForm.getCategory());
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

//    @PostMapping("/user")
//    public ResponseEntity<Book> create(@RequestBody Book book){
//        bookService.save(book);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @GetMapping("/category")
    public ResponseEntity<Iterable<Category>> getCategory(){
        Iterable<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.remove(id);
        return new ResponseEntity<>(bookOptional.get(),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookOptional.get(),HttpStatus.OK);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @ModelAttribute BookForm bookForm){
        Optional<Book> bookOptional = bookService.findById(id);
        bookForm.setId(bookOptional.get().getId());
        MultipartFile multipartFile = bookForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("upload.path");
        Book existBook = new Book(id, bookForm.getName(),bookForm.getPrice(),bookForm.getAuthor(),fileName,bookForm.getCategory());
        try {
            FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpload+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (existBook.getImage().equals("filename.jpg")){
            existBook.setImage(bookOptional.get().getImage());
        }
        bookService.save(existBook);
        return new ResponseEntity<>(existBook,HttpStatus.OK);
    }
}
