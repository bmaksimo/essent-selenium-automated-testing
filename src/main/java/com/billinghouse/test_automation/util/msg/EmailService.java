package com.billinghouse.test_automation.util.msg;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class EmailService {

    public static void main(String[] args) {
        new EmailService().createTestReport(null);
    }

    private static final Logger logger = Logger.getLogger(EmailService.class);

    private static final String PATH = "/email/";
    private static final String OUTPUT = "/html/";
    private static final String NUAT5019_TEMPLATE = "nuat5019_template.tpl";
    private static final String NUAT5019_result = "nuat-5019-result.html";

    public boolean createTestReport(Map input) {
        String messageBody = getMessageFromTemplate(input);
        if (StringUtils.isNotBlank(messageBody)) writeToFile(messageBody);
        return sendEmailNow(getJavaMailSender(), messageBody);
    }

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(ConfigProvider.getProperty(ConfigKey.ESSENT_SMTP_HOST));
        mailSender.setUsername(ConfigProvider.getProperty(ConfigKey.ESSENT_SMTP_USER));
        mailSender.setPassword(ConfigProvider.getProperty(ConfigKey.ESSENT_SMTP_PASSWORD));
        Integer port = Integer.parseInt(ConfigProvider.getProperty(ConfigKey.ESSENT_SMTP_PORT));
        mailSender.setPort(port);

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

    private void writeToFile(String content) {
        try {
            Files.write(Paths.get(ResourceUtil.toPath(OUTPUT + NUAT5019_result)), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Done writing file to " + OUTPUT + NUAT5019_result);
    }

    private boolean sendEmailNow(JavaMailSender emailSender, String messageBody) {
        try {
            if (null != emailSender) {
                MimeMessage message = getEmailMessage(messageBody, emailSender);
                logger.info("Sending email report to SME...");
                emailSender.send(message);
                logger.info("Done.");

                return true;
            }
            logger.warn("Could not find the email sender. Please check if email properties are properly set.");
            return false;
        } catch (MessagingException e) {
            logger.error("Something went wrong while sending an email. Please check if your email server is up and running.");
            return false;
        }
    }

//    private MimeMessage getEmailMessage(String messageBody, JavaMailSender emailSender) throws MessagingException {
//        MimeMessage mimeMailMessage = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
//        helper.setFrom("noreply@essent.be");
//        helper.setTo("sme@essent.be");
//        helper.setSubject("NUAT-5019 Scenario Execution");
//        helper.setText(messageBody);
//
//        return mimeMailMessage;
//    }
//
//    private JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.essent.be");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("essentmailer@essent.be");
//        mailSender.setPassword("pass1234");
//
//        return mailSender;
//    }

    private MimeMessage getEmailMessage(String messageBody, JavaMailSender emailSender) throws MessagingException {
        MimeMessage mimeMailMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
        helper.setFrom(ConfigProvider.getProperty(ConfigKey.ESSENT_EMAIL_FROM));
        helper.setTo(ConfigProvider.getProperty(ConfigKey.ESSENT_EMAIL_TO));
        helper.setSubject("NUAT-5019 Scenario Execution");
        helper.setText(messageBody);

        return mimeMailMessage;
    }

}

