package pl.com.przepiora.invoice.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SimpleMessageFactory {

    @Value("${parkiva.host.url}")
    private String hostUrl;

    public SimpleMailMessage getActivationLinkMessage(String email, String token) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("info.ivoice.app@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Invoice - activation link");
        simpleMailMessage.setText("Please click link to activate: " + hostUrl + "/confirm_mail?token=" + token);
        return simpleMailMessage;
    }
}
