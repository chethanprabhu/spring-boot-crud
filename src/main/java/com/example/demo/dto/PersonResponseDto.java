package com.example.demo.dto;

import com.example.demo.entity.Person;

import java.util.List;

public class PersonResponseDto {
    private List<Person> items;
    private long count;

    public List<Person> getItems() {
        return items;
    }

    public void setItems(List<Person> items) {
        this.items = items;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
