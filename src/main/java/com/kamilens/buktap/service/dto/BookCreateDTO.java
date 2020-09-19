package com.kamilens.buktap.service.dto;

import com.kamilens.buktap.entity.enumeration.Language;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;

@Data
public class BookCreateDTO {

    private String name;
    private MultipartFile image;
    private Set<Long> authors;
    private Date publishedDate;
    private String isbn;
    private Language language;
    private MultipartFile file;
    private Set<Long> previewPages;

}
