import lombok.Getter;

import java.security.*;

@Getter
public class Transaction {
    private final String from;
    private final String to;
    private final Long amount;
    private final byte[] signature;

    public Transaction(String from, Wallet to, Long amount, PrivateKey privateKey) {
        this.from = from;
        this.to = to.getOwner();
        this.amount = amount;
        this.signature = signTransaction(privateKey);
        to.addMoney(amount);
    }


    private byte[] signTransaction(PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withDSA");
            sign.initSign(privateKey);
            sign.update(Util.getHash(getDataToHash()).getBytes());
            return sign.sign();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public String getDataToHash() {
        return from + to + amount.toString();
    }

    public boolean isValid(PublicKey publicKey) {
        try {
            Signature signatureToVerify = Signature.getInstance("SHA256withDSA");
            signatureToVerify.initVerify(publicKey);
            signatureToVerify.update(Util.getHash(getDataToHash()).getBytes());
            return signatureToVerify.verify(signature);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

}
