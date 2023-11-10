package me.shindonghyeok.kiosk_main.service;

import me.shindonghyeok.kiosk_main.domain.ReservationInfo;
import me.shindonghyeok.kiosk_main.domain.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    List<ReservationInfo> getAllReservations();
    ReservationInfo getReservationByUsername(String username);

    ReservationInfo createReservation(String username, LocalDate reservationDate, LocalTime reservationTime);

    ReservationInfo updateReservationStatus(String username, ReservationStatus status);
    void deleteReservationByUsername(String username);
}
