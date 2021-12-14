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
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MedicineControllerIT {

    private MockMvc mockMvc;
    private DatabaseDataSourceConnection dataSourceConnection;

    private final String URI = "/medicines";

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
        JdbcTestUtils.deleteFromTables(jdbcTemplate,  "medicines");
    }

    @Test
    void getAllMedicines() throws Exception {

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.get(URI))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].title",
                            Matchers.hasItems("MedicineControllerIT_name2","MedicineControllerIT_name1")))
                    .andDo(print());
        } finally {
            this.dataSourceConnection.close();
        }

    }

    @Test
    void getMedicine() throws Exception {

        Long id = 20211112L;

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("MedicineControllerIT_name2"))
                    .andDo(print());
        } finally {
            this.dataSourceConnection.close();
        }

    }

    @Test
    void createMedicine() throws Exception {

        MedicineDTO medicineDTO = new MedicineDTO(2021111203L,"MedicineControllerIT_name3", BigDecimal.valueOf(2021111203), "2021111203", "2021111203" );

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.post(URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(medicineDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.title").value("MedicineControllerIT_name3"))
                    .andDo(print());
        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    void updateMedicine() throws Exception {

        Long id = 20211112L;

        MedicineDTO medicineDTO = new MedicineDTO(20211112L,"NewTitle", BigDecimal.valueOf(20211112L), "20211112L", "20211112L" );

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.put(URI + "/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(medicineDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.title").value("NewTitle"))
                    .andDo(print());
        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    void deleteMedicine() throws Exception {

        Long id = 20211114L;

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/{id}", id))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } finally {
            dataSourceConnection.close();
        }

    }

    private IDataSet readDataSet() throws IOException, DataSetException {

        try (var resource = getClass()
                .getResourceAsStream("MedicineControllerIT_dataset.xml")) {

            return new FlatXmlDataSetBuilder()
                    .build(resource);
        }

    }

}