package com.kamilens.buktap.web.rest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class PaginationUtil {

    @Value("${pageable.headers.total-count}")
    private static String totalCount;

    @Value("${pageable.headers.page-number}")
    private static String pageNumber;

    public static <T> HttpHeaders generatePaginationHeaders(Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(totalCount, Long.toString(page.getTotalElements()));
        headers.add(pageNumber, Long.toString(page.getNumber()));
        return headers;
    }


}
