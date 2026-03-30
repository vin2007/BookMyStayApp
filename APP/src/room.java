import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Reservation Class (Serializable)
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId,
                       String guestName,
                       String roomType) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {

        System.out.println("Reservation ID: "
                + reservationId);

        System.out.println("Guest Name: "
                + guestName);

        System.out.println("Room Type: "
                + roomType);

        System.out.println("--------------------------");
    }
}


// System State Container (Serializable)
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> reservations;

    public SystemState(Map<String, Integer> inventory,
                       List<Reservation> reservations) {

        this.inventory = inventory;
        this.reservations = reservations;
    }
}


// Persistence Service
class PersistenceService {

    private static final String FILE_NAME =
            "system_state.dat";


    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println(
                    "System state saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error saving system state.");
        }
    }


    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(FILE_NAME))) {

            System.out.println(
                    "System state restored successfully.");

            return (SystemState) in.readObject();

        } catch (Exception e) {

            System.out.println(
                    "No previous state found. Starting fresh.");

            return null;
        }
    }
}


// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistenceService =
                new PersistenceService();


        SystemState state =
                persistenceService.loadState();


        Map<String, Integer> inventory;
        List<Reservation> reservations;


        if (state == null) {

            inventory = new HashMap<>();

            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);

            reservations = new ArrayList<>();


            reservations.add(
                    new Reservation(
                            "RES301",
                            "Pranjal",
                            "Single Room"));

            reservations.add(
                    new Reservation(
                            "RES302",
                            "Rahul",
                            "Suite Room"));

        } else {

            inventory = state.inventory;
            reservations = state.reservations;
        }


        System.out.println("\nCurrent Reservations:");

        for (Reservation reservation :
                reservations) {

            reservation.displayReservation();
        }


        System.out.println("Current Inventory:");

        for (String room : inventory.keySet()) {

            System.out.println(room + " : "
                    + inventory.get(room));
        }


        SystemState newState =
                new SystemState(
                        inventory,
                        reservations);


        persistenceService.saveState(newState);
    }
}