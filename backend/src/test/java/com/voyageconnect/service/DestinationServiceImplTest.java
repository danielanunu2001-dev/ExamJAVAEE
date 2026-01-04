package com.voyageconnect.service;

import com.voyageconnect.model.Destination;
import com.voyageconnect.repository.DestinationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DestinationServiceImplTest {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private DestinationServiceImpl destinationService;

    @Test
    void findAll_shouldReturnAllDestinations() {
        when(destinationRepository.findAll()).thenReturn(List.of(new Destination(), new Destination()));

        List<Destination> result = destinationService.findAll();

        assertEquals(2, result.size());
        verify(destinationRepository, times(1)).findAll();
    }
}
