package ruslan.simakov.naval.model;

public record BookingResult(boolean success, String message) {

    public static BookingResult successful() {
        return new BookingResult(true, "OK");
    }

    public static BookingResult failed(String msg) {
        return new BookingResult(false, msg);
    }

    public String status() {
        return success ? "SUCCESS" : "FAIL";
    }
}
