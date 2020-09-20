package com.kamilens.buktap.service;

import com.kamilens.buktap.entity.pojo.NotificationEmail;

public interface MailService {

    void sendMail(NotificationEmail notificationEmail);

}
