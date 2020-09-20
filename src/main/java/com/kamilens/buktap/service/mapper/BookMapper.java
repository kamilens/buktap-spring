package com.kamilens.buktap.service.mapper;

import com.kamilens.buktap.entity.Book;
import com.kamilens.buktap.entity.BookImage;
import com.kamilens.buktap.entity.BookPage;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.dto.BookUpdateDTO;
import com.kamilens.buktap.web.rest.vm.BookVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, BookGenreMapper.class})
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "bookCreateDTO.name")
    @Mapping(target = "image", source = "bookImage")
    @Mapping(target = "authors", source = "bookCreateDTO.authors")
    @Mapping(target = "genres", source = "bookCreateDTO.genres")
    @Mapping(target = "publishedDate", source = "bookCreateDTO.publishedDate")
    @Mapping(target = "isbn", source = "bookCreateDTO.isbn")
    @Mapping(target = "language", source = "bookCreateDTO.language")
    @Mapping(target = "uploadDate", ignore = true)
    @Mapping(target = "storageFolder", source = "storageFolder")
    @Mapping(target = "pages", source = "pages")
    Book createDTOToEntity(BookCreateDTO bookCreateDTO, String storageFolder, BookImage bookImage, Set<BookPage> pages);

    @Mapping(target = "id", source = "bookUpdateDTO.id")
    @Mapping(target = "name", source = "bookUpdateDTO.name")
    @Mapping(target = "image", source = "bookImage")
    @Mapping(target = "authors", source = "bookUpdateDTO.authors")
    @Mapping(target = "genres", source = "bookUpdateDTO.genres")
    @Mapping(target = "publishedDate", source = "bookUpdateDTO.publishedDate")
    @Mapping(target = "isbn", source = "bookUpdateDTO.isbn")
    @Mapping(target = "language", source = "bookUpdateDTO.language")
    @Mapping(target = "uploadDate", ignore = true)
    @Mapping(target = "storageFolder", ignore = true)
    @Mapping(target = "pages", ignore = true)
    Book updateDTOToEntity(BookUpdateDTO bookUpdateDTO, BookImage bookImage);

    BookVM entityToVM(Book book);

    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }

}
