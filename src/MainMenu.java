import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    static Scanner input = new Scanner(System.in);
    static String email;
    static String firstName;
    static String lastName;
    static String roomnumber;
    static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    static String regex = "^[0-9]{2}/[0-9]{2}/[0-9]{4}$";
    static Pattern pattern = Pattern.compile(regex);
    static IRoom room;
    final static int PERIOD = 7;

    public static void main() {

        int option = 0;

        while (option != 5) {
            System.out.println("Welcome to the Hotel Reservation");
            System.out.println("-------------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("-------------------------------------");
            System.out.println("Please select a number");

            try {
                option = input.nextInt();
                System.out.println();

                switch (option) {
                    case 1:
                        reserveRoom();
                        break;
                    case 2:
                        seeReservations();
                        break;
                    case 3:
                        createAccount();
                        break;
                    case 4:
                        AdminMenu.main();
                        break;
                    case 5:
                        System.out.println("Program Hotel Reservation finished");
                        break;
                    default:
                        System.out.println("The option you entered is not available.");
                        System.out.println("Please select the option 1, 2, 3, 4 or 5.");
                        System.out.println();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("The option you entered is not available");
                System.out.println("Please select the option 1, 2, 3, 4 or 5.");
                System.out.println();
                input.nextLine();
            }
        }
    }

    public static void reserveRoom() {
        boolean checkinOK = false;
        Date checkinDate = new Date();
        Date checkoutDate = new Date();
        String checkin = "";
        String checkout = "";


        do {
            try {
                System.out.println("Type in your check-in date - format dd/MM/yyyy");
                checkin = input.next();

                if (pattern.matcher(checkin).matches()) {
                    checkinDate = df.parse(checkin);
                    checkinOK = true;
                } else {
                    System.out.println("The date you entered is not in the format dd/MM/yyyy");
                    checkinOK = false;
                }
            } catch (ParseException e) {
                System.out.println("The date you entered is not in the format dd/MM/yyyy");
                System.out.println();
                input.nextLine();
            }
        } while (checkinOK == false);

        boolean checkoutOK = false;
        do {
            try {
                System.out.println("Type in your check-out date - format dd/MM/yyyy");
                checkout = input.next();

                if (pattern.matcher(checkout).matches()) {
                    checkoutDate = df.parse(checkout);
                    checkoutOK = true;
                } else {
                    System.out.println("The date you entered is not in the format dd/MM/yyyy");
                    checkoutOK = false;
                }
            } catch (ParseException e) {
                System.out.println("The date you entered is not in the format dd/MM/yyyy");
                System.out.println();
                input.nextLine();
            }
        } while(checkoutOK == false);

        if (checkinDate.before(checkoutDate) && checkoutDate.after(checkinDate)) {
            List<IRoom> roomlist = (List<IRoom>) HotelResource.findARoom(checkinDate, checkoutDate);

            if (!roomlist.isEmpty()) {
                System.out.println("Available rooms in desired period from " + checkinDate + " to " + checkoutDate);
                bookRoom(roomlist, checkinDate, checkoutDate);

            } else {
                System.out.println("Find available rooms in the alternative period " + PERIOD + " days later:");

                checkinDate = setAlternativeDate(checkinDate);
                checkoutDate = setAlternativeDate(checkoutDate);

                System.out.println("Available rooms in an alternative period from " + checkinDate + " to " + checkoutDate);

                roomlist = (List<IRoom>) HotelResource.findARoom(checkinDate, checkoutDate);

                if (!roomlist.isEmpty()) {
                    bookRoom(roomlist, checkinDate, checkoutDate);
                } else {
                    System.out.println("There are no rooms available in the alternative period.");
                }
            }
        } else {
            System.out.println("The check-in date is after the check-out date. Please start again.");
        }
    }

    public static Date setAlternativeDate(Date date) {
        Calendar alternativeDate = Calendar.getInstance();
        alternativeDate.setTime(date);
        alternativeDate.add(Calendar.DATE, PERIOD);
        return alternativeDate.getTime();
    }

    public static void bookRoom(List<IRoom> roomlist, Date checkinDate, Date checkoutDate) {
        boolean freeroom = false;

        for (IRoom room : roomlist) {
            System.out.println(room.toString());
        }

        try {
            do {
                freeroom = false;
                System.out.println("Type in your room");
                roomnumber = input.next();
                room  = HotelResource.getRoom(roomnumber);

                for (IRoom room : roomlist) {
                    if(room.getRoomNumber().equals(roomnumber)) {
                        freeroom = true;
                    }
                }

                if (room != null && freeroom == true) {
                    System.out.println("The room with the number " + roomnumber + " is available for the dates you want to book.");

                    do {
                        try {
                            System.out.println("Type in your email address");
                            email = input.next();

                            if (HotelResource.getCustomer(email) != null) {
                                System.out.println("The customer " + email + " does exist.");

                                HotelResource.bookARoom(email, room, checkinDate, checkoutDate);

                                System.out.println("The room " + room + " is booked from " + checkinDate.toString() +
                                        " to " + checkoutDate.toString() + " for " + email);
                            } else {
                                System.out.println("The customer " + email + " does not exist.");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println(e.getMessage());
                            System.out.println();
                            input.nextLine();
                        }
                    } while (email == null);

                } else {
                    if (freeroom == false) {
                        System.out.println("The room with the number " + roomnumber + " is already booked for the date you desire.");
                    } else {
                        System.out.println("The room with the number " + roomnumber + " does not exist.");
                    }
                }
            } while (room == null);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println();
            input.nextLine();
        }
    }

    public static void seeReservations() {
        try {
            System.out.println("Type in your email address");
            email = input.next();

            if (HotelResource.getCustomer(email) != null) {
                Collection<Reservation> customerReservation = HotelResource.getCustomersReservations(email);

                if (customerReservation.isEmpty()) {
                    System.out.println("The are no reservation for the customer " + email + ".");
                } else {
                    System.out.println("Reservations for the customer " + email + " :");

                    for (Reservation res : customerReservation) {
                        System.out.println(res.toString());
                    }
                }
            } else {
                System.out.println("The customer with the mail " + email + " does not exist.");
            }

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println();
            input.nextLine();
        }
    }

    public static void createAccount() {
        try {
            System.out.println("Type in your first name:");
            firstName = input.next();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println();
            input.nextLine();
        }

        try {
            System.out.println("Type in your last name:");
            lastName = input.next();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println();
            input.nextLine();
        }

        try {
            System.out.println("Type in your email address:");
            email = input.next();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println();
            input.nextLine();
        }

        if (HotelResource.getCustomer(email) == null) {
            try {
                HotelResource.createACustomer(email, firstName, lastName);
                System.out.println("The customer " + HotelResource.getCustomer(email).getEmail() +
                        " was created.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("The customer was not created.");
                System.out.println();
                input.nextLine();
            }
        } else {
            System.out.println("The customer " + HotelResource.getCustomer(email).getEmail() +
                    " already exists.");
        }
    }
}
