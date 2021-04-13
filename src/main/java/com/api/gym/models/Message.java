package com.api.gym.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "message")
@Table(name = "message", schema = "public")
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="from_user_id", nullable=false)
    private User sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="to_user_id", nullable=false)
    private User recipient;

    @Column(name = "message")
    String message;

    @Column(name = "status")
    String status;

}
