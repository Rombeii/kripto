import java.security.*;

public class Transaction {
    private final Wallet from;
    private final Wallet to;
    private final Long amount;
    private final byte[] signature;

    public Transaction(Wallet from, Wallet to, Long amount, PrivateKey privateKey) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.signature = signTransaction(privateKey);
    }


    private byte[] signTransaction(PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withDSA");
            sign.initSign(privateKey);
            sign.update(getDataToHash().getBytes());
            return sign.sign();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public String getDataToHash() {
        return from.getOwner() + to.getOwner() + amount.toString();
    }

    public boolean isValid(PublicKey publicKey) {
        try {
            Signature signatureToVerify = Signature.getInstance("SHA256withDSA");
            signatureToVerify.initVerify(publicKey);
            signatureToVerify.update(getDataToHash().getBytes());
            return signatureToVerify.verify(signature);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public void transferMoney() {
        to.addMoney(amount);
    }

    public void refundMoney() {
        from.addMoney(amount);
    }

}
