import java.util.PriorityQueue;

public class HuffmanCoding implements HuffmanInterface {

    private class HuffmanNode implements Comparable<HuffmanNode> {
        int count;
        char value;
        HuffmanNode left;
        HuffmanNode right;

        public HuffmanNode(int count, char value) {
            this.count = count;
            this.value = value;
            left = right = null;
        }

        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            this.left = left;
            this.right = right;
            this.count = left.count + right.count;
            this.value = 0;
        }

        @Override
        public int compareTo(HuffmanNode o) {
            return this.count - o.count;
        }
    }

    PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
    HuffmanNode root;
    String[] codes = new String[256];


    @Override
    public String decode(String codedMessage) {
        StringBuilder result = new StringBuilder();
        HuffmanNode current = root;

        for (int i = 0; i < codedMessage.length(); i++) {
            char c = codedMessage.charAt(i);

            HuffmanNode next = (c == '0') ? current.left : current.right;
            current = next;
            if (current == null) {
                throw new IllegalArgumentException("Illegal code");
            }

            if (current.left == null && current.right == null) {
                result.append(current.value);
                current = root;
            }
        }

        return result.toString();
    }

    @Override
    public String encode(String message) {
        int[] counts = new int[256];

        for (char c : message.toCharArray()) {
            counts[c]++;
        }

        for(char c = 0; c < counts.length; c++) {
            if (counts[c] > 0) {
                HuffmanNode huffmanNode = new HuffmanNode(counts[c], c);
                priorityQueue.add(huffmanNode);
            }
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode composite = new HuffmanNode(left, right);
            priorityQueue.add(composite);
        }

        HuffmanNode root = priorityQueue.poll();

        generateCodes(root,"");

        StringBuilder sb = new StringBuilder();
        for(char c : message.toCharArray()) {
            sb.append(codes[c]);
        }

        return sb.toString();
    }

    void generateCodes(HuffmanNode node, String code) {
        if(node.left ==null && node.right == null) {
            codes[node.value] = code;
            return;
        }
        if(node.left != null) {
            generateCodes(node.left, code + "0");
        }
        if(node.right != null) {
            generateCodes(node.right, code + "1");
        }
    }

}

/**Name is Kyle Bertrand
 *
 *        codes
 * k 1    0111
 * y 1    1011
 * l 1    010
 * e 2    100
 * b 1    1101
 * t 1    1100
 * r 2    111
 * a 1    000
 * n 1    001
 * d 1    0110
 * _ 1    1010
 *
 *                 13
 *        5                 8
 *    2     3            4      4
 * a   n   l   2       e  2    2   r
 *            d k        _ y  t B
 * */