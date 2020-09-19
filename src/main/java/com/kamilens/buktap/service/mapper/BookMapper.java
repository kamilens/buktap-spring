package com.kamilens.buktap.service.mapper;

import com.kamilens.buktap.entity.Book;
import com.kamilens.buktap.entity.BookAuthor;
import com.kamilens.buktap.entity.BookImage;
import com.kamilens.buktap.entity.BookPage;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "bookCreateDTO.name"),
            @Mapping(target = "image", source = "bookImage"),
            @Mapping(target = "authors", expression = "java(mapIdToAuthor(bookCreateDTO.getAuthors()))"),
            @Mapping(target = "publishedDate", source = "bookCreateDTO.publishedDate"),
            @Mapping(target = "isbn", source = "bookCreateDTO.isbn"),
            @Mapping(target = "language", source = "bookCreateDTO.language"),
            @Mapping(target = "uploadDate", ignore = true),
            @Mapping(target = "pages", source = "pages")
    })
    Book createDtoToEntity(BookCreateDTO bookCreateDTO, BookImage bookImage, Set<BookPage> pages);

    default Set<BookAuthor> mapIdToAuthor(Set<Long> authors) {
        return authors
                .stream()
                .map(id -> BookAuthor.builder().id(id).build())
                .collect(Collectors.toSet());
    }

}
