package com.epam.at.pageobjectmodel.decorators;

public abstract class AdvancedMailData implements MailData {

    protected MailData mail;

    public AdvancedMailData(MailData mail) {
        this.mail = mail;
    }
}
