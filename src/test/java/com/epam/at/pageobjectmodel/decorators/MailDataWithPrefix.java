package com.epam.at.pageobjectmodel.decorators;

public class MailDataWithPrefix extends AdvancedMailData {

    public MailDataWithPrefix(MailData mail) {
        super(mail);
    }

    public String getMailAddress() {
        return mail.getMailAddress();
    }

    public String getMailBody() {
        return mail.getMailBody();
    }

    public String getMailSubject() {
        return "decorator_" + mail.getMailSubject();
    }

    public void writeMail() {
        System.out.println(String.format("Create a mail with 'Address to': %s; 'Subject': %s, 'Body': %s",
                mail.getMailAddress(), mail.getMailSubject(), mail.getMailBody()));
    }
}
