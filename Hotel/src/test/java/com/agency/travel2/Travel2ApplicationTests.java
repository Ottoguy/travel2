package com.agency.travel2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
	private HotelBookingRepository hotelBookingRepository;

	private List<HotelBooking> hotelBookings;

	@BeforeEach
	void setUp() {
		hotelBookings = new ArrayList<>();
		var hotelBooking1 = new HotelBooking();
		var hotelBooking2 = new HotelBooking();

		hotelBooking1.setFlightId(325);
		hotelBooking1.setBeds(3);
		hotelBooking1.setFloor(4);

		hotelBooking2.setFlightId(446);
		hotelBooking2.setBeds(7);
		hotelBooking2.setFloor(5);

		hotelBookings.add(hotelBooking1);
		hotelBookings.add(hotelBooking2);
	}

	@Test
	void testGetAllHotelBookings() throws Exception {
		when(hotelBookingRepository.findAll()).thenReturn(hotelBookings);

		mockMvc.perform(get("/hotelBookings"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(hotelBookings.size()))
				.andExpect(jsonPath("$[0].id").value(hotelBookings.get(0).getId()))
				.andExpect(jsonPath("$[0].beds").value(hotelBookings.get(0).getBeds()))
				.andExpect(jsonPath("$[0].floor").value(hotelBookings.get(0).getFloor()))

				.andExpect(jsonPath("$[1].id").value(hotelBookings.get(1).getId()))
				.andExpect(jsonPath("$[1].beds").value(hotelBookings.get(1).getBeds()))
				.andExpect(jsonPath("$[1].floor").value(hotelBookings.get(1).getFloor()));
	}

	@Test
	void testGetHotelbookingById() throws Exception {
		long id = 3259;
		var hotelBooking = new HotelBooking();
		hotelBooking.setCarId(id);
		hotelBooking.setFloor(3);
		hotelBooking.setBeds(8);
		hotelBooking.setFlightId(258);
		hotelBookingRepository.save(hotelBooking);

		when(hotelBookingRepository.findById(id)).thenReturn(java.util.Optional.of(hotelBooking));

		mockMvc.perform(get("/hotelBookings/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(hotelBooking.getId()))
				.andExpect(jsonPath("$.beds").value(8))
				.andExpect(jsonPath("$.flightId").value(258));
	}

	@Test
	void testCreateHotelBooking() throws Exception {
		long id = 3259;
		var hotelBooking = new HotelBooking();
		hotelBooking.setCarId(id);
		hotelBooking.setFloor(3);
		hotelBooking.setBeds(8);
		hotelBooking.setFlightId(258);
		hotelBookingRepository.save(hotelBooking);

		when(hotelBookingRepository.save(hotelBooking)).thenReturn(hotelBooking);

		mockMvc.perform(post("/hotelBookings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(hotelBooking)))
				.andExpect(status().isCreated());
	}


	@Test
	void testFindByCarId() throws Exception {
		long carId0 = 4534;

		HotelBooking hotel = new HotelBooking();
		hotel.setCarId(carId0);
		hotelBookingRepository.save(hotel);

		List<HotelBooking> bookings = hotelBookingRepository.findByCarId(carId0);

		when(hotelBookingRepository.findById(carId0)).thenReturn(Optional.of(hotel));
		validateFindByCarId(carId0, hotel);
	}

	private void validateFindByCarId(long id, HotelBooking hotelBooking) throws Exception {
		mockMvc.perform(get("/hotelBookings/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(hotelBooking.getId()))
				.andExpect(jsonPath("$.carId").value(hotelBooking.getCarId()));
	}
}
