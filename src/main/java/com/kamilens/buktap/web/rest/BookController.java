package com.kamilens.buktap.web.rest;

import com.kamilens.buktap.service.BookService;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.web.rest.vm.IdVM;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/books")
@RestController
public class BookController {

    private final BookService bookService;

    @SneakyThrows
    @PreAuthorize("hasAuthority('developers:read')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<IdVM> create(@ModelAttribute @Valid BookCreateDTO bookCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookCreateDTO));
    }

}
