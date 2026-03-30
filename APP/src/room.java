import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;


// Booking Request Class
class BookingRequest {

    private String guestName;
    private String roomType;

    public BookingRequest(String guestName,
                          String roomType) {

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


// Shared Inventory Manager (Thread Safe)
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }


    public synchronized boolean allocateRoom(
            String roomType) {

        if (inventory.get(roomType) > 0) {

            inventory.put(roomType,
                    inventory.get(roomType) - 1);

            return true;
        }

        return false;
    }


    public synchronized void displayInventory() {

        System.out.println("\nFinal Inventory Status:");

        for (String room : inventory.keySet()) {

            System.out.println(room + " : "
                    + inventory.get(room));
        }
    }
}


// Shared Booking Queue
class BookingQueue {

    private Queue<BookingRequest> queue =
            new LinkedList<>();


    public synchronized void addRequest(
            BookingRequest request) {

        queue.add(request);

        System.out.println("Request added: "
                + request.getGuestName());
    }


    public synchronized BookingRequest getRequest() {

        return queue.poll();
    }
}


// Booking Processor Thread
class BookingProcessor extends Thread {

    private BookingQueue bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(
            BookingQueue bookingQueue,
            RoomInventory inventory) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }


    public void run() {

        BookingRequest request;

        while ((request =
                bookingQueue.getRequest()) != null) {

            processRequest(request);
        }
    }


    private void processRequest(
            BookingRequest request) {

        synchronized (inventory) {

            boolean allocated =
                    inventory.allocateRoom(
                            request.getRoomType());

            if (allocated) {

                System.out.println(
                        request.getGuestName()
                                + " successfully booked "
                                + request.getRoomType());

            } else {

                System.out.println(
                        "Booking failed for "
                                + request.getGuestName()
                                + " (No rooms available)");
            }
        }
    }
}


// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        RoomInventory inventory =
                new RoomInventory();

        BookingQueue queue =
                new BookingQueue();


        queue.addRequest(
                new BookingRequest(
                        "Pranjal",
                        "Single Room"));

        queue.addRequest(
                new BookingRequest(
                        "Rahul",
                        "Single Room"));

        queue.addRequest(
                new BookingRequest(
                        "Sneha",
                        "Single Room"));

        queue.addRequest(
                new BookingRequest(
                        "Amit",
                        "Suite Room"));


        BookingProcessor processor1 =
                new BookingProcessor(
                        queue,
                        inventory);

        BookingProcessor processor2 =
                new BookingProcessor(
                        queue,
                        inventory);


        processor1.start();
        processor2.start();


        try {

            processor1.join();
            processor2.join();

        } catch (InterruptedException e) {

            System.out.println(
                    "Thread interrupted");
        }


        inventory.displayInventory();
    }
}