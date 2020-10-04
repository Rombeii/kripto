import lombok.Getter;
import lombok.Setter;
import org.cryptacular.util.NonceUtil;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Block {

    private String rootValue;

    @Setter
    private String previousHash;

    private final List<Transaction> transactions;

    private final Timestamp timeStamp;

    private String nonce;

    public Block(Map<Transaction, PublicKey> transactionsToValidate) {
        this.timeStamp = new Timestamp(System.currentTimeMillis());
        this.transactions = getAllValidTransactions(transactionsToValidate);
        getMerkleRootValue(transactions.stream()
                                       .map(t -> Util.getHash(t.getDataToHash()))
                                       .collect(Collectors.toList()));
        System.out.println(String.format("Block creation started with %d transactions, %d transactions were found valid.",
                transactionsToValidate.size(), transactions.size()));
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

    public void mine() {
        System.out.println("\tMining operation started");
        while (!isNonceValid()) {
            nonce = new String(NonceUtil.randomNonce(32), StandardCharsets.US_ASCII);
        }
        System.out.println("\tNonce found, the hash is: " + generateBlockHash());
    }

    public boolean isNonceValid() {
        return startWithNumberOfZeros(generateBlockHash(), 5);
    }

    private boolean startWithNumberOfZeros(String str, int numberOfZeros) {
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < numberOfZeros; i++ )
            sb.append("0");
        return str.startsWith(sb.toString());
    }

    public String generateBlockHash() {
        return Util.getHash(rootValue + nonce);
    }
}
