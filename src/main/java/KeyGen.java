import jdk.jfr.internal.Logger;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGen {

    KeyPairGenerator keyPairGenerator;

    public KeyPair generateKeyPair(){
        if (keyPairGenerator == null) {
            try {
                keyPairGenerator = KeyPairGenerator.getInstance("DSA");
                keyPairGenerator.initialize(2048);
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.toString());
            }
        }
        return keyPairGenerator.generateKeyPair();
    }
}
