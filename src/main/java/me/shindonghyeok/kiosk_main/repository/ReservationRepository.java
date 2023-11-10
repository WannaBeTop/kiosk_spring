package me.shindonghyeok.kiosk_main.repository;

import me.shindonghyeok.kiosk_main.domain.ReservationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationInfo, Long> {
    //리포지터리의 대상이 되는 엔티티의 타입(Question)과 해당 엔티티의 PK의 속성 타입(Integer)을 지정해야 한다. 이것은 JpaRepository를 생성하기 위한 규칙이다.

    // @Repository 어노테이션을 사용하여 이 인터페이스가 스프링 빈으로 등록되도록 표시

    // 별도의 메서드 정의 없이 JpaRepository에서 기본적인 CRUD 작업을 상속받을 수 있음.

    ReservationInfo findByUsername(String username);

    List<ReservationInfo> findByReservationDateTime(LocalDateTime reservationDateTime);
}