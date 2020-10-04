import lombok.Getter;

import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Block {
    private String hash;
    private String previousHash;
    private List<Transaction> transactions;
    private Timestamp timeStamp;
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transactionToAdd, PublicKey publicKey) {
        if (transactionToAdd.isValid(publicKey)) {
            transactions.add(transactionToAdd);
        } else {
            throw new IllegalArgumentException("The transaction is not valid");
        }
    }
}
