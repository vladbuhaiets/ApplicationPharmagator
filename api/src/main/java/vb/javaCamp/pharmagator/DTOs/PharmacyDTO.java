package vb.javaCamp.pharmagator.DTOs;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PharmacyDTO {

    private Long Id;

    private String name;

    private String medicineLinkTemplate;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicineLinkTemplate() {
        return medicineLinkTemplate;
    }

    public void setMedicineLinkTemplate(String medicineLinkTemplate) {
        this.medicineLinkTemplate = medicineLinkTemplate;
    }

}
