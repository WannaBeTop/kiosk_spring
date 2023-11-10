package me.shindonghyeok.kiosk_main.service;

import me.shindonghyeok.kiosk_main.domain.ReservationInfo;
import me.shindonghyeok.kiosk_main.domain.ReservationStatus;
import me.shindonghyeok.kiosk_main.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationServiceDetail implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceDetail(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationInfo> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public ReservationInfo getReservationByUsername(String username) {
        return reservationRepository.findByUsername(username);
    }

    @Override
    public ReservationInfo createReservation(String username, LocalDate reservationDate, LocalTime reservationTime) {
        // 특정 날짜와 시간에 예약이 이미 존재하는지 확인
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);
        List<ReservationInfo> existingReservations = reservationRepository.findByReservationDateTime(reservationDateTime);
        if (!existingReservations.isEmpty()) {
            throw new IllegalStateException("The reservation already exists at the given date and time.");
        }

        // 예약 생성
        ReservationInfo reservation = new ReservationInfo();
        reservation.setUsername(username);
        reservation.setReservationDateTime(reservationDateTime);
        reservation.setStatus(ReservationStatus.RESERVED); // 예약 상태 초기화

        return reservationRepository.save(reservation);
    }


    @Override
    public ReservationInfo updateReservationStatus(String username, ReservationStatus status) {
        ReservationInfo reservationInfo = reservationRepository.findByUsername(username);
        if (reservationInfo != null) {
            reservationInfo.setStatus(status);
            return reservationRepository.save(reservationInfo);
        }
        return null;
    }

    @Override
    public void deleteReservationByUsername(String username) {
        try {
            ReservationInfo reservationInfo = reservationRepository.findByUsername(username);
            if (reservationInfo != null) {
                reservationRepository.delete(reservationInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
