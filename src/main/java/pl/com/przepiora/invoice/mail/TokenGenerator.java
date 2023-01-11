package pl.com.przepiora.invoice.mail;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class TokenGenerator {
    private static final int LENGTH = 32;
    private static final String CHARACTERS = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    public String generate() {
        try {
            StringBuilder builder = new StringBuilder();
            Random random = SecureRandom.getInstanceStrong();
            for (int i = 0; i < LENGTH; i++) {
                builder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

    }
}