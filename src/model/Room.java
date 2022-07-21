package model;

import java.util.Objects;

public class Room implements IRoom {
    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setEnumeration(String enumeration) throws IllegalArgumentException {
        if (enumeration.equals(RoomType.SINGLE.toString())) {
            this.enumeration = RoomType.SINGLE;
        } else if (enumeration.equals(RoomType.DOUBLE.toString())) {
            this.enumeration = RoomType.DOUBLE;
        } else {
            throw new IllegalArgumentException("The input " + enumeration + " for room type is neither SINGLE nor DOUBLE. " +
                    "Room was not created.");
        }
    }

    @Override
    public String toString() {
        return "Room number : " + roomNumber +
                ", price : " + price +
                ", type : " + enumeration;
    }

    @Override
    public String getRoomNumber() {

        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {

        return this.getRoomType();
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(price, room.price) && enumeration == room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
