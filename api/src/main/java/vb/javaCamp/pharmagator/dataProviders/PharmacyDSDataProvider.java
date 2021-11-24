package vb.javaCamp.pharmagator.dataProviders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.dataProviders.dto.ds.CategoryDto;
import vb.javaCamp.pharmagator.dataProviders.dto.ds.DSMedicineDto;
import vb.javaCamp.pharmagator.dataProviders.dto.ds.DSMedicinesResponse;
import vb.javaCamp.pharmagator.dataProviders.dto.ds.FilterRequest;

import java.util.Collection;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Qualifier("pharmacyDSDataProvider")
public class PharmacyDSDataProvider implements DataProvider {

    @Qualifier("pharmacyDSWebClient")
    private final WebClient dsClient;

    @Value("${pharmagator.data-providers.apteka-ds.category-fetch-url}")
    private String categoriesFetchUrl;

    @Value("${pharmagator.data-providers.apteka-ds.category-path}")
    private String categoryPath;

    @Value("${pharmagator.data-providers.apteka-ds.pharmacy-name}")
    private String pharmacyName;


    public Stream<MedicineDTO> loadData() {

        return this.fetchCategories().stream()
                .filter(categoryDto -> categoryDto.getName().equals("Медикаменти"))
                .map(CategoryDto::getChildren)
                .flatMap(Collection::stream)
                .map(CategoryDto::getSlug)
                .flatMap(this::fetchMedicineByCategory);

    }

    public List<CategoryDto> fetchCategories() {

        return this.dsClient.get().uri(categoriesFetchUrl)
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<CategoryDto>>() {
                }).block();

    }

    public Stream<MedicineDTO> fetchMedicineByCategory(String category) {

        FilterRequest filterRequest = FilterRequest.builder()
                .page(1L)
                .per(100L)
                .build();

        DSMedicinesResponse dsMedicinesResponse = getDSMedicineResponceByCategory(category,
                filterRequest.getPer(), filterRequest.getPage());

        Long totalPages;

        if (dsMedicinesResponse != null) {
            if ((dsMedicinesResponse.getTotal() % filterRequest.getPer()) == 0) {
                totalPages = dsMedicinesResponse.getTotal() / filterRequest.getPer();
            } else {
                totalPages = dsMedicinesResponse.getTotal() / filterRequest.getPer() + 1;
            }

            return LongStream.rangeClosed(1, totalPages)
                    .mapToObj(page -> getDSMedicineResponceByCategory(category, filterRequest.getPer(), page))
                    .map(DSMedicinesResponse::getProducts)
                    .flatMap(Collection::stream)
                    .map(this::mapToMedicineDTO);

        }

        return Stream.of();

    }

    private DSMedicinesResponse getDSMedicineResponceByCategory(String category, Long per, Long page) {

        return this.dsClient.post()
                .uri(categoryPath + "/" + category)
                .body(Mono.just(FilterRequest.builder()
                        .per(per)
                        .page(page)
                        .build()
                ), FilterRequest.class)
                .retrieve()
                .bodyToMono(DSMedicinesResponse.class)
                .block();
    }

    private MedicineDTO mapToMedicineDTO(DSMedicineDto dsMedicineDto) {

        return MedicineDTO.builder()
                .title(dsMedicineDto.getName())
                .externalId(dsMedicineDto.getId())
                .price(dsMedicineDto.getPrice())
                .pharmacyName(pharmacyName)
                .build();
    }


}
