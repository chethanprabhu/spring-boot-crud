package com.example.demo.controller;

import com.example.demo.dto.PersonRequestDto;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/persons/{id}")
    public Person getPersonById(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @PostMapping("/persons")
    public Person createPerson(@RequestBody PersonRequestDto request) {
        return personService.create(request);
    }

    @PatchMapping("/persons/{id}")
    public void updatePerson(@PathVariable int id, @RequestBody PersonRequestDto person) {
        personService.update(id, person);
    }

    @DeleteMapping("/persons/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.delete(id);
    }
}
