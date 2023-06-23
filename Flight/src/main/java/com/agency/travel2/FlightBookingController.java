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

@CrossOrigin(origins = "http://localhost:8081")
@Controller
public class FlightBookingController {

    @Autowired
    FlightBookingRepository flightBookingRepository;
    @Autowired
    private final RestTemplate restTemplate;
    public FlightBookingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/summary/{id}")
    @ResponseBody
    public ResponseEntity<String> summary(@PathVariable("id") long id) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);
        if (flightBookingData.isPresent() ) {
            String hotel_info;
            String car_info;
            String flight_info = getFlightBookingInfo(id).getBody();

            try{
                String hotel_url = "http://localhost:8082/hotelTable/info/" + flightBookingData.get().getHotelId();
                ResponseEntity<String> hotel_response = restTemplate.getForEntity(hotel_url, String.class);
                hotel_info = hotel_response.getBody();


            } catch (Exception e) {
                hotel_info = "No hotel booking with that id\n";
            }

            try{
                String car_url = "http://localhost:8080/carTable/info/" + flightBookingData.get().getCarId();
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


    @GetMapping("/flightbooking")
    public String flightBookingForm(Model model) {
        model.addAttribute("flightbooking", new FlightBooking());
        return "flightbooking";
    }

    @PostMapping("/flightbooking")
    public String flightBookingSubmit(@ModelAttribute FlightBooking flightbooking, Model model) {
        model.addAttribute("flightbooking", flightbooking);
        createFlightBooking(flightbooking);
        String car_url = "http://host.docker.internal:8080/carTable/" + flightbooking.getCarId() + "/flightId/" + flightbooking.getId();
        restTemplate.exchange(car_url, HttpMethod.PUT, null, String.class);
        String hotel_url = "http://host.docker.internal:8082/hotelTable/" + flightbooking.getHotelId() + "/flightId/" + flightbooking.getId();
        restTemplate.exchange(hotel_url, HttpMethod.PUT, null, String.class);
        return "result";
    }

    @GetMapping("/flightTable")
    @ResponseBody
    public ResponseEntity<List<FlightBooking>> getAllFlightBookings() {
        try {
            List<FlightBooking> flightTable = new ArrayList<FlightBooking>();

            flightBookingRepository.findAll().forEach(flightTable::add);

            if (flightTable.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(flightTable, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/flightTable/{id}")
    @ResponseBody
    public ResponseEntity<FlightBooking> getFlightBookingById(@PathVariable("id") long id) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            return new ResponseEntity<>(flightBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/flightTable/{id}/carId/{carId}")
    @ResponseBody
    public ResponseEntity<FlightBooking> updateCarId(@PathVariable("id") long id, @PathVariable("carId") long carId) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            FlightBooking flightBooking = flightBookingData.get();
            flightBooking.setCarId(carId);

            return new ResponseEntity<>(flightBookingRepository.save(flightBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @PutMapping("/flightTable/{id}/hotelId/{hotelId}")
    @ResponseBody
    public ResponseEntity<FlightBooking> updateHotelId(@PathVariable("id") long id, @PathVariable("hotelId") long hotelId) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            FlightBooking flightBooking = flightBookingData.get();
            flightBooking.setHotelId(hotelId);

            return new ResponseEntity<>(flightBookingRepository.save(flightBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/flightTable/info/{id}")
    @ResponseBody
    public ResponseEntity<String> getFlightBookingInfo(@PathVariable("id") long id) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            String origin = flightBookingData.get().getOrigin();
            String destination = flightBookingData.get().getDestination();
            return new ResponseEntity<>("Origin: " + origin + "\n" + "Destination: " + destination + "\n", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No flight booking with that id", HttpStatus.OK);
        }
    }

    @PostMapping("/flightTable")
    @ResponseBody
    public ResponseEntity<FlightBooking> createFlightBooking(@RequestBody FlightBooking flightBooking) {
        try {
            FlightBooking _flightBooking = flightBookingRepository
                    //.save(new FlightBooking(flightBooking.getContent(), flightBooking.getBrand(), flightBooking.getFrom(), flightBooking.getTo()));
                    .save(flightBooking);
            return new ResponseEntity<>(_flightBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/flightTable/{id}")
    @ResponseBody
    public ResponseEntity<FlightBooking> updateFlightBooking(@PathVariable("id") long id, @RequestBody FlightBooking flightBooking) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            FlightBooking _flightBooking = flightBookingData.get();
            _flightBooking.setOrigin(flightBooking.getOrigin());
            _flightBooking.setDestination(flightBooking.getDestination());
            _flightBooking.setFlightNumber(flightBooking.getFlightNumber());
            _flightBooking.setCarId(flightBooking.getCarId());
            _flightBooking.setHotelId(flightBooking.getHotelId());
            return new ResponseEntity<>(flightBookingRepository.save(_flightBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/flightTable/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteFlightBooking(@PathVariable("id") long id) {
        try {
            flightBookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/flightTable")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteAllFlightBookings() {
        try {
            flightBookingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}