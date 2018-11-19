package com.billinghouse.test_automation.util.msg;

import com.essent.testing.util.resource.ResourceUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class EmailService {

    public static void main(String[] args) {
        new EmailService().createTestReport(null);
    }

    private static final Logger logger = Logger.getLogger(EmailService.class);

    private static final String PATH = "/email/";
    private static final String NUAT5019_TEMPLATE = "nuat5019_template.tpl";

    public boolean createTestReport(Map input) {
        JavaMailSender emailSender = getJavaMailSender();
        String messageBody = getMessageFromTemplate(input);
        try {
            MimeMessage message = getEmailMessage(messageBody, emailSender);
            logger.info("Sending email report to SME...");
            emailSender.send(message);
            logger.info("Done.");

            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    private MimeMessage getEmailMessage(String messageBody, JavaMailSender emailSender) throws MessagingException {
        MimeMessage mimeMailMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
        helper.setFrom("noreply@essent.be");
        helper.setTo("sme@essent.be");
        helper.setSubject("NUAT-5019 Scenario Execution");
        helper.setText(messageBody);

        return mimeMailMessage;
    }

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.essent.be");
        mailSender.setPort(587);

        mailSender.setUsername("essentmailer@essent.be");
        mailSender.setPassword("pass1234");

        return mailSender;
    }

    private String getMessageFromTemplate(Map<String, String> data) {
        String messageTemplatePath = ResourceUtil.toPath(PATH + NUAT5019_TEMPLATE);
        File messageTemplateFile = new File(messageTemplatePath);
        try {
            String messageBody = FileUtils.readFileToString(messageTemplateFile, Charset.defaultCharset());
            StrSubstitutor substitutor = new StrSubstitutor(data);
            messageBody = substitutor.replace(messageBody);

            return messageBody;
        } catch (IOException e) {
            logger.warn("Something went wrong while creating the email message body.");
        }

        return null;
    }
}

