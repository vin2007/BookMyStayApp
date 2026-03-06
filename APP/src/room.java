import java.util.ArrayList;
import java.util.List;

abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sq ft");
        System.out.println("Price: $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 100);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 350, 180);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 600, 350);
    }
}

class RoomInventory {

    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void displayAllRooms() {
        for (Room room : rooms) {
            room.displayRoomDetails();
            System.out.println();
        }
    }
}

public class HotelApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.addRoom(new SingleRoom());
        inventory.addRoom(new DoubleRoom());
        inventory.addRoom(new SuiteRoom());

        inventory.displayAllRooms();
    }
}