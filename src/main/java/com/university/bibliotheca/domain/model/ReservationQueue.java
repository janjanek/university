package com.university.bibliotheca.domain.model;

import lombok.Value;

import java.util.List;

@Value
public class ReservationQueue {
    String name;
    List<Reservation> userReservations;
}