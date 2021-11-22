package vb.javaCamp.pharmagator.dataProviders.impls;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;
import vb.javaCamp.pharmagator.dataProviders.DataProvider;

import java.math.BigDecimal;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
public class DataProviderImpl implements DataProvider {

    public Stream<MedicineDTO> loadData() {

        return IntStream.rangeClosed(1, 100)
            .mapToObj(integer -> MedicineDTO.builder()
                    .title(integer + "")
                    .externalId("external id: " + integer)
                    .price(BigDecimal.valueOf(integer))
                    .pharmacyName("test")
                .build());

    }

}
