package com.university.bibliotheca.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.Reservation;

import java.util.Date;

public class    ReservationRequest {

    @JsonProperty(value ="userName")
    String userName;
    @JsonProperty(value ="occupation")
    Occupation occupation;
    @JsonProperty(value ="reservationDate")
    Date reservationDate;

    public ReservationRequest(Reservation reservation){
        this.userName = reservation.getUserId();
        this.occupation = reservation.getOccupation();
        this.reservationDate = reservation.getReservationDate();
    }

    public Reservation toDomain(){
        return new Reservation(null, this.userName, this.occupation, this.reservationDate);
    }
}
