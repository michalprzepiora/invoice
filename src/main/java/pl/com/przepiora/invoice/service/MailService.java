package pl.com.przepiora.invoice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.com.przepiora.invoice.mail.SimpleMessageFactory;

@Service
@Slf4j
public class MailService {
    private JavaMailSender javaMailSender;
    private SimpleMessageFactory simpleMessageFactory;

    public MailService(JavaMailSender javaMailSender, SimpleMessageFactory simpleMessageFactory) {
        this.javaMailSender = javaMailSender;
        this.simpleMessageFactory = simpleMessageFactory;
    }

    public boolean sendActivationLink(String email, String token) {
        try {
            javaMailSender.send(simpleMessageFactory.getActivationLinkMessage(email, token));
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            log.debug("Mail was not send, because: {}", e.getLocalizedMessage());
        }
        return false;
    }
}
