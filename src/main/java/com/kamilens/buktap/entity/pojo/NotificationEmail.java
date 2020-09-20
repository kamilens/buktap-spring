package com.kamilens.buktap.entity.pojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;

}
