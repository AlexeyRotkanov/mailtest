package com.epam.at.pageobjectmodel.objects;

import com.epam.at.pageobjectmodel.tools.GenerateDefaultMailData;

public class Mail {

    private String addressTo;
    private String subject;
    private String body;

    public Mail() {
        addressTo = "alex.r.epm@gmail.com";
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
