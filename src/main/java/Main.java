import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Person alice = new Person("Alice", 500L);
        Person bob = new Person("Bob", 300L);
        Person charlie = new Person("Charlie", 200L);

        Block block1 = new Block("");
        block1.addTransaction(alice.createTransactionToPersonWithAmount(bob, 10L), alice.getPublicKey());
        block1.addTransaction(alice.createTransactionToPersonWithAmount(bob, 20L), alice.getPublicKey());
        block1.addTransaction(bob.createTransactionToPersonWithAmount(alice, 40L), bob.getPublicKey());
        block1.addTransaction(bob.createTransactionToPersonWithAmount(charlie, 50L), bob.getPublicKey());
        block1.addTransaction(charlie.createTransactionToPersonWithAmount(alice, 80L), charlie.getPublicKey());
        block1.addTransaction(charlie.createTransactionToPersonWithAmount(bob, 100L), charlie.getPublicKey());
    }

    private static byte[] generateRandomTransaction() {
        return RandomStringUtils.random(123, true, true).getBytes();
    }
}
