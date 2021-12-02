package vb.javaCamp.pharmagator.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vb.javaCamp.pharmagator.DTOs.PriceDTO;
import vb.javaCamp.pharmagator.entities.Price;
import vb.javaCamp.pharmagator.entities.PriceId;
import vb.javaCamp.pharmagator.mappers.PriceMapper;
import vb.javaCamp.pharmagator.repositories.PriceRepository;
import vb.javaCamp.pharmagator.services.PriceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public List<PriceDTO> getAllPrices() {

        return priceRepository.findAll()
                .stream()
                .map(PriceMapper::entityToDto)
                .collect(Collectors.toList());

    }

    public PriceDTO getPrice(Long pid, Long mid) {

        PriceId priceId = new PriceId(pid, mid);
        return priceRepository.findById(priceId)
                .map(PriceMapper::entityToDto)
                .orElse(new PriceDTO());

    }

    public PriceDTO createPrice(PriceDTO priceDTO) {

        Price price = PriceMapper.DtoToEntity(priceDTO);
        Price created = priceRepository.save(price);
        PriceDTO dto = PriceMapper.entityToDto(created);
        return dto;

    }

    public PriceDTO updatePrice(PriceDTO priceDTO, Long pid, Long mid) {
        Price price = PriceMapper.DtoToEntity(priceDTO);
        price.setPharmacyId(pid);
        price.setMedicineId(mid);
        Price updated = priceRepository.save(price);
        PriceDTO dto = PriceMapper.entityToDto(updated);
        return dto;

    }

    public void deletePrice(Long pid, Long mid) {

        PriceId priceId = new PriceId(pid, mid);
        priceRepository.deleteById(priceId);

    }

}
