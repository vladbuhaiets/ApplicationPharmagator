package vb.javaCamp.pharmagator.dataProviders.dto.ds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String name;
    private String slug;
    private List<CategoryDto> children;

}
