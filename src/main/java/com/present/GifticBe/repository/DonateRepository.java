package com.present.GifticBe.repository;

import com.present.GifticBe.domain.Donate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonateRepository extends JpaRepository<Donate, Long> {
    Optional<Donate> findDonateById(Long donateId);
}
