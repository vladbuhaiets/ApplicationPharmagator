package vb.javaCamp.pharmagator.dataProviders.dto.anc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ANCMedicinesResponce {

    private Long total;

    private List<ANCMedicineDto> products;

}
