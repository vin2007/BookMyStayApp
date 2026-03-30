import java.util.HashMap;
import java.util.Map;


// Custom Exception Class
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


// Room Inventory Manager
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }


    public void validateRoomType(String roomType)
            throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {

            throw new InvalidBookingException(
                    "Invalid room type selected: "
                            + roomType);
        }
    }


    public void validateAvailability(String roomType)
            throws InvalidBookingException {

        if (inventory.get(roomType) <= 0) {

            throw new InvalidBookingException(
                    "Room not available: "
                            + roomType);
        }
    }


    public void reserveRoom(String roomType)
            throws InvalidBookingException {

        validateRoomType(roomType);

        validateAvailability(roomType);

        inventory.put(roomType,
                inventory.get(roomType) - 1);

        System.out.println("Room reserved successfully: "
                + roomType);
    }


    public void displayInventory() {

        System.out.println("\nCurrent Inventory Status:");

        for (String room : inventory.keySet()) {

            System.out.println(room + " : "
                    + inventory.get(room));
        }
    }
}


// Booking Validator Service
class BookingValidatorService {

    public void validateBookingInput(String guestName,
                                     String roomType)
            throws InvalidBookingException {

        if (guestName == null
                || guestName.trim().isEmpty()) {

            throw new InvalidBookingException(
                    "Guest name cannot be empty");
        }

        if (roomType == null
                || roomType.trim().isEmpty()) {

            throw new InvalidBookingException(
                    "Room type must be selected");
        }
    }
}


// Main Class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory =
                new RoomInventory();

        BookingValidatorService validator =
                new BookingValidatorService();


        try {

            String guestName = "Pranjal";
            String roomType = "Suite Room";

            validator.validateBookingInput(
                    guestName,
                    roomType);

            inventory.reserveRoom(roomType);


        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: "
                    + e.getMessage());
        }


        try {

            String guestName = "";
            String roomType = "Luxury Room";

            validator.validateBookingInput(
                    guestName,
                    roomType);

            inventory.reserveRoom(roomType);


        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: "
                    + e.getMessage());
        }


        inventory.displayInventory();
    }
}