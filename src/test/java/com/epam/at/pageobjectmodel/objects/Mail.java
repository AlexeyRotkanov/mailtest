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

    public Mail(MailBuilder builder) {
        this.addressTo = builder.addressTo;
        this.subject = builder.subject;
        this.body = builder.body;
    }

    public static class MailBuilder {
        private String addressTo;
        private String subject;
        private String body;

        public MailBuilder(String addressTo) {
            this.addressTo = addressTo;
        }

        public MailBuilder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public MailBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public Mail build() {
            Mail mail = new Mail(this);
            return mail;
        }
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
