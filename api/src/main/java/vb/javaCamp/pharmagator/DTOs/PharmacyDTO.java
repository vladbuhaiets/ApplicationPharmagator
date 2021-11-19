package vb.javaCamp.pharmagator.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PharmacyDTO {

    private Long Id;

    private String name;

    private String medicineLinkTemplate;

}
