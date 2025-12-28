package com.voyageconnect.controller;

import com.voyageconnect.model.Destination;
import com.voyageconnect.model.User;
import com.voyageconnect.model.UserRole;
import com.voyageconnect.repository.DestinationRepository;
import com.voyageconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;
    private final com.voyageconnect.repository.BookingRepository bookingRepository;

    public AdminController(DestinationRepository destinationRepository, UserRepository userRepository, com.voyageconnect.repository.BookingRepository bookingRepository) {
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    // Destinations management
    @GetMapping("/destinations")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Destination> listDestinations() {
        return destinationRepository.findAll();
    }

    @PostMapping("/destinations")
    @PreAuthorize("hasRole('ADMIN')")
    public Destination createDestination(@RequestBody Destination dto) {
        if (!dto.isActive()) dto.setActive(true);
        return destinationRepository.save(dto);
    }

    @PutMapping("/destinations/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDestination(@PathVariable Long id, @RequestBody Destination body) {
        Optional<Destination> opt = destinationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Destination d = opt.get();
        d.setName(body.getName());
        d.setDescription(body.getDescription());
        d.setCountry(body.getCountry());
        d.setPrice(body.getPrice());
        d.setActive(body.isActive());
        destinationRepository.save(d);
        return ResponseEntity.ok(d);
    }

    @DeleteMapping("/destinations/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDestination(@PathVariable Long id) {
        Optional<com.voyageconnect.model.Destination> opt = destinationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        com.voyageconnect.model.Destination d = opt.get();
        d.setDeleted(true);
        destinationRepository.save(d);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/destinations/{id}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> suspendDestination(@PathVariable Long id, @RequestParam boolean suspend) {
        Optional<Destination> opt = destinationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Destination d = opt.get();
        d.setActive(!suspend);
        destinationRepository.save(d);
        return ResponseEntity.ok(d);
    }

    @PatchMapping("/destinations/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> restoreDestination(@PathVariable Long id) {
        Optional<Destination> opt = destinationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Destination d = opt.get();
        d.setDeleted(false);
        destinationRepository.save(d);
        return ResponseEntity.ok(d);
    }

    @DeleteMapping("/destinations/{id}/purge")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> purgeDestination(@PathVariable Long id, @RequestParam(required = false, defaultValue = "false") boolean force) {
        Optional<Destination> opt = destinationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        long refs = bookingRepository.countByDestinationId(id);
        if (refs > 0 && !force) {
            return ResponseEntity.status(409).body("Destination has bookings; set force=true to delete and remove related bookings");
        }
        if (refs > 0 && force) {
            bookingRepository.deleteByDestinationId(id);
        }
        destinationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Users management
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id, @RequestParam UserRole role) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        User u = opt.get();
        u.setRole(role);
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }

    @PatchMapping("/users/{id}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> suspendUser(@PathVariable Long id, @RequestParam boolean suspend) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        User u = opt.get();
        // simple suspend: set password to a random value or mark disabled — here we null password as a simple approach
        if (suspend) {
            u.setPassword("DISABLED");
        } else {
            // cannot recover original password here — admin should reset via separate flow
            u.setPassword("RESET_REQUIRED");
        }
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }
}
