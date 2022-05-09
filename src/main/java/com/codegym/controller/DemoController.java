package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DemoController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/user")
    public ModelAndView userPage(){
//        return new ModelAndView("listBook");
        ModelAndView modelAndView = new ModelAndView("listBook");
        modelAndView.addObject("book", bookService.findAll());
        return modelAndView;
    }
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

    @GetMapping("")
    public ResponseEntity<Iterable<Book>> getAll(){
        Iterable<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Book> create(@RequestBody Book book){
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
