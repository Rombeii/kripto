import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

@Getter
public class Block {
    private String hash;
    private String previousHash;
    private byte[] transaction;
    private long timeStamp;
    private byte[] digitalisAlairas;
    private int nonce;

    public Block(String transaction, String previousHash, long timeStamp, Signature digitalisAlairas) throws Exception {
        this.transaction = transaction;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.digitalisAlairas = digitalisAlairas;
        this.hash = hashGeneralas();
    }

    public String hashGeneralas() throws Exception{
        String dataToHash = previousHash + timeStamp + nonce + transaction;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        StringBuilder buffer = new StringBuilder();
        assert bytes != null;
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }
}
