package vb.javaCamp.pharmagator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vb.javaCamp.pharmagator.DTOs.PharmacyDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PharmacyControllerIT {

    private MockMvc mockMvc;
    private DatabaseDataSourceConnection dataSourceConnection;

    private final String URI = "/pharmacies";

    @Autowired
    public void setComponents(final MockMvc mockMvc,
                              final DataSource dataSource) throws SQLException {
        this.mockMvc = mockMvc;
        this.dataSourceConnection = new DatabaseDataSourceConnection(dataSource);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,  "pharmacies");
    }

    @Test
    public void findAllPharmacies_ok() throws Exception {

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.get(URI))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$[*].name", Matchers.hasItems("PharmacyControllerIT_name1", "PharmacyControllerIT_name2")));

        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    public void findByIdPharmacy_ok() throws Exception {

        Long id = 20211112L;

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", id))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.name").value("PharmacyControllerIT_name2"))
                    .andExpect(jsonPath("$.medicineLinkTemplate").value("PharmacyControllerIT_link2"))
                    .andDo(print());
        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    public void deletePharmacy_ok() throws Exception {

        Long id = 20211114L;

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/{id}", id))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    public void createPharmacy_ok() throws Exception {

        PharmacyDTO pharmacyDTO = new PharmacyDTO("PharmacyControllerIT_name3", "PharmacyControllerIT_link3");

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.post(URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(pharmacyDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.name").value("PharmacyControllerIT_name3"))
                    .andExpect(jsonPath("$.medicineLinkTemplate").value("PharmacyControllerIT_link3"))
                    .andDo(print());
        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    public void updatePharmacy_ok() throws Exception {

        Long id = 20211112L;

        PharmacyDTO newDTO = new PharmacyDTO();
        newDTO.setName("NewName");

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.put(URI + "/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(newDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.name").value("NewName"))
                    .andDo(print());
        } finally {
            this.dataSourceConnection.close();
        }

    }

    private IDataSet readDataSet() throws IOException, DataSetException {

        try (var resource = getClass()
                .getResourceAsStream("PharmacyControllerIT_dataset.xml")) {

            return new FlatXmlDataSetBuilder()
                    .build(resource);
        }

    }

}
