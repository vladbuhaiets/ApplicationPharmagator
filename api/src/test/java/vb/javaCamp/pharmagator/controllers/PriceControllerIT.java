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
import vb.javaCamp.pharmagator.DTOs.PriceDTO;
import vb.javaCamp.pharmagator.entities.Price;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceControllerIT {

    private MockMvc mockMvc;
    private DatabaseDataSourceConnection dataSourceConnection;

    private final String URI = "/prices";

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
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "prices", "pharmacies", "medicines");
    }

    @Test
    void getAllPrices() throws Exception {

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.get(URI))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$[*].externalId",
                            Matchers.hasItems("20211112", "20211111")))
                    .andDo(print());
        } finally {
            this.dataSourceConnection.close();
        }

    }

    @Test
    void getPrice() throws Exception {

        Long pid = 20211112L;
        Long mid = 20211112L;

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.get(URI + "/pharmacyId/{pid}/medicineId/{mid}", pid, mid))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.externalId").value("20211112"))
                    .andDo(print());
        } finally {
            this.dataSourceConnection.close();
        }

    }

    @Test
    void createPrice() throws Exception {

        Long pid = 20211119L;
        Long mid = 20211119L;

        PriceDTO priceDTO = new PriceDTO(pid, mid, BigDecimal.valueOf(20211119L) ,"20211119");
        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.post(URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(priceDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.price").value("20211119"))
                    .andExpect(jsonPath("$.medicineId").value("20211119"))
                    .andDo(print());
        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    void updatePrice() throws Exception {

        Long pid = 20211112L;
        Long mid = 20211112L;

        PriceDTO priceDTO = new PriceDTO(pid, mid, BigDecimal.valueOf(20211112L) ,"20211113");

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.put(URI + "/pharmacyId/{pid}/medicineId/{mid}", pid, mid)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"price": "180.0", "externalId": "20211113"}
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.externalId").value("20211113"))
                    .andDo(print());
        } finally {
            dataSourceConnection.close();
        }

    }

    @Test
    void deletePrice() throws Exception {

        Long pid = 20211112L;
        Long mid = 20211112L;

        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/pharmacyId/{pid}/medicineId/{mid}", pid, mid))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } finally {
            dataSourceConnection.close();
        }

    }

    private IDataSet readDataSet() throws IOException, DataSetException {

        try (var resource = getClass()
                .getResourceAsStream("PriceControllerIT_dataset.xml")) {

            return new FlatXmlDataSetBuilder()
                    .build(resource);
        }

    }

}