import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        Wallet alice = new Wallet("Alice", 500L);
        Wallet bob = new Wallet("Bob", 300L);
        Wallet charlie = new Wallet("Charlie", 200L);

        Map<Transaction, PublicKey> block1Transactions = new HashMap<>();
        Transaction a = alice.createTransactionToPersonWithAmount(bob, 20L);


        block1Transactions.put(alice.createTransactionToPersonWithAmount(bob, 20L), alice.getPublicKey());
        block1Transactions.put(alice.createTransactionToPersonWithAmount(bob, 10L), alice.getPublicKey());
        block1Transactions.put(alice.createTransactionToPersonWithAmount(charlie, 40L), alice.getPublicKey());
        block1Transactions.put(bob.createTransactionToPersonWithAmount(charlie, 50L), bob.getPublicKey());
        block1Transactions.put(charlie.createTransactionToPersonWithAmount(alice, 10L), charlie.getPublicKey());
        block1Transactions.put(charlie.createTransactionToPersonWithAmount(bob, 40L), charlie.getPublicKey());

        block1Transactions.put(bob.createTransactionToPersonWithAmount(alice, 70L), alice.getPublicKey());

        Block block1 = new Block("", block1Transactions);

    }
}
