package com.kamilens.buktap.service.mapper;

import com.kamilens.buktap.entity.BookGenre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookGenreMapper {

    default BookGenre fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(id);
        return bookGenre;
    }

}
