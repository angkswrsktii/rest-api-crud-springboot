package com.example.restapi.domain.services;

import com.example.restapi.domain.ports.HewanPort;
import com.example.restapi.domain.validator.AuthorizationValidator;
import com.example.restapi.infrastructure.entity.Hewan;
import com.example.restapi.infrastructure.repository.HewanRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HewanServices extends AuthorizationService implements HewanPort {
    private final HewanRepository hewanRepository;
    public HewanServices(AuthorizationValidator authorizationValidator,
                         HewanRepository hewanRepository) {
        super(authorizationValidator);
        this.hewanRepository = hewanRepository;
    }
    @Override
    public Hewan save(Hewan hewan) {
        return hewanRepository.save(hewan);
    }
    @Override
    public List<Hewan> findByHewanId(String hewanId) {return hewanRepository.findByHewanId(hewanId);}
    @Override
    public List<Hewan> findAllHewan() {
        return hewanRepository.findAllHewan();
    }
    @Override
    public void deleteByHewanId(String hewanId) {
        hewanRepository.deleteByHewanId(hewanId);
    }
}
