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

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class CarBookingController {

    @Autowired
    CarBookingRepository carBookingRepository;
    private final RestTemplate restTemplate;
    public CarBookingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/carbooking")
    public String carBookingForm(Model model) {
        model.addAttribute("carbooking", new CarBooking());
        return "carbooking";
    }

    @PostMapping("/carbooking")
    public String carBookingSubmit(@ModelAttribute CarBooking carbooking, Model model) {
        model.addAttribute("carbooking", carbooking);
        createCarBooking(carbooking);
        String flight_url = "http://host.docker.internal:8081/flightTable/" + carbooking.getFlightId() + "/carId/" + carbooking.getId();
        restTemplate.exchange(flight_url, HttpMethod.PUT, null, String.class);
        String hotel_url = "http://host.docker.internal:8082/hotelTable/" + carbooking.getHotelId() + "/carId/" + carbooking.getId();
        restTemplate.exchange(hotel_url, HttpMethod.PUT, null, String.class);
        return "result";
    }

    @GetMapping("/carTable")
    @ResponseBody
    public ResponseEntity<List<CarBooking>> getAllCarBookings() {
        try {
            List<CarBooking> carTable = new ArrayList<CarBooking>();

            carBookingRepository.findAll().forEach(carTable::add);

            if (carTable.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(carTable, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/carTable/{id}/flightId/{flightId}")
    @ResponseBody
    public ResponseEntity<CarBooking> updateFlightId(@PathVariable("id") long id, @PathVariable("flightId") long flightId) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);

        if (carBookingData.isPresent()) {
            CarBooking carBooking = carBookingData.get();
            carBooking.setFlightId(flightId);

            return new ResponseEntity<>(carBookingRepository.save(carBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/carTable/{id}/hotelId/{hotelId}")
    @ResponseBody
    public ResponseEntity<CarBooking> updateHotelId(@PathVariable("id") long id, @PathVariable("hotelId") long hotelId) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);

        if (carBookingData.isPresent()) {
            CarBooking carBooking = carBookingData.get();
            carBooking.setHotelId(hotelId);

            return new ResponseEntity<>(carBookingRepository.save(carBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/carTable/{id}")
    @ResponseBody
    public ResponseEntity<CarBooking> getCarBookingById(@PathVariable("id") long id) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);

        if (carBookingData.isPresent()) {
            return new ResponseEntity<>(carBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/summary/{id}")
    @ResponseBody
    public ResponseEntity<String> summary(@PathVariable("id") long id) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);
        if (carBookingData.isPresent() ) {
            String flight_info;
            String hotel_info;
            String car_info = getCarBookingInfo(id).getBody();

            try{
                String flight_url = "http://localhost:8081/flightTable/info/" + carBookingData.get().getFlightId();
                ResponseEntity<String> flight_response = restTemplate.getForEntity(flight_url, String.class);
                flight_info = flight_response.getBody();


            } catch (Exception e) {
                flight_info = "No flight booking with that id\n";
            }

            try{
                String hotel_url = "http://localhost:8082/hotelTable/info/" + carBookingData.get().getHotelId();
                ResponseEntity<String> hotel_response = restTemplate.getForEntity(hotel_url, String.class);
                hotel_info = hotel_response.getBody();


            } catch (Exception e) {
                hotel_info = "No hotel booking with that id\n";
            }

            return new ResponseEntity<>( hotel_info + flight_info + car_info, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/carTable/info/{id}")
    @ResponseBody
    public ResponseEntity<String> getCarBookingInfo(@PathVariable("id") long id) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);

        if (carBookingData.isPresent()) {

            String from = carBookingData.get().getFrom();
            String to = carBookingData.get().getTo();

            return new ResponseEntity<>("From: " + from + "\n" + "To: " + to + "\n", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No car booking with that id", HttpStatus.OK);
        }
    }


    @GetMapping("/getQuery")
    @ResponseBody
    public ResponseEntity<String> getQuery() {
        return new ResponseEntity<>("hej", HttpStatus.OK);
    }




    @PostMapping("/carTable")
    @ResponseBody
    public ResponseEntity<CarBooking> createCarBooking(@RequestBody CarBooking carBooking) {
        try {
            CarBooking _carBooking = carBookingRepository
                    //.save(new CarBooking(carBooking.getContent(), carBooking.getBrand(), carBooking.getFrom(), carBooking.getTo()));
                    .save(carBooking);
            return new ResponseEntity<>(_carBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/carTable/{id}")
    @ResponseBody
    public ResponseEntity<CarBooking> updateCarBooking(@PathVariable("id") long id, @RequestBody CarBooking carBooking) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);

        if (carBookingData.isPresent()) {
            CarBooking _carBooking = carBookingData.get();
            _carBooking.setContent(carBooking.getContent());
            _carBooking.setBrand(carBooking.getBrand());
            _carBooking.setFrom(carBooking.getFrom());
            _carBooking.setTo(carBooking.getTo());
            _carBooking.setFlightId(carBooking.getFlightId());
            _carBooking.setHotelId(carBooking.getHotelId());
            return new ResponseEntity<>(carBookingRepository.save(_carBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/carTable/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteCarBooking(@PathVariable("id") long id) {
        try {
            carBookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/carTable")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteAllCarBookings() {
        try {
            carBookingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}