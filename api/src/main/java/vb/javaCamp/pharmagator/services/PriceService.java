package vb.javaCamp.pharmagator.services;

import vb.javaCamp.pharmagator.DTOs.PriceDTO;

import java.util.List;

public interface PriceService {

    List<PriceDTO> getAllPrices();

    PriceDTO getPrice(Long pid, Long mid);

    PriceDTO createPrice(PriceDTO priceDTO);

    PriceDTO updatePrice(PriceDTO priceDTO, Long pid, Long mid);

    void deletePrice(Long pid, Long mid);
}
