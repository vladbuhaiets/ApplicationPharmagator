package vb.javaCamp.pharmagator.dataProviders.dto.anc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ANCSubcategoryDTO {

    private String name;

    private String link;

    private List<ANCSubcategoryDTO> subcategories;

}
