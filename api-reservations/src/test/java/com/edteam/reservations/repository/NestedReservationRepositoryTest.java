package com.edteam.reservations.repository;

import com.edteam.reservations.model.Reservation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.edteam.reservations.util.ReservationUtil.getReservation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

//La siguiente anotación no es valida al momento de realizar nested test
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags(@Tag("repository"))
// @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Check the functionally of the repository")
class NestedReservationRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(NestedReservationRepositoryTest.class);

    // Given
    ReservationRepository reservationRepository;

    @BeforeEach
    void initialize_each_test() {
        log.info("Reservation Repository initialized");
        reservationRepository = new ReservationRepository();

        assertEquals(1, reservationRepository.getReservations().size());
    }

    @AfterEach
    void destroy_each_test() {
        log.info("Reservation Repository destroyed");
    }

    @BeforeAll
    static void initialize_all_test() {
        log.info("Reservation Repository initialized the context on all tests");
    }

    @AfterAll
    static void destroy_all_test() {
        log.info("Reservation Repository destroyed");
    }

    @Nested
    class GetReservations {
        @Tag("success-case")
        @DisplayName("Should return the information of all the reservation")
        @Test
        void getReservations_should_return_reservation() {

            reservationRepository.save(getReservation(null, "EZE", "MIA"));
            // When
            List<Reservation> result = reservationRepository.getReservations();

            // Then
            assertAll(() -> assertNotNull(result), () -> assertThat(result, hasSize(2)),
                    () -> assertThat(getReservation(1L, "EZE", "MIA"), in(result)),
                    () -> assertThat(result.get(0), hasProperty("id")));
        }

        //@ResourceLock("reservation")
        @Tag("success-case")
        @DisplayName("Should return the information of the reservation")
        @Test
        void getReservation_should_return_reservation() {

            // When
            Optional<Reservation> result = reservationRepository.getReservationById(1L);

            // Then
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                    () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));

        }

        @Disabled
        @Tag("error-case")
        @DisplayName("Should not return the information of the reservation by timeout")
        @Test
        @Timeout(value = 1L, unit = TimeUnit.MILLISECONDS)
        void getReservation_should_not_return_reservation_by_timeout() {

            // When
            Optional<Reservation> result = reservationRepository.getReservationById(1L);

            // Then
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                    () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));

        }

        @Tag("error-case")
        @DisplayName("Should return the information of the reservation")
        @Test
        void getReservation_should_not_return_reservation() {

            // When
            Optional<Reservation> result = reservationRepository.getReservationById(6L);

            // Then
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isEmpty()));
        }
    }

    @Nested
    class SaveReservation {
        @Tag("success-case")
        @DisplayName("Should return the information of saved the reservation")
        @ParameterizedTest
        @CsvSource({ "EZE,MIA" })
        // @CsvFileSource(resources = "/data/origin_destination.csv")
        void save_should_return_reservation(String origin, String destination) {
            // when
            Reservation result = reservationRepository.save(getReservation(null, origin, destination));

            // Then
            assertAll(() -> assertNotNull(result),
                    () -> assertEquals(origin, result.getItinerary().getSegment().get(0).getOrigin()),
                    () -> assertEquals(destination, result.getItinerary().getSegment().get(0).getDestination()));
        }
    }

}
