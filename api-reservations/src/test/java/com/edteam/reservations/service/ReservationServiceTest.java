package com.edteam.reservations.service;

import com.edteam.reservations.connector.CatalogConnector;
import com.edteam.reservations.dto.ReservationDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

import static com.edteam.reservations.util.ReservationUtil.getReservation;
import static com.edteam.reservations.util.ReservationUtil.getReservationDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tags(@Tag("service"))
// @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Check the functionally of the service")
public class ReservationServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ReservationServiceTest.class);

    ReservationService reservationService;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    CatalogConnector catalogConnector;
    @Mock
    ConversionService conversionService;

    @BeforeEach
    void initialize_each_test() {
        log.info("Reservation properties initialized");
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationRepository, conversionService, catalogConnector);

    }

    @Tag("success-case")
    @DisplayName("Should return the information of the reservation")
    @Test
    void getReservation_should_return_reservation() {
        // Given
        Reservation reservationModel = getReservation(1L, "EZE", "MIA");
        when(reservationRepository.getReservationById(1L)).thenReturn(Optional.of(reservationModel));

        ReservationDTO reservationDTO = getReservationDTO(1L, "EZE", "MIA");
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);
        // When
        ReservationDTO result = reservationService.getReservationById(1L);
        // Then
        verify(reservationRepository, atMostOnce()).getReservationById(1L);
        verify(catalogConnector, never()).getCity(any());
        verify(conversionService, atMostOnce()).convert(reservationModel, ReservationDTO.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(getReservationDTO(1l, "EZE", "MIA"),
                        result));

    }

    @Tag("success-case")
    @DisplayName("Should delete the reservation")
    @Test
    void delete_should_remove_a_reservation() {
        // Given
        Reservation reservationModel = getReservation(1L, "EZE", "MIA");
        when(reservationRepository.getReservationById(1L)).thenReturn(Optional.of(reservationModel));

        ReservationDTO reservationDTO = getReservationDTO(1L, "EZE", "MIA");
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);
        doNothing().when(reservationRepository).delete(1L);
        // When
        reservationService.delete(1L);

        // Then
        verify(reservationRepository, atMostOnce()).delete(1L);
        verify(reservationRepository, atMostOnce()).getReservationById(1L);
        verify(catalogConnector, never()).getCity(any());
        verify(conversionService, atMostOnce()).convert(reservationModel, ReservationDTO.class);

    }

    @Tag("error-case")
    @DisplayName("Should return the information of the reservation")
    @Test
    void getReservation_should_not_return_reservation() {
        // Given
        when(reservationRepository.getReservationById(6L)).thenReturn(Optional.empty());

        // When
        EdteamException exception = assertThrows(EdteamException.class, () -> {
            reservationService.getReservationById(6L);
        });

        // Then
        assertAll(() -> assertNotNull(exception),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getMessage(), exception.getDescription()),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getHttpStatus(), exception.getStatus()));
    }

}
