import java.util.*;

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

        boolean isLeaf(){return left == null && right == null;}
    }


    class HuffmanCode{
        String code;
        char value;
        public HuffmanCode(char value, String code){
            this.code = code;
            this.value = value;
        }
    }

    List<HuffmanCode> codes = new ArrayList<HuffmanCode>();
    PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
    HuffmanNode root;




    @Override
    public String decode(String codedMessage) {
        if (codedMessage == null || codedMessage.length() == 0 || root==null) {
            return "";
        }

        StringBuilder decodedMessage = new StringBuilder();
        HuffmanNode node = root;

        for(char c : codedMessage.toCharArray()) {
            if (c == '0'){
                node = node.left;
            }
            if (c == '1'){
                node = node.right;
            }
            if(node.isLeaf()){
                decodedMessage.append(node.value);
                node = root;
            }
        }

        return decodedMessage.toString();
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

        this.root = priorityQueue.poll();

        String encodedMessage = "";
        generateCodes(root,"");
        for(char c : message.toCharArray()) {
            encodedMessage += findCodes(c);
        }

        return encodedMessage;
    }


    String findCodes(char c) {
        for(HuffmanCode code : codes){
            if(code.value == c){
                return code.code;
            }
        }
        return "";
    }


    void generateCodes(HuffmanNode node, String code) {
        if (node == null) { return;}
        if (node.isLeaf()) {
            //reached a leaf save the code
            codes.add(new HuffmanCode(node.value, code));
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");

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