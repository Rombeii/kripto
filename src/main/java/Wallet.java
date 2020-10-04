import lombok.Getter;

import java.security.*;

public class Wallet {
    @Getter
    private final String owner;
    private Long amountOfMoney;
    private KeyPair keyPair;

    public Wallet(String owner, Long amountOfMoney) {
        this.owner = owner;
        this.amountOfMoney = amountOfMoney;
        generateKeyPair();
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            keyPairGenerator.initialize(2048);
            this.keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Transaction createTransactionToPersonWithAmount(Wallet to, Long amount) {
        if (amountOfMoney < amount) {
            throw new IllegalArgumentException("You can't spend more than you have!");
        } else {
            amountOfMoney -= amount;
            System.out.printf("A new Transaction was created: %s sent %s %d coins.%n",
                    owner, to.getOwner(), amount);
            return new Transaction(this, to, amount, keyPair.getPrivate());
        }
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public void addMoney(Long amountToAdd) {
        amountOfMoney += amountToAdd;
    }
}
