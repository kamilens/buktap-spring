package com.kamilens.buktap.service;

import com.kamilens.buktap.service.dto.BookCreateDTO;
import com.kamilens.buktap.web.rest.vm.IdResponseVM;

public interface BookService {

    IdResponseVM create(BookCreateDTO bookCreateDTO);

}
