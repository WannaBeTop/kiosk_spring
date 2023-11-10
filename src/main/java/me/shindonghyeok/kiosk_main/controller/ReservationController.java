package me.shindonghyeok.kiosk_main.controller;

import me.shindonghyeok.kiosk_main.domain.ReservationInfo;
import me.shindonghyeok.kiosk_main.domain.ReservationStatus;
import me.shindonghyeok.kiosk_main.repository.ReservationRepository;
import me.shindonghyeok.kiosk_main.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/createReservation")
    public String showReservationForm() {
        return "createReservation"; // HTML 템플릿 이름
    }

    @PostMapping("/createReservation")
    public String createReservation(
            @RequestParam String username,
            @RequestParam String reservationDate,
            @RequestParam String reservationTime,
            UriComponentsBuilder uriBuilder) {

        // 예약 생성 로직
        LocalDate date = LocalDate.parse(reservationDate);
        LocalTime time = LocalTime.parse(reservationTime);
        reservationService.createReservation(username, date, time);

        // UriComponentsBuilder를 사용하여 URL을 빌드하고 인코딩
        return "redirect:" + uriBuilder.path("/reservation/{username}")
                .buildAndExpand(username)
                .encode()
                .toUriString();
    }

    @GetMapping("/reservation/{username}")
    public String showReservationList(@PathVariable String username, Model model) {
        // 예약 정보를 가져와서 모델에 추가
        ReservationInfo reservation = reservationService.getReservationByUsername(username);
        model.addAttribute("reservation", reservation);

        return "reservationDetails"; // 예약 상세 정보를 보여줄 HTML 페이지 이름
    }


    @PostMapping("/reservation/{username}/updateStatus")
    public String updateReservationStatus(
            @PathVariable String username,
            @RequestParam String status,
            Model model) {

        // 예약 상태 업데이트 로직
        ReservationStatus reservationStatus = ReservationStatus.valueOf(status);
        reservationService.updateReservationStatus(username, reservationStatus);

        // 업데이트된 예약 정보를 다시 가져오기
        ReservationInfo updatedReservation = reservationService.getReservationByUsername(username);
        model.addAttribute("reservation", updatedReservation);

        // 상세 정보 페이지로 리다이렉션
        return "redirect:/reservation/" + username;
    }

    @PostMapping("/reservation/{username}/delete")
    public String deleteReservation(
            @PathVariable String username) {

        // 예약 삭제 로직
        reservationService.deleteReservationByUsername(username);

        // 예약 목록 페이지로 리다이렉션
        return "redirect:/reservationList";
    }

    @GetMapping("/reservationList")
    public String getAllReservation(){
        return "reservationList";
    }


}

