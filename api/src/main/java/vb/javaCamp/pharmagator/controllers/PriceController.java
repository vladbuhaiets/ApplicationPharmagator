package vb.javaCamp.pharmagator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vb.javaCamp.pharmagator.DTOs.PriceDTO;
import vb.javaCamp.pharmagator.services.PriceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public List<PriceDTO> getAllPrices() {

        return priceService.getAllPrices();

    }

    @GetMapping("/pharmacyId/{pid}/medicineId/{mid}")
    public PriceDTO getPrice(@PathVariable("pid") Long pid, @PathVariable("mid") Long mid) {

        return priceService.getPrice(pid,mid);

    }

    @PostMapping
    public PriceDTO createPrice(@RequestBody PriceDTO priceDTO) {

        return priceService.createPrice(priceDTO);

    }

    @PutMapping("/pharmacyId/{pid}/medicineId/{mid}")
    public PriceDTO updatePrice(@RequestBody PriceDTO priceDTO, @PathVariable("pid") Long pid, @PathVariable("mid") Long mid) {

        return priceService.updatePrice(priceDTO, pid, mid);

    }

    @DeleteMapping("/pharmacyId/{pid}/medicineId/{mid}")
    public void deletePrice(@PathVariable("pid") Long pid, @PathVariable("mid") Long mid) {

        priceService.deletePrice(pid,mid);

    }

}
