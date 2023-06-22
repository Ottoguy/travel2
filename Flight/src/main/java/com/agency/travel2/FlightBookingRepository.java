package com.agency.travel2;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findByFlightNumber(int flightNumber);

    List<FlightBooking> findByOrigin(String origin);

    List<FlightBooking> findByCarId(long carId);

    List<FlightBooking> findByHotelId(long hotelId);
}