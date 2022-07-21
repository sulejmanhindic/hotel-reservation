import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import java.util.*;

public class AdminMenu {
    static Scanner input = new Scanner(System.in);

    public static void main() {
        int option = 0;

        while (option != 5) {
            System.out.println("Admin menu");
            System.out.println("-------------------------------------");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Back to Main Menu");
            System.out.println("-------------------------------------");
            System.out.println("Please select a number:");

            try {
                option = input.nextInt();
                System.out.println();

                switch (option) {
                    case 1:
                        seeCustomers();
                        break;
                    case 2:
                        seeRooms();
                        break;
                    case 3:
                        seeReservations();
                        break;
                    case 4:
                        addARoom();
                        break;
                    case 5:
                        System.out.println("Back to main menu");
                        System.out.println("----------------------------------------");
                        break;
                    default:
                        System.out.println("The option you entered is not available.");
                        System.out.println("Please select one of the options 1, 2, 3, 4 or 5.");
                        System.out.println();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("The text you entered is not an option.");
                System.out.println("Please select one of the options 1, 2, 3, 4 or 5.");
                System.out.println();
                input.nextLine();
            }
        }
    }

    public static void seeCustomers() {
        Collection<Customer> customerList = AdminResource.getAllCustomer();

        if (customerList.isEmpty()) {
            System.out.println("No customers were created.");
        } else {
            System.out.println("List of registered customers:");
            for(Customer customer : customerList) {
                System.out.println(customer.toString());
            }
        }

        System.out.println();
    }

    public static void seeRooms() {
        Collection<IRoom> roomList = AdminResource.getAllRooms();

        if(roomList.isEmpty()) {
            System.out.println("No rooms were created.");
        } else {
            System.out.println("List of existing room:");
            for(IRoom room : roomList) {
                System.out.println(room.toString());
            }
        }

        System.out.println();
    }

    public static void seeReservations() {
        System.out.println("List of reservations:");
        AdminResource.displayAllReservations();
        System.out.println();
    }

    public static void addARoom() {
        String addAnotherRoom = "Y";

        do {
            try {
                System.out.println("Enter a room number");
                String roomNumber = input.next();

                System.out.println("Enter a type: SINGLE OR DOUBLE");
                String type = input.next();

                System.out.println("Enter a price");
                String price = input.next();

                if (HotelResource.getRoom(roomNumber) == null) {
                    Room newRoom = new Room();

                    newRoom.setEnumeration(type);
                    newRoom.setRoomNumber(roomNumber);
                    newRoom.setPrice(Double.parseDouble(price));

                    AdminResource.addRoom(Collections.singletonList(newRoom));
                    System.out.println("A room with " + newRoom.toString() + " was created.");
                } else {
                    System.out.println("The room with the number " + roomNumber + " already exists.");
                    System.out.println("Please type in another number.");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Do you want to add another room? Type Y for Yes, any other input quits the process");
                String answer = input.next();

                if(answer.equals("Y")) {

                } else {
                    addAnotherRoom = "N";
                }

            }
        } while (addAnotherRoom.equals("Y"));
    }
}
