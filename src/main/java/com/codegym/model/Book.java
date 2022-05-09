package com.codegym.model;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Long price;
    private String author;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    public Book() {
    }

    public Book(String name, Long price, String author) {
        this.name = name;
        this.price = price;
        this.author = author;
    }

    public Book(Long id, String name, Long price, String author) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
    }

    public Book(String name, Long price, String author, Category category) {
        this.name = name;
        this.price = price;
        this.author = author;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
