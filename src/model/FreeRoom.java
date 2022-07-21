package model;

public class FreeRoom extends Room {
    public FreeRoom() {
        this.price = 0.0;
    }

    @Override
    public String toString() {
        return "Free room with room number = " + roomNumber +
                ", price = " + price +
                ", enumeration = " + enumeration;
    }
}
