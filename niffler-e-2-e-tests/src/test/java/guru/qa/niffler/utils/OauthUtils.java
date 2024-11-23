package guru.qa.niffler.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class OauthUtils {

    private static final String CODE_VERIFIER_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
    private static final int DEFAULT_CODE_VERIFIER_LENGTH = 64;

    public static String generateCodeVerifier() {
        Random random = new Random();
        StringBuilder verifier = new StringBuilder(DEFAULT_CODE_VERIFIER_LENGTH);
        for (int i = 0; i < DEFAULT_CODE_VERIFIER_LENGTH; i++) {
            int index = random.nextInt(CODE_VERIFIER_CHARSET.length());
            verifier.append(CODE_VERIFIER_CHARSET.charAt(index));
        }
        return verifier.toString();
    }

    public static String generateCodeChallenge(String codeVerifier) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
