package com.careerstreet.notification_service.repository;

import com.careerstreet.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, String> {
}
