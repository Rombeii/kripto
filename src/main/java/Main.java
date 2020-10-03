import org.apache.commons.lang3.RandomStringUtils;

import java.security.Key;
import java.security.KeyPair;
import java.security.Signature;

public class Main {

    //TODO rendes logolás bevezetése
    //TODO tranzakcio -> Merkel fa
    //TODO ellenorzes a fahoz
    //TODO nounce
    //TODO hash

    public static void main(String[] args) {
        KeyGen keyGen = new KeyGen();
        KeyPair elso = keyGen.generateKeyPair();
        byte[] tranzakcio = generateRandomTransaction();
        byte[] alairas = signTransaction(elso, tranzakcio);
        verifySignature(elso, tranzakcio, alairas);
        KeyPair masodik = keyGen.generateKeyPair();
    }

    private static byte[] generateRandomTransaction() {
        return RandomStringUtils.random(123, true, true).getBytes();
    }

    private static byte[] signTransaction(KeyPair keys, byte[] transaction) {
        try {
            Signature sign = Signature.getInstance("SHA256withDSA");
            sign.initSign(keys.getPrivate());
            sign.update(transaction);
            return sign.sign();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    private static byte[] signTransaction(KeyPair keys) {
        return signTransaction(keys, generateRandomTransaction());
    }

    private static boolean verifySignature(KeyPair keyPair, byte[] transaction, byte[] signatureToVerify) {
        try {
            Signature signature = Signature.getInstance("SHA256withDSA");
            signature.initVerify(keyPair.getPublic());
            signature.update(transaction);
            return signature.verify(signatureToVerify);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

}
