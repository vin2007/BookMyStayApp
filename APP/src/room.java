import java.util.*;

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventoryService {

    private HashMap<String, Integer> inventory;

    public RoomInventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {

        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {

        inventory.put(roomType,
                inventory.get(roomType) - 1);
    }
}

class BookingService {

    private Queue<Reservation> requestQueue;

    private RoomInventoryService inventoryService;

    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService(Queue<Reservation> requestQueue,
                          RoomInventoryService inventoryService) {

        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;

        allocatedRooms = new HashMap<>();
    }

    public void processRequests() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();

            String roomType = reservation.getRoomType();

            if (inventoryService.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRooms
                        .putIfAbsent(roomType, new HashSet<>());

                allocatedRooms
                        .get(roomType)
                        .add(roomId);

                inventoryService
                        .decrementAvailability(roomType);

                System.out.println("Reservation Confirmed for "
                        + reservation.getGuestName()
                        + " → Assigned Room ID: "
                        + roomId);

            } else {

                System.out.println("Reservation Failed for "
                        + reservation.getGuestName()
                        + " → No rooms available");
            }
        }
    }

    private String generateRoomId(String roomType) {

        return roomType.substring(0, 2).toUpperCase()
                + "-"
                + UUID.randomUUID().toString().substring(0, 4);
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        Queue<Reservation> requestQueue =
                new LinkedList<>();

        requestQueue.add(
                new Reservation("Pranjal", "Single Room"));

        requestQueue.add(
                new Reservation("Rahul", "Double Room"));

        requestQueue.add(
                new Reservation("Sneha", "Suite Room"));

        requestQueue.add(
                new Reservation("Amit", "Suite Room"));

        RoomInventoryService inventoryService =
                new RoomInventoryService();

        BookingService bookingService =
                new BookingService(requestQueue,
                        inventoryService);

        bookingService.processRequests();
    }
}