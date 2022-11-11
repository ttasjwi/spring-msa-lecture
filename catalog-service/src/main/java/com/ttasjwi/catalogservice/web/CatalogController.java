package com.ttasjwi.catalogservice.web;

import com.ttasjwi.catalogservice.service.CatalogService;
import com.ttasjwi.catalogservice.service.dto.CatalogDto;
import com.ttasjwi.catalogservice.web.dto.CatalogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String status(HttpServletRequest request) {
        int port = request.getLocalPort();
        log.info("Server port = {}", port);
        return String.format("It's working in Catalog Service on Port %d", port);
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogResponse>> findAll() {
        List<CatalogDto> dtos = catalogService.findAll();

        List<CatalogResponse> responses = dtos.stream()
                .map(CatalogResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

}
