import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.*;

@Getter
public class Transaction {
    private final String from;
    private final String to;
    private final Long amount;
    private final byte[] signature;

    public Transaction(String from, String to, Long amount, PrivateKey privateKey) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.signature = signTransaction(privateKey);
    }

    private byte[] toHash() {
        String dataToHash = from + to + amount.toString();
        MessageDigest digest;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
        StringBuilder buffer = new StringBuilder();
        assert bytes != null;
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString().getBytes();
    }


    public byte[] signTransaction(PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withDSA");
            sign.initSign(privateKey);
            sign.update(toHash());
            return sign.sign();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public boolean isValid(PublicKey publicKey) {
        try {
            Signature signatureToVerify = Signature.getInstance("SHA256withDSA");
            signatureToVerify.initVerify(publicKey);
            signatureToVerify.update(toHash());
            return signatureToVerify.verify(signature);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

}
