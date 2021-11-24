package vb.javaCamp.pharmagator.dataProviders;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.dataProviders.dto.anc.ANCMedicineDto;
import vb.javaCamp.pharmagator.dataProviders.dto.anc.ANCMedicinesResponce;
import vb.javaCamp.pharmagator.dataProviders.dto.anc.ANCSubcategoryDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//@Service
@RequiredArgsConstructor
@Qualifier("pharmacyANCDataProvider")
public class PharmacyANCDataProvider implements DataProvider {

    @Qualifier("pharmacyANCWebClient")
    private final WebClient ancClient;

    @Value("${pharmagator.data-providers.anc.category-fetch-url}")
    private String categoriesFetchUrl;

    @Value("${pharmagator.data-providers.anc.pharmacy-name}")
    private String pharmacyName;

    @Value("${pharmagator.data-providers.anc.page-size}")
    private Long pageSize;

    public Stream<MedicineDTO> loadData() {
        return this.fetchCategories().stream()
                .map(ANCSubcategoryDTO::getSubcategories)
                .flatMap(Collection::stream)
                .map(ANCSubcategoryDTO::getLink)
                .flatMap(this::fetchMedicinesByLink);

    }

    public List<ANCSubcategoryDTO> fetchCategories() {

        return ancClient.get().uri(categoriesFetchUrl + "/medikamenty-1")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ANCSubcategoryDTO>() {
                })
                .map(ANCSubcategoryDTO::getSubcategories)
                .block();

    }

    public Stream<MedicineDTO> fetchMedicinesByLink(String link) {

        ANCMedicinesResponce responceForTotal = getMedicineResponceByLinkAndPage(link, 0L);
        Long totalPages;

        if (responceForTotal != null) {
            if ((responceForTotal.getTotal() % 100) == 0) {
                totalPages = responceForTotal.getTotal() / 100;
            } else {
                totalPages = responceForTotal.getTotal() / 100 + 1;
            }


            return LongStream.range(0, totalPages)
                    .mapToObj(page -> getMedicineResponceByLinkAndPage(link, page))
                    .map(ANCMedicinesResponce::getProducts)
                    .flatMap(Collection::stream)
                    .map(this::mapToMedicineDTO);
        }

        return Stream.of();

    }

    private ANCMedicinesResponce getMedicineResponceByLinkAndPage(String link, Long page) {
        return this.ancClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(categoriesFetchUrl + "/" + link)
                        .queryParam("p", page)
                        .queryParam("s", pageSize)
                        .build()
                ).retrieve()
                .bodyToMono(ANCMedicinesResponce.class)
                .block();
    }

    private MedicineDTO mapToMedicineDTO(ANCMedicineDto ancMedicineDto) {

        return MedicineDTO.builder()
                .title(ancMedicineDto.getName())
                .externalId(ancMedicineDto.getId())
                .price(ancMedicineDto.getPrice())
                .pharmacyName(pharmacyName)
                .build();

    }

}

