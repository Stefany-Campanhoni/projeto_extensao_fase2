package com.stefanycampanhoni.projeto_extensao_fase2.city;

import com.stefanycampanhoni.projeto_extensao_fase2.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public City save(City city) {
        return cityRepository.save(city);
    }

    public City findById(Integer id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found"));
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public void delete(Integer id) {
        cityRepository.deleteById(id);
    }
}
