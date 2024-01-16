package com.kuroko.heathyapi.feature.water;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.kuroko.heathyapi.feature.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Water {
    @Id
    private long id;
    private double amount;

    @CreationTimestamp
    private Date createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
