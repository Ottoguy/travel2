package  com.agency.travel2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HotelBookingController {
    private List<HotelBooking> bookings;

    public HotelBookingController() {
        bookings = new ArrayList<>();
    }

    @GetMapping("/bookings")
    public String getAllBookings(Model model) {
        if (bookings.isEmpty()) {
            return "empty";
        }
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @GetMapping("/bookings/{id}")
    public String getBookingById(Model model, @PathVariable("id") int id) {
        for (HotelBooking b : bookings) {
            List tempList = new ArrayList<HotelBooking>();
            tempList.add(b);
            if (b.getId() == id) {
                model.addAttribute("bookings", tempList);
                return "bookings";
            }
        }
        return "notfound";
    }

    @PostMapping("/bookings")
    public String createBooking(@RequestBody HotelBooking newBooking, Model model) {
        int newId = bookings.size() + 1; // unique ID
        newBooking.setId(newId);
        bookings.add(newBooking);
        model.addAttribute("booking", newBooking);

        return "/bookings";
    }

    @PutMapping("/bookings/{id}") // update dates and the city.
    public String updateBooking(@PathVariable("id") int id, @RequestBody HotelBooking fieldsToUpdate, Model model) {
        for (HotelBooking b : bookings) {
            if (b.getId() == id) {
                b.setCheckOutDate(fieldsToUpdate.getCheckInDate());
                b.setCheckOutDate(fieldsToUpdate.getCheckInDate());
                b.setNumberOfPersons(fieldsToUpdate.getNumberOfPersons());
                return "updated";
            }
        }
        model.addAttribute("booking", id);
        return "notfound";
    }

    @DeleteMapping("/bookings/{id}")
    public String deleteBooking(@PathVariable("id") int id, Model model) {
        for (HotelBooking booking : bookings) {
            if (booking.getId() == id) {
                model.addAttribute("booking", bookings);
                bookings.remove(booking);

                return "removed";
            }
        }
        return "notfound";
    }
}
