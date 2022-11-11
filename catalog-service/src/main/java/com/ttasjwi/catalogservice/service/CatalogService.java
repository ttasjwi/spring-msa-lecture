package com.ttasjwi.catalogservice.service;

import com.ttasjwi.catalogservice.service.dto.CatalogDto;

import java.util.List;

public interface CatalogService {

    List<CatalogDto> findAll();

}
