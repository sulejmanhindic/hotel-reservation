package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService reservationService = new ReservationService();
    private static Collection<Reservation> reservations = new ArrayList<Reservation>();
    private static Map<String, IRoom> roomsMap = new HashMap<String, IRoom>();

    public static void addRoom(IRoom room) {
        roomsMap.put(room.getRoomNumber(), room);
    }

    public static IRoom getARoom (String roomId) {
        return roomsMap.get(roomId);
    }

    public static Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation res = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(res);
        return res;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<IRoom>();

        for (IRoom room : roomsMap.values()) {
            availableRooms.add(room);
        }

        for (Reservation res : reservations) {
            if (checkCheckInDate(checkInDate, res) || checkCheckOutDate(checkOutDate, res) ||
                    checkInBetween(checkInDate, checkOutDate, res)) {
                availableRooms.remove(res.getRoom());
            } else {

            }
        }

        return availableRooms;
    }

    private static boolean checkCheckInDate(Date checkin, Reservation reservation) {
        return ((checkin.after(reservation.getCheckInDate()) && checkin.before(reservation.getCheckOutDate())) ||
                checkin.equals(reservation.getCheckInDate()) || checkin.equals(reservation.getCheckOutDate()));
    }

    private static boolean checkCheckOutDate(Date checkout, Reservation reservation) {
        return ((checkout.after(reservation.getCheckInDate()) && checkout.before(reservation.getCheckOutDate())) ||
                checkout.equals(reservation.getCheckInDate()) || checkout.equals(reservation.getCheckOutDate()));
    }

    private static boolean checkInBetween(Date checkin, Date checkout, Reservation reservation) {
        return (reservation.getCheckInDate().after(checkin) && reservation.getCheckOutDate().after(checkin) &&
                reservation.getCheckInDate().before(checkout) && reservation.getCheckOutDate().before(checkout));
    }

    public static Collection<Reservation> getCustomerReservation (Customer customer) {
        List<Reservation> customerReservation = new ArrayList<Reservation>();
        for(Reservation res : reservations) {
            if (res.getCustomer().getEmail().toString().equals(customer.getEmail().toString())) {
                customerReservation.add(res);
            }
        }
        return customerReservation;
    }

    public static void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("There are not any reservations.");
        } else {
            printReservations();
        }
    }

    static void printReservations() {
        for (Reservation res : reservations) {
            System.out.println(res.toString());
        }
    }

    public static Collection<IRoom> getAllRooms() {
        return roomsMap.values();
    }
}
