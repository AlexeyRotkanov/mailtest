package com.epam.at.pageobjectmodel.objects;

import com.epam.at.pageobjectmodel.tools.GenerateDefaultMailData;

public class Mail implements com.epam.at.pageobjectmodel.decorators.MailData {

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

    public String getMailAddress() {
        return addressTo;
    }

    public String getMailSubject() {
        return subject;
    }

    public String getMailBody() {
        return body;
    }

    public void writeMail() {
        System.out.println(String.format("Create a mail with 'Address to': %s; 'Subject': %s, 'Body': %s",
                addressTo, subject, body));
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
