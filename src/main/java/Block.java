import lombok.Getter;

import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Block {
    private String rootValue;
    private String previousHash;
    private List<Transaction> transactions;
    private Timestamp timeStamp;
    private int nonce;

    public Block(String previousHash, Map<Transaction, PublicKey> transactionsToValidate) {
        this.previousHash = previousHash;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
        this.transactions = getAllValidTransactions(transactionsToValidate);
        getMerkleRootValue(transactions.stream()
                                       .map(t -> Util.getHash(t.getDataToHash()))
                                       .collect(Collectors.toList()));
    }

    private List<Transaction> getAllValidTransactions(final Map<Transaction, PublicKey>  transactionsToValidate) {
        return transactionsToValidate.keySet()
                .stream()
                .filter(t -> t.isValid(transactionsToValidate.get(t)))
                .collect(Collectors.toList());
    }

    private void getMerkleRootValue(List<String> hashes) {
        if (hashes.size() == 1) {
            rootValue = hashes.get(0);
        } else {
            duplicateLastValueIfNeeded(hashes);
            getMerkleRootValue(createPairs(hashes));
        }
    }

    private void duplicateLastValueIfNeeded(List<String> hashes) {
        if (hashes.size() % 2 != 0) {
            hashes.add(hashes.get(hashes.size() - 1));
        }
    }

    private List<String> createPairs(List<String> hashes) {
        List<String> newHashes = new ArrayList<>();
        for (int i = 0; i < hashes.size() - 1; i += 2) {
            newHashes.add(Util.getHash(hashes.get(i).concat(hashes.get(i + 1))));
        }
        return newHashes;
    }
}
