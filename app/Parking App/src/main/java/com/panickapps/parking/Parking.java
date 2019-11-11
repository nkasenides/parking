package com.panickapps.parking;

public class Parking {

    private String id;
    private ParkingStatus status;

    public Parking(String id, ParkingStatus status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParkingStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingStatus status) {
        this.status = status;
    }

}
