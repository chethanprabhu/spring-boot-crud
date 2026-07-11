package com.example.demo.service;

import com.example.demo.dto.PersonRequestDto;
import com.example.demo.entity.Person;
import com.example.demo.exception.PersonAgeLimitException;
import com.example.demo.exception.PersonNotFoundException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(int id) {
        Optional<Person> person = personRepository.findById(id);

        if(person.isEmpty()){
            throw new PersonNotFoundException("Person with id " + id + " not found");
        }
        return person.get();
    }

    @Transactional
    public Person create(PersonRequestDto request) {
        if(request.getAge() != null && request.getAge() > 60){
            throw new PersonAgeLimitException("Age is greater than 60");
        }

        Person person = personMapper.toEntity(request);

        personRepository.save(person);
        return person;
    }

    @Transactional
    public void update(int id, PersonRequestDto person) {
        Person existingPerson = personRepository.findById(id).orElseThrow(() ->
            new PersonNotFoundException("Person with id " + id + " not found")
        );

        if(person.getName() != null) {
            existingPerson.setName(person.getName());
        }

        if(person.getAddress() != null) {
            existingPerson.setAddress(person.getAddress());
        }

        if(person.getEmail() != null) {
            existingPerson.setEmail(person.getEmail());
        }

        if(person.getPhoneNumber() != null) {
            existingPerson.setPhoneNumber(person.getPhoneNumber());
        }

        if(person.getMarried() != null) {
            existingPerson.setMarried(person.getMarried());
        }

        if(person.getAge() != null) {
            existingPerson.setAge(person.getAge());
        }
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }
}
