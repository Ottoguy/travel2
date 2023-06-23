package com.agency.travel2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Travel2ApplicationTest {

	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FlightBookingRepository flightBookingRepo;

	private List<FlightBooking> flightBookings;

	@BeforeEach
	void setUp() {
		flightBookings = new ArrayList<>();
		var flight1 = new FlightBooking();
		var flight2 = new FlightBooking();

		flight1.setFlightNumber(325);
		flight1.setDestination("Stockholm");
		flight1.setOrigin("Prague");

		flight1.setFlightNumber(446);
		flight1.setDestination("Prague");
		flight1.setOrigin("Tbilisi");

		flightBookings.add(flight1);
		flightBookings.add(flight2);
	}

	@Test
	void testGetAllFlights() throws Exception {
		when(flightBookingRepo.findAll()).thenReturn(flightBookings);

		mockMvc.perform(get("/flightTable"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(flightBookings.size()))
				.andExpect(jsonPath("$[0].id").value(flightBookings.get(0).getId()))
				.andExpect(jsonPath("$[0].carId").value(flightBookings.get(0).getCarId()))
				.andExpect(jsonPath("$[1].id").value(flightBookings.get(1).getId()))
				.andExpect(jsonPath("$[1].carId").value(flightBookings.get(1).getCarId()));
	}

	@Test
	void testGetFlightBookingById() throws Exception {
		long id = 3259;
		var flight = new FlightBooking();
		flight.setCarId(id);
		flight.setOrigin("Stockholm");
		flight.setDestination("Prague");
		flightBookingRepo.save(flight);

		when(flightBookingRepo.findById(id)).thenReturn(java.util.Optional.of(flight));

		mockMvc.perform(get("/flightTable/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(flight.getId()))
				.andExpect(jsonPath("$.origin").value("Stockholm"))
				.andExpect(jsonPath("$.destination").value("Prague"));
	}

	@Test
	void testCreateFlightBooking() throws Exception {
		long id = 3259;
		FlightBooking flight = new FlightBooking();
		flight.setFlightNumber(38);
		flight.setId(341);
		flight.setCarId(id);
		flight.setOrigin("Stockholm");
		flight.setDestination("Prague");
		flightBookingRepo.save(flight);

		when(flightBookingRepo.save(flight)).thenReturn(flight);

		mockMvc.perform(post("/flightTable")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(flight)))
				.andExpect(status().isCreated());
	}


	@Test
	void testFindByCarId() throws Exception {
		long carId0 = 16131L;

		FlightBooking flight = new FlightBooking();
		flight.setCarId(carId0);
		flightBookingRepo.save(flight);

		List<FlightBooking> bookings = flightBookingRepo.findByCarId(carId0);

		when(flightBookingRepo.findById(carId0)).thenReturn(Optional.of(flight));

		validateFindByHotelIdOrFlightId(carId0, flight);

	}

	private void validateFindByHotelIdOrFlightId(long id, FlightBooking flightBooking) throws Exception {
		mockMvc.perform(get("/flightTable/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(flightBooking.getId()))
				.andExpect(jsonPath("$.hotelId").value(flightBooking.getHotelId()));
	}
}
