package com.example.project.services.firebase;

import com.example.project.models.dtos.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PushNotificationService {
    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    @Autowired
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @Async
    public void sendPushNotificationToToken(PushNotificationRequest request, Map<String, String> dataPPP) {
        try {
            fcmService.sendMessageToToken(request, dataPPP);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
