package com.example.demo.service;

import com.example.demo.dto.PersonRequestDto;
import com.example.demo.dto.PersonResponseDto;
import com.example.demo.entity.Person;
import com.example.demo.exception.PersonAgeLimitException;
import com.example.demo.exception.PersonNotFoundException;
import com.example.demo.exception.SortFieldNotAllowedException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.repository.PersonRepository;
import com.example.demo.specification.PersonSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("name", "address", "email");

    public PersonResponseDto getAllPersons(Boolean married, Integer minAge, Integer maxAge,  Pageable pageable) {
        pageable.getSort().forEach(order -> {
            if(!ALLOWED_SORT_FIELDS.contains(order.getProperty())) {
                throw new SortFieldNotAllowedException(("Sorting is allowed only on name, email and address fields."));
            }
        });

        Specification<Person> specification = Specification.where(PersonSpecification.hasMarried(married))
                .and(PersonSpecification.ageFilter(minAge, maxAge));

        Page<Person> page = personRepository.findAll(specification, pageable);

        PersonResponseDto response = new PersonResponseDto();
        response.setItems(page.getContent());
        response.setCount(page.getTotalElements());

        return response;
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
