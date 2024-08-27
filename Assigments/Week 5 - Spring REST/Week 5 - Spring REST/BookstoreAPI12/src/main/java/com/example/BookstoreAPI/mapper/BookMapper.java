package com.example.BookstoreAPI.mapper;

import com.example.BookstoreAPI.BookDTO;
import com.example.BookstoreAPI.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
}