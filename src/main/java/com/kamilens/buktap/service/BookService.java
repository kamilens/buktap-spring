package com.kamilens.buktap.service;

import com.kamilens.buktap.entity.Book;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.dto.BookUpdateDTO;
import com.kamilens.buktap.web.rest.vm.BookVM;
import com.kamilens.buktap.web.rest.vm.IdVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;

public interface BookService {

    IdVM create(BookCreateDTO bookCreateDTO) throws IOException;

    Page<BookVM> getAll(Specification<Book> bookSpecification, Pageable pageable);

    BookVM getById(Long id);

    IdVM update(BookUpdateDTO bookUpdateDTO) throws IOException;

    IdVM delete(Long id);

}
