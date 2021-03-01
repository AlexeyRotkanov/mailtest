package com.epam.at.pageobjectmodel.decorators;

public class MailDataWithCopy extends AdvancedMailData {

    private String addressCopy;

    public MailDataWithCopy(MailData mail, String addressCopy) {
        super(mail);
        this.addressCopy = addressCopy;
    }

    public String getMailAddress() {
        return mail.getMailAddress();
    }

    public String getMailSubject() {
        return mail.getMailSubject();
    }

    public String getMailBody() {
        return mail.getMailBody();
    }

    public String getMailCopy() {
        return addressCopy;
    }

    public void writeMail() {
        System.out.println(String.format("Create a mail with 'Address to': %s; 'Subject': %s, 'Body': %s, 'Copy': %s",
                mail.getMailAddress(), mail.getMailBody(), mail.getMailBody(), addressCopy));
    }
}
