package com.university.bibliotheca.domain.model;

import com.university.bibliotheca.adapter.ReservationDto;
import lombok.Value;

import java.util.Date;

@Value
public class Reservation {
    String userId;
    String bookName;
    Occupation occupation;
    Date reservationDate;

    public ReservationDto toDto(){
        return new ReservationDto(this.userId, this.bookName, this.occupation, this.reservationDate);
    }
}
