package com.kamilens.buktap.service;

import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.web.rest.vm.IdVM;

public interface BookService {

    IdVM create(BookCreateDTO bookCreateDTO);

}
