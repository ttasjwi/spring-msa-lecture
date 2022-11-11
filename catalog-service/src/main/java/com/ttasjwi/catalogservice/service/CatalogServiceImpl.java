package com.ttasjwi.catalogservice.service;

import com.ttasjwi.catalogservice.domain.CatalogEntity;
import com.ttasjwi.catalogservice.domain.CatalogRepository;
import com.ttasjwi.catalogservice.service.dto.CatalogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogDto> findAll() {
        List<CatalogEntity> catalogs = catalogRepository.findAll();

        List<CatalogDto> results = catalogs.stream()
                .map(CatalogDto::new)
                .collect(Collectors.toList());

        return results;
    }
}
