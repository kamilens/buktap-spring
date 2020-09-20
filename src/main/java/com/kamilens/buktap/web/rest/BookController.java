package com.kamilens.buktap.web.rest;

import com.kamilens.buktap.entity.enumeration.UserPermission;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.dto.BookUpdateDTO;
import com.kamilens.buktap.web.rest.vm.BookVM;
import com.kamilens.buktap.web.rest.vm.IdVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(tags = "Book resource")
@RequestMapping("/api/books")
public interface BookController {

    @ApiOperation("Create Book")
    @PreAuthorize("hasAuthority(\"" + UserPermission.Names.BOOK_WRITE + "\")")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<IdVM> create(@ModelAttribute BookCreateDTO bookCreateDTO) throws IOException;

    @ApiOperation("Get all Books")
    @PreAuthorize("hasAuthority(\"" + UserPermission.Names.BOOK_READ + "\")")
    @GetMapping
    ResponseEntity<List<BookVM>> getAll(Pageable pageable);

    @ApiOperation("Get Book by ID")
    @PreAuthorize("hasAuthority(\"" + UserPermission.Names.BOOK_READ + "\")")
    @GetMapping("/{id}")
    ResponseEntity<BookVM> getById(@PathVariable Long id);

    @ApiOperation("Update Book")
    @PreAuthorize("hasAuthority(\"" + UserPermission.Names.BOOK_WRITE + "\")")
    @PutMapping
    ResponseEntity<IdVM> update(@RequestBody BookUpdateDTO bookUpdateDTO) throws IOException;

    @ApiOperation("Delete Book by ID")
    @PreAuthorize("hasAuthority(\"" + UserPermission.Names.BOOK_WRITE + "\")")
    @DeleteMapping("/{id}")
    ResponseEntity<IdVM> delete(@PathVariable Long id);

}
