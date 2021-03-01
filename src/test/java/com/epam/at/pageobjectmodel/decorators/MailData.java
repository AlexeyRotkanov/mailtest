package com.epam.at.pageobjectmodel.decorators;

public interface MailData {

    public String getMailAddress();

    public String getMailSubject();

    public String getMailBody();

    public void writeMail();
}
