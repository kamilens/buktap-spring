package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.BookImage;
import com.kamilens.buktap.entity.BookPage;
import com.kamilens.buktap.service.BookFileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BookFileStorageServiceImpl implements BookFileStorageService {

    @Value("${buktap.file-storage-path.books.main-path}")
    private String mainPath;

    @Value("${buktap.file-storage-path.books.pdf-pages-suffix}")
    private String pdfPagesSuffix;

    @Value("${buktap.file-storage-path.books.book-images-suffix}")
    private String bookImagesSuffix;

    @PostConstruct
    private void postConstruct() throws IOException {
        if (Files.notExists(Path.of(mainPath))) {
            Files.createDirectories(Path.of(mainPath));
        }
    }

    public BookImage storeBookImage(String bookUniquePath,
                                    MultipartFile imageFile) throws IOException {
        String imageFilePath = String.format(
                "%s/%s/%s",
                mainPath, bookUniquePath,
                bookImagesSuffix
        );

        Files.createDirectories(Path.of(imageFilePath));

        String fullPath =  String.format(
                "%s/%s.%s",
                imageFilePath,
                UUID.randomUUID().toString(),
                FilenameUtils.getExtension(imageFile.getOriginalFilename())
        );

        Files.writeString(Paths.get(fullPath), Arrays.toString(imageFile.getBytes()));

        return BookImage
                .builder()
                .original(fullPath)
                .thumbnail(fullPath)
                .build();
    }

    public Set<BookPage> storeBookPages(String bookUniquePath,
                                        MultipartFile bookFile,
                                        Set<Long> previewPages) throws IOException {
        PDDocument document = PDDocument.load(bookFile.getBytes());
        Splitter splitter = new Splitter();
        List<PDDocument> Pages = splitter.split(document);

        Iterator<PDDocument> iterator = Pages.listIterator();

        Set<BookPage> bookPages = new HashSet<>();

        long i = 0;
        BookPage bookPage;
        String mainPathLocal = mainPath, fullPath;

        String bookPagePath = String.format("%s/%s/%s", mainPathLocal, bookUniquePath, pdfPagesSuffix);
        Files.createDirectories(Path.of(bookPagePath));

        while (iterator.hasNext()) {
            PDDocument pd = iterator.next();
            fullPath = String.format("%s/%s-%d.pdf", bookPagePath , UUID.randomUUID().toString(), i++);
            pd.save(fullPath);
            bookPage = BookPage
                    .builder()
                    .file(fullPath)
                    .page(i)
                    .preview(previewPages.contains(i))
                    .build();

            bookPages.add(bookPage);
        }

        document.close();

        return bookPages;
    }

}
