package com.voyageconnect.service;

import com.voyageconnect.model.Destination;
import com.voyageconnect.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }
}
