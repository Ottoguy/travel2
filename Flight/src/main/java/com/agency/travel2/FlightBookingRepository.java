package com.agency.travel2;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findByFlightnumber(int flightnumber);

    List<FlightBooking> findByOrigin(String origin);
}