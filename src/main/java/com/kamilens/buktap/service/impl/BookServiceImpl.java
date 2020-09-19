package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.Book;
import com.kamilens.buktap.entity.BookImage;
import com.kamilens.buktap.entity.BookPage;
import com.kamilens.buktap.repository.BookRepository;
import com.kamilens.buktap.service.BookFileStorageService;
import com.kamilens.buktap.service.BookService;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.mapper.BookMapper;
import com.kamilens.buktap.web.rest.vm.IdVM;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookFileStorageService bookFileStorageService;

    @SneakyThrows
    @Override
    public IdVM create(BookCreateDTO bookCreateDTO) {
        String bookUniquePath = UUID.randomUUID().toString();

        BookImage bookImage = bookFileStorageService
                .storeBookImage(bookUniquePath, bookCreateDTO.getImage());

        Set<BookPage> bookPages = bookFileStorageService
                .storeBookPages(bookUniquePath, bookCreateDTO.getFile(), bookCreateDTO.getPreviewPages());

        Book mappedBook = bookMapper.createDtoToEntity(bookCreateDTO, bookImage, bookPages);

        Book savedBook = bookRepository.save(mappedBook);

        return IdVM.builder().id(savedBook.getId()).build();
    }

}
