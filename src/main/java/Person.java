import lombok.Getter;

import java.security.*;

public class Person {
    @Getter
    private final String name;

    private final Long amountOfMoney;

    private KeyPair keyPair;

    public Person(String name, Long amountOfMoney) {
        this.name = name;
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

    public Transaction createTransactionToPersonWithAmount(Person to, Long amount) {
        if (amountOfMoney < amount) {
            throw new IllegalArgumentException("You can't spend more than you have!");
        } else {
            return new Transaction(name, to.getName(), amount, keyPair.getPrivate());
        }
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

}
