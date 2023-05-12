package com.present.GifticBe.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Donate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donate_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User donate_user;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Posts posts;

    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL)
    private Set<User> benefit_user;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private DonateStatus status;



    private LocalDateTime donateDate; // 기부 시간

    // == 연관관계 매서드 ==//

    public void setDonate_user(@Nullable User user) {
        this.donate_user = user;
        assert user != null;
        user.getDonateList().add(this);
    }

    public void setPosts(@Nullable Posts posts) {
        this.posts = posts;
        assert posts != null;
        posts.setDonate(this);
    }

    //== 생성 메서드 == //

    public static Donate createDonate(User user, Posts posts, Delivery delivery) {
        Donate donate = new Donate();
        donate.setDonate_user(user);
        donate.setPosts(posts);
        donate.setDelivery(delivery);
        donate.setStatus(DonateStatus.ORDER);
        donate.setDonateDate(LocalDateTime.now());

        return donate;
    }

    public void cancelDonate() {
        this.setDonate_user(null);
        this.setPosts(null);
        this.setDelivery(null);
        this.setStatus(DonateStatus.CANCEL);
        this.setDonateDate(LocalDateTime.now());
    }

    //==비즈니스 로직==//
    /**
     * 기부 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMPLETE) {
            throw new IllegalStateException("이미 완료된 상품입니다.");
        }

        this.setStatus(DonateStatus.CANCEL);
        this.cancelDonate();
    }



}
