package com.present.GifticBe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Donate donate;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // DONATE, BENEFIT

}
