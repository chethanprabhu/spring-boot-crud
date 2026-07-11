package com.example.demo.mapper;

import com.example.demo.dto.PersonRequestDto;
import com.example.demo.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonRequestDto request);
}
