package com.epam.at.pageobjectmodel.objects;

import com.epam.at.pageobjectmodel.tools.GenerateDefaultMailData;

public class Mail {

    private final String MAIL_ADDRESS_TO = "alex.r.epm@gmail.com";

    private String addressTo;
    private String subject;
    private String body;

    public Mail() {
        addressTo = MAIL_ADDRESS_TO;
        subject = GenerateDefaultMailData.generateMailSubject();
        body = GenerateDefaultMailData.generateMailBody();
    }

    public Mail(String addressTo, String subject, String body) {
        this.addressTo = addressTo;
        this.subject = subject;
        this.body = body;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
