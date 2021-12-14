package vb.javaCamp.pharmagator.config;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vb.javaCamp.pharmagator.DTOs.MedicineDTO;

@Configuration
public class CsvParserConfig {

    @Bean(name = "rowProcessor")
    public BeanListProcessor<MedicineDTO> rowProcessor() {
        return new BeanListProcessor<>(MedicineDTO.class);
    }

    @Bean
    public CsvParser csvParser(BeanListProcessor<MedicineDTO> beanListProcessor) {

        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setProcessor(beanListProcessor);

        return new CsvParser(settings);

    }

}
