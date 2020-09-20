package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.Book;
import com.kamilens.buktap.entity.BookImage;
import com.kamilens.buktap.entity.BookPage;
import com.kamilens.buktap.repository.BookRepository;
import com.kamilens.buktap.service.BookFileStorageService;
import com.kamilens.buktap.service.BookService;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.dto.BookUpdateDTO;
import com.kamilens.buktap.service.mapper.BookMapper;
import com.kamilens.buktap.web.rest.error.NotFoundException;
import com.kamilens.buktap.web.rest.vm.BookVM;
import com.kamilens.buktap.web.rest.vm.IdVM;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookFileStorageService bookFileStorageService;

    private static final class BookNotFoundException extends NotFoundException {
        public BookNotFoundException() {
            super("Book not found");
        }
    }

    @Override
    public IdVM create(BookCreateDTO bookCreateDTO) throws IOException {
        String bookUniquePath = UUID.randomUUID().toString();

        BookImage bookImage = bookFileStorageService
                .storeBookImage(bookUniquePath, bookCreateDTO.getImage());

        Set<BookPage> bookPages = bookFileStorageService
                .storeBookPages(bookUniquePath, bookCreateDTO.getFile(), bookCreateDTO.getPreviewPages());

        Book mappedBook = bookMapper.createDTOToEntity(bookCreateDTO, bookUniquePath, bookImage, bookPages);

        Book savedBook = bookRepository.save(mappedBook);

        return IdVM.builder().id(savedBook.getId()).build();
    }

    @Override
    public Page<BookVM> getAll(Specification<Book> bookSpecification, Pageable pageable) {
        return bookRepository.findAll(bookSpecification, pageable).map(bookMapper::entityToVM);
    }

    @Override
    public BookVM getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookMapper.entityToVM(book);
    }

    @Override
    public IdVM update(BookUpdateDTO bookUpdateDTO) throws IOException {
        Book book = bookRepository.findById(bookUpdateDTO.getId()).orElseThrow(BookNotFoundException::new);

        BookImage bookImage = bookFileStorageService
                .storeBookImage(book.getStorageFolder(), bookUpdateDTO.getImage());

        Book mappedBook = bookMapper.updateDTOToEntity(bookUpdateDTO, bookImage);

        Book savedBook = bookRepository.save(mappedBook);

        return IdVM.builder().id(savedBook.getId()).build();
    }

    @Override
    public IdVM delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
        return IdVM.builder().id(id).build();
    }

}
