package com.university.bibliotheca.adapter;

import com.university.bibliotheca.domain.model.Occupation;
import lombok.Value;

import java.util.Date;

@Value
public class ReservationDto {
    String userId;
    String bookName;
    Occupation occupation;
    Date reservationDate;
}


