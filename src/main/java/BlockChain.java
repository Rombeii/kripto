import java.util.ArrayList;
import java.util.List;

public class BlockChain {
    List<Block> blocks;

    public BlockChain(Block block) {
        blocks = new ArrayList<>();
        block.setPreviousHash("");
        blocks.add(block);
        System.out.printf("Blockchain was created with a block with %d transactions.%n",
                block.getTransactions().size());
    }

    public void addBlock(Block block) {
        if(block.isNonceValid()) {
            block.setPreviousHash(blocks.get(blocks.size() - 1).generateBlockHash());
            blocks.add(block);
            System.out.printf("A Block was added to the blockchain with %s transactions.%n",
                    block.getTransactions().size());
        }
    }

    public boolean isValid() {
        boolean isValid = blocks.get(0).isNonceValid();
        for (int i = 1; i < blocks.size(); i++) {
            Block currentBlock = blocks.get(i);
            if (!currentBlock.getPreviousHash().equals(blocks.get(i - 1).generateBlockHash())
                || !currentBlock.isNonceValid()) {
                return false;
            }
        }
        return isValid;
    }
}
