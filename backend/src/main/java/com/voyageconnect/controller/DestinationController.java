package com.voyageconnect.controller;

import com.voyageconnect.model.Destination;
import com.voyageconnect.service.DestinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@Tag(name = "Destinations", description = "Endpoints for browsing destinations")
@SecurityRequirement(name = "bearerAuth")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @Operation(summary = "Get all destinations")
    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.findAll();
    }
}
