package com.agency.travel2;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Travel2ApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarBookingRepository carBookingRepository;

	private List<CarBooking> carBookings;

	@BeforeEach
	void setUp() {
		carBookings = new ArrayList<>();
		var car1 = new CarBooking();
		var car2 = new CarBooking();

		car1.setContent("content1");
		car1.setFrom("12:05:2023");
		car1.setTo("14:05:2023");

		car2.setContent("content2");
		car2.setFrom("01:05:2023");
		car2.setTo("04:05:2023");

		carBookings.add(car1);
		carBookings.add(car2);
	}

	@Test
	void testGetAllCarBookings() throws Exception {
		when(carBookingRepository.findAll()).thenReturn(carBookings);

		mockMvc.perform(get("/carBookings"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(carBookings.size()))
				.andExpect(jsonPath("$[0].id").value(carBookings.get(0).getId()))
				.andExpect(jsonPath("$[0].content").value(carBookings.get(0).getContent()))
				.andExpect(jsonPath("$[1].id").value(carBookings.get(1).getId()))
				.andExpect(jsonPath("$[1].content").value(carBookings.get(1).getContent()));
	}

	@Test
	void testGetCarBookingById() throws Exception {
		long id = 1L;
		var car = new CarBooking();
		car.setFrom("01:08:2023");
		car.setTo("15:08:2023");

		when(carBookingRepository.findById(id)).thenReturn(java.util.Optional.of(car));

		mockMvc.perform(get("/carBookings/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(car.getId()))
				.andExpect(jsonPath("$.content").value(car.getContent()));
	}

	@Test
	void testFindByFlightId() throws Exception {
		long flightId0 = 16131L;
		long flightId1 = 13151L;
		long flightId2 = 14563L;

		CarBooking carBooking0 = new CarBooking();
		carBooking0.setHotelId(flightId0);
		carBookingRepository.save(carBooking0);

		CarBooking carBooking1 = new CarBooking();
		carBooking1.setHotelId(flightId1);
		carBookingRepository.save(carBooking1);

		CarBooking carBooking2 = new CarBooking();
		carBooking2.setHotelId(flightId2);
		carBookingRepository.save(carBooking2);

		List<CarBooking> bookings = carBookingRepository.findByHotelId(flightId0);

		when(carBookingRepository.findById(flightId0)).thenReturn(Optional.of(carBooking0));
		when(carBookingRepository.findById(flightId1)).thenReturn(Optional.of(carBooking1));
		when(carBookingRepository.findById(flightId2)).thenReturn(Optional.of(carBooking2));

		validateFindByHotelIdOrFlightId(flightId0, carBooking0);
		validateFindByHotelIdOrFlightId(flightId1, carBooking1);
		validateFindByHotelIdOrFlightId(flightId2, carBooking2);
	}

	@Test
	void testFindByHotelId() throws Exception {
		long hotelId0 = 16131L;
		long hotelId1 = 13151L;
		long hotelId2 = 14563L;

		CarBooking carBooking0 = new CarBooking();
		carBooking0.setHotelId(hotelId0);
		carBookingRepository.save(carBooking0);

		CarBooking carBooking1 = new CarBooking();
		carBooking1.setHotelId(hotelId1);
		carBookingRepository.save(carBooking1);

		CarBooking carBooking2 = new CarBooking();
		carBooking2.setHotelId(hotelId2);
		carBookingRepository.save(carBooking2);

		List<CarBooking> bookings = carBookingRepository.findByHotelId(hotelId0);

		when(carBookingRepository.findById(hotelId0)).thenReturn(Optional.of(carBooking0));
		when(carBookingRepository.findById(hotelId1)).thenReturn(Optional.of(carBooking1));
		when(carBookingRepository.findById(hotelId2)).thenReturn(Optional.of(carBooking2));

		validateFindByHotelIdOrFlightId(hotelId0, carBooking0);
		validateFindByHotelIdOrFlightId(hotelId1, carBooking1);
		validateFindByHotelIdOrFlightId(hotelId2, carBooking2);
	}

	private void validateFindByHotelIdOrFlightId(long id, CarBooking carBooking) throws Exception {
		mockMvc.perform(get("/carBookings/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(carBooking.getId()))
				.andExpect(jsonPath("$.hotelId").value(carBooking.getHotelId()))
				.andExpect(jsonPath("$.content").value(carBooking.getContent()));
	}
}
