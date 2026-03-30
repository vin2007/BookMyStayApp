import java.util.ArrayList;
import java.util.List;


class Reservation {

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

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {

        System.out.println("Reservation ID: "
                + reservationId);

        System.out.println("Guest Name: "
                + guestName);

        System.out.println("Room Type: "
                + roomType);

        System.out.println("---------------------------");
    }
}


class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {

        history = new ArrayList<>();
    }

    public void addReservation(
            Reservation reservation) {

        history.add(reservation);

        System.out.println("Reservation stored: "
                + reservation.getReservationId());
    }

    public List<Reservation> getHistory() {

        return history;
    }
}


class BookingReportService {

    public void generateReport(
            List<Reservation> reservations) {

        System.out.println("\nBooking History Report\n");

        for (Reservation reservation :
                reservations) {

            reservation.displayReservation();
        }

        System.out.println("Total Reservations: "
                + reservations.size());
    }
}


public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history =
                new BookingHistory();


        Reservation r1 =
                new Reservation(
                        "RES101",
                        "Pranjal",
                        "Single Room");

        Reservation r2 =
                new Reservation(
                        "RES102",
                        "Rahul",
                        "Double Room");

        Reservation r3 =
                new Reservation(
                        "RES103",
                        "Sneha",
                        "Suite Room");


        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);


        BookingReportService reportService =
                new BookingReportService();

        reportService.generateReport(
                history.getHistory());
    }
}