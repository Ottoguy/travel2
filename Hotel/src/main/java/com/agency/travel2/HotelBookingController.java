package com.agency.travel2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8082")
@Controller
public class HotelBookingController {

    @Autowired
    HotelBookingRepository hotelBookingRepository;
    @Autowired
    private final RestTemplate restTemplate;
    public HotelBookingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping("/summary/{id}")
    @ResponseBody
    public ResponseEntity<String> summary(@PathVariable("id") long id) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);
        if (hotelBookingData.isPresent() ) {
            String flight_info;
            String car_info;
            String hotel_info = getHotelBookingInfo(id).getBody();

            try{
                String flight_url = "http://localhost:8081/flightTable/info/" + hotelBookingData.get().getFlightId();
                ResponseEntity<String> flight_response = restTemplate.getForEntity(flight_url, String.class);
                flight_info = flight_response.getBody();


            } catch (Exception e) {
                flight_info = "No flight booking with that id\n";
            }

            try{
                String car_url = "http://localhost:8080/carTable/info/" + hotelBookingData.get().getCarId();
                ResponseEntity<String> car_response = restTemplate.getForEntity(car_url, String.class);
                car_info = car_response.getBody();


            } catch (Exception e) {
                car_info = "No car booking with that id\n";
            }

            return new ResponseEntity<>( hotel_info + flight_info + car_info, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/hotelbooking")
    public String hotelBookingForm(Model model) {
        model.addAttribute("hotelbooking", new HotelBooking());
        return "hotelbooking";
    }

    @PostMapping("/hotelbooking")
    public String hotelBookingSubmit(@ModelAttribute HotelBooking hotelbooking, Model model) {
        model.addAttribute("hotelbooking", hotelbooking);
        createHotelBooking(hotelbooking);
        String car_url = "http://localhost:8080/carTable/" + hotelbooking.getCarId() + "/hotelId/" + hotelbooking.getId();
        restTemplate.exchange(car_url, HttpMethod.PUT, null, String.class);
        String hotel_url = "http://localhost:8081/flightTable/" + hotelbooking.getFlightId() + "/hotelId/" + hotelbooking.getId();
        restTemplate.exchange(hotel_url, HttpMethod.PUT, null, String.class);
        return "result";
    }

    @GetMapping("/hotelTable")
    @ResponseBody
    public ResponseEntity<List<HotelBooking>> getAllHotelBookings() {
        try {
            List<HotelBooking> hotelTable = new ArrayList<HotelBooking>();

            hotelBookingRepository.findAll().forEach(hotelTable::add);

            if (hotelTable.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(hotelTable, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hotelTable/{id}")
    @ResponseBody
    public ResponseEntity<HotelBooking> getHotelBookingById(@PathVariable("id") long id) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        if (hotelBookingData.isPresent()) {
            return new ResponseEntity<>(hotelBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/hotelTable")
    @ResponseBody
    public ResponseEntity<HotelBooking> createHotelBooking(@RequestBody HotelBooking hotelBooking) {
        try {
            HotelBooking _hotelBooking = hotelBookingRepository
                    .save(hotelBooking);
            return new ResponseEntity<>(_hotelBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hotelTable/{id}")
    @ResponseBody
    public ResponseEntity<HotelBooking> updateHotelBooking(@PathVariable("id") long id, @RequestBody HotelBooking hotelBooking) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        if (hotelBookingData.isPresent()) {
            HotelBooking _hotelBooking = hotelBookingData.get();
            _hotelBooking.setBeds(hotelBooking.getBeds());
            _hotelBooking.setFloor(hotelBooking.getFloor());
            _hotelBooking.setCarId(hotelBooking.getCarId());
            _hotelBooking.setFlightId(hotelBooking.getFlightId());
            return new ResponseEntity<>(hotelBookingRepository.save(_hotelBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/hotelTable/{id}/carId/{carId}")
    @ResponseBody
    public ResponseEntity<HotelBooking> updateCarId(@PathVariable("id") long id, @PathVariable("carId") long carId) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        if (hotelBookingData.isPresent()) {
            HotelBooking hotelBooking = hotelBookingData.get();
            hotelBooking.setCarId(carId);

            return new ResponseEntity<>(hotelBookingRepository.save(hotelBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @PutMapping("/hotelTable/{id}/flightId/{flightId}")
    @ResponseBody
    public ResponseEntity<HotelBooking> updateFlightId(@PathVariable("id") long id, @PathVariable("flightId") long flightId) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        if (hotelBookingData.isPresent()) {
            HotelBooking hotelBooking = hotelBookingData.get();
            hotelBooking.setFlightId(flightId);

            return new ResponseEntity<>(hotelBookingRepository.save(hotelBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/hotelTable/info/{id}")
    @ResponseBody
    public ResponseEntity<String> getHotelBookingInfo(@PathVariable("id") long id) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        if (hotelBookingData.isPresent()) {

            String beds = String.valueOf(hotelBookingData.get().getBeds());
            String floor = String.valueOf(hotelBookingData.get().getFloor());

            return new ResponseEntity<>("Beds: " + beds + "\n" + "Floor: " + floor + "\n", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No hotel booking with that id", HttpStatus.OK);
        }
    }

    @DeleteMapping("/hotelTable/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteHotelBooking(@PathVariable("id") long id) {
        try {
            hotelBookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hotelTable")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteAllHotelBookings() {
        try {
            hotelBookingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}