package com.kamilens.buktap.service.mapper;

import com.kamilens.buktap.entity.BookAuthor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    default BookAuthor fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setId(id);
        return bookAuthor;
    }

}
