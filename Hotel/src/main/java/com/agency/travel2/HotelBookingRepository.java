package com.agency.travel2;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    List<HotelBooking> findByCarId(long carId);

    List<HotelBooking> findByFlightId(long flightId);
}