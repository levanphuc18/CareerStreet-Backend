
package com.careerstreet.notification_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "msgBody")
    private String msgBody;

    @Column(name = "status")
    private String status;
}
