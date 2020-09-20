package com.kamilens.buktap.web.rest.impl;

import com.kamilens.buktap.service.BookService;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.dto.BookUpdateDTO;
import com.kamilens.buktap.web.rest.BookController;
import com.kamilens.buktap.web.rest.util.PaginationUtil;
import com.kamilens.buktap.web.rest.vm.BookVM;
import com.kamilens.buktap.web.rest.vm.IdVM;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookControllerImpl implements BookController {

    private final BookService bookService;
    private final PaginationUtil paginationUtil;

    public ResponseEntity<IdVM> create(@Valid BookCreateDTO bookCreateDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookCreateDTO));
    }

    @Override
    public ResponseEntity<List<BookVM>> getAll(Pageable pageable) {
        Page<BookVM> page = bookService.getAll(pageable);
        HttpHeaders headers = paginationUtil.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Override
    public ResponseEntity<BookVM> getById(Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @Override
    public ResponseEntity<IdVM> update(@Valid BookUpdateDTO bookUpdateDTO) throws IOException {
        return ResponseEntity.ok(bookService.update(bookUpdateDTO));
    }

    @Override
    public ResponseEntity<IdVM> delete(Long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

}
