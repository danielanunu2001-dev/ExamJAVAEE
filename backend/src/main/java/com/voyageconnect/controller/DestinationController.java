package com.voyageconnect.controller;

import com.voyageconnect.model.Destination;
import com.voyageconnect.repository.DestinationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationRepository destinationRepository;

    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @GetMapping
    public List<Destination> search(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String country) {
        List<Destination> all = destinationRepository.findAll();
        return all.stream()
                .filter(d -> name == null || d.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(d -> country == null || d.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }
}
