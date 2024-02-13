package com.sparta.springprepare.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordHistory extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uesr_id")
    private User user;

    public PasswordHistory(String password, User user) {
        this.password = password;
        this.user = user;
    }
}
