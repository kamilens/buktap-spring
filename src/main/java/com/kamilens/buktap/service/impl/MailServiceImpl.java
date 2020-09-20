package com.kamilens.buktap.service.impl;

import com.kamilens.buktap.entity.pojo.NotificationEmail;
import com.kamilens.buktap.service.MailContentBuilder;
import com.kamilens.buktap.service.MailService;
import com.kamilens.buktap.web.rest.error.MailSenderException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    @Value("${buktap.emails.no-reply}")
    private String noReplyEmail;

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    private static final class MailServiceException extends MailSenderException {
        public MailServiceException(String recipient) {
            super("Exception occurred when sending mail to" + recipient);
        }
    }

    @Async
    @Override
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparer = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(noReplyEmail);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            mailSender.send(messagePreparer);
        } catch (MailException e) {
            throw new MailServiceException(notificationEmail.getRecipient());
        }
    }

}
