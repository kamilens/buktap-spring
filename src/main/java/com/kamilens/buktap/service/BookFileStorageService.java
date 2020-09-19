package com.kamilens.buktap.service;

import com.kamilens.buktap.entity.BookImage;
import com.kamilens.buktap.entity.BookPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface BookFileStorageService {

    BookImage storeBookImage(String bookUniquePath, MultipartFile imageFile) throws IOException;

    Set<BookPage> storeBookPages(String bookUniquePath,
                                        MultipartFile bookFile,
                                        Set<Long> previewPages) throws IOException;

}
