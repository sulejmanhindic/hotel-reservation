package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource = new HotelResource();

    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    public static void createACustomer(String email, String fistName, String lastName) {
        CustomerService.addCustomer(email, fistName, lastName);
    }

    public static IRoom getRoom (String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return ReservationService.reserveARoom(CustomerService.getCustomer(customerEmail), room, checkInDate, checkOutDate );
    }

    public static Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = CustomerService.getCustomer(customerEmail);

        return ReservationService.getCustomerReservation(customer);
    }

    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {

        return ReservationService.findRooms(checkIn,checkOut );
    }
}
