package com.agency.travel2;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {
    List<CarBooking> findById(int id);

    List<CarBooking> findByBrand(String brand);

    List<CarBooking> findByFlightId(long flightId);

    List<CarBooking> findByHotelId(long hotelId);
}