package com.careerstreet.notification_service.service.implement;

import com.careerstreet.event.NotificationEvent;
import com.careerstreet.notification_service.dto.NotificationRequest;
import com.careerstreet.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
//    private String sender = "levanphuc18112001@gmail.com";

    @Override
    public String sendEmail(NotificationEvent notificationEvent){
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(notificationEvent.getRecipient());
            mailMessage.setText(notificationEvent.getMsgBody());
            mailMessage.setSubject(notificationEvent.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
    @Override
    @Async
    public CompletableFuture<String> sendEmailAsync(NotificationEvent notificationEvent){
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(notificationEvent.getRecipient());
            mailMessage.setText(notificationEvent.getMsgBody());
            mailMessage.setSubject(notificationEvent.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            String result = "Email sent successfully!";
            return CompletableFuture.completedFuture(result);
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            String result = "Error while Sending Mail";
            return CompletableFuture.completedFuture(result);
        }
    }
}
