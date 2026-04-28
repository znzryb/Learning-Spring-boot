package com.example.helloandapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private List<MyBook> myBookArrayList = new ArrayList<>();

    public static class MyBook {
        public int id;
        public String title;
        public String author;
    }

    @GetMapping
    public List<MyBook> getAllBooks() {
        return myBookArrayList;
    }

    @PostMapping
    public MyBook addBooks(@RequestBody MyBook myBook) {
        myBook.id=myBookArrayList.size()+1;
        myBookArrayList.add(myBook);
        return myBook;
    }

    @GetMapping("/{id}")
    public MyBook getBooksById(@PathVariable int id) {
        id-=1;
        if (id < 0 || id >= myBookArrayList.size()) return null;
        return myBookArrayList.get(id);
    }

}
