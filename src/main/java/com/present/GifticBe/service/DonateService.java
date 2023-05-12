package com.present.GifticBe.service;

import com.present.GifticBe.domain.*;
import com.present.GifticBe.repository.DonateRepository;
import com.present.GifticBe.repository.PostRepository;
import com.present.GifticBe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DonateService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final DonateRepository donateRepository;

    @Transactional
    public Long Donate(Long userId, Long postId) {
        User user = userRepository.findUserById(userId).get();
        Posts post = postRepository.findById(postId).get();

        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);

        Donate donate = Donate.createDonate(user, post, delivery);
        donateRepository.save(donate);

        return donate.getId();
    }

    @Transactional
    public void cancelDonate(Long donateId) {
        Donate donate = donateRepository.findDonateById(donateId).get();
        donate.cancel();
    }
}
