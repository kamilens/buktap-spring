package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.Book;
import com.kamilens.buktap.entity.BookImage;
import com.kamilens.buktap.entity.BookPage;
import com.kamilens.buktap.repository.BookRepository;
import com.kamilens.buktap.service.BookService;
import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.service.mapper.BookMapper;
import com.kamilens.buktap.web.rest.vm.IdResponseVM;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @SneakyThrows
    @Override
    public IdResponseVM create(BookCreateDTO bookCreateDTO) {
        PDDocument document = PDDocument.load(bookCreateDTO.getFile().getBytes());
        Splitter splitter = new Splitter();
        List<PDDocument> Pages = splitter.split(document);

        Iterator<PDDocument> iterator = Pages.listIterator();

        Set<BookPage> bookPages = new HashSet<>();

        long i = 1;
        BookPage bookPage;
        String
                absPath = "C:/Users/kamil/Desktop/temp_storage",
                folderPath = UUID.randomUUID().toString(),
                fileName,
                fullPath;

        Files.createDirectory(Path.of(String.format("%s/%s/%s", absPath, "pdf_pages", folderPath)));
        Files.createDirectory(Path.of(String.format("%s/%s/%s", absPath, "book_images", folderPath)));

        while(iterator.hasNext()) {
            PDDocument pd = iterator.next();
            fileName = UUID.randomUUID().toString();
            fullPath = String.format("%s/%s/%s/%s-%d.pdf", absPath, "pdf_pages", folderPath, fileName, i++);
            pd.save(fullPath);
            bookPage = BookPage
                    .builder()
                    .file(fullPath)
                    .page(i)
                    .preview(bookCreateDTO.getPreviewPages().contains(i))
                    .build();

            bookPages.add(bookPage);
        }

        document.close();

        String imageFilePath = String.format("%s/%s/%s/%s.png", absPath, "book_images", folderPath, UUID.randomUUID().toString());

        BookImage bookImage = BookImage
                .builder()
                .original(imageFilePath)
                .thumbnail(imageFilePath)
                .build();

        Files.writeString(Paths.get(imageFilePath), Arrays.toString(bookCreateDTO.getImage().getBytes()));

        Book mappedBook = bookMapper.createDtoToEntity(bookCreateDTO, bookImage, bookPages);

        Book savedBook = bookRepository.save(mappedBook);

        return IdResponseVM.builder().id(savedBook.getId()).build();
    }

}
