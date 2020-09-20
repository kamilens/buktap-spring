package com.kamilens.buktap.web.rest;

import com.kamilens.buktap.entity.Book;
import com.kamilens.buktap.entity.enumeration.UserPermission;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.dto.BookUpdateDTO;
import com.kamilens.buktap.web.rest.vm.BookVM;
import com.kamilens.buktap.web.rest.vm.IdVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = "Book resource")
@RequestMapping("/api/books")
public interface BookController {

    @ApiOperation("Create Book")
    @PreAuthorize("hasPermission(\"" + UserPermission.Names.BOOK_WRITE + "\")")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<IdVM> create(@ModelAttribute @Valid BookCreateDTO bookCreateDTO) throws IOException;

    @ApiOperation("Get all Books")
    @PreAuthorize("hasPermission(\"" + UserPermission.Names.BOOK_READ + "\")")
    @GetMapping
    ResponseEntity<List<BookVM>> getAll(Specification<Book> bookSpecification, Pageable pageable);

    @ApiOperation("Get Book by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(\"" + UserPermission.Names.BOOK_READ + "\")")
    ResponseEntity<BookVM> getById(@PathVariable Long id);

    @ApiOperation("Update Book")
    @PutMapping
    @PreAuthorize("hasPermission(\"" + UserPermission.Names.BOOK_WRITE + "\")")
    ResponseEntity<IdVM> update(@RequestBody @Valid BookUpdateDTO bookUpdateDTO) throws IOException;

    @ApiOperation("Delete Book by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(\"" + UserPermission.Names.BOOK_WRITE + "\")")
    ResponseEntity<IdVM> delete(@PathVariable Long id);

}
