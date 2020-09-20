package com.kamilens.buktap.service.dto;

import com.kamilens.buktap.entity.enumeration.Language;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Data
public class BookUpdateDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @NotNull
    private MultipartFile image;

    @NotNull
    private Set<Long> authors;

    @Size(min = 1)
    @NotNull
    private Set<Long> genres;

    @NotNull
    @Past
    private Date publishedDate;

    @NotNull
    @NotBlank
    @NotEmpty
    @ISBN
    private String isbn;

    @NotNull
    private Language language;

    @NotNull
    @Size(min = 3, max = 5)
    private Set<Long> previewPages;

}
