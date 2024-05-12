package com.university.bibliotheca.domain.model;

import lombok.Value;

import java.util.Date;

@Value
public class Reservation {
    String userId;
    String userName;
    Occupation occupation;
    Date reservationDate;
}
