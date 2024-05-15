package com.university.bibliotheca.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationRequest {

    @JsonProperty(value ="userId")
    String userId;
    @JsonProperty(value ="userName")
    String userName;
    @JsonProperty(value ="occupation")
    Occupation occupation;
    @JsonProperty(value ="reservationDate")
    String reservationDate;

    public ReservationRequest(Reservation reservation){
        this.userId = reservation.getUserId();
        this.userName = reservation.getUserName();
        this.occupation = reservation.getOccupation();
        if(reservation.getReservationDate() != null) {
            this.reservationDate = reservation.getReservationDate().toString();
        }
    }

    public Reservation toDomain() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date date = dateFormat.parse(this.reservationDate);
            return new Reservation(userId, this.userName, this.occupation, date);
    }
}
