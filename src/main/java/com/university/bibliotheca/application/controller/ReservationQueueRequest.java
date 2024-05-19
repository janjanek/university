package com.university.bibliotheca.application.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationQueueRequest {

    @JsonProperty(value = "name")
    String name;
    @JsonProperty(value = "userReservations")
    List<ReservationRequest> userReservationRequests;


    public ReservationQueueRequest(ReservationQueue reservationQueue){
        this.name = reservationQueue.getName();
        this.userReservationRequests = reservationQueue.getUserReservations().stream().map(ReservationRequest::new).collect(Collectors.toList());
    }

    public ReservationQueue toDomain(){
        List<Reservation> domainReservations = this.userReservationRequests.stream().map(reservation -> {
            try {
                return reservation.toDomain();
            } catch (ParseException e) {
                return null;
            }

        }).collect(Collectors.toList());
        return new ReservationQueue(this.name, domainReservations);

    }
}


