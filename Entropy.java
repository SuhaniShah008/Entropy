import java.io.FileReader; //Reads the file
import java.io.IOException; //Checks for IO exception
import java.util.Map; //Used to creating a map
import java.util.HashMap; //Used for creating a hash mamp
import java.util.PriorityQueue; //Used for creating priority queue

class Node implements Comparable<Node> {
    char ch;
    int freq;
    Node left, right;

    /*
     * pre: A character and its frequency
     * post: Initializes a leaf node with the given character and frequency
     */
    // Constructor for creating a leaf node
    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }

    /*
     * pre: There must be two child nodes
     * post: Initializes an internal node with the sum of frequencies of both children
     */
    // Constructor for creating internal node
    Node(Node left, Node right) {
        this.ch = '\0'; // Internal node
        this.freq = left.freq + right.freq;
        this.left = left;
        this.right = right;
    }

    /*
     * pre: There must be a Node object to compare
     * post: Returns a negative value if this node has lower frequency, positive if higher, and zero if equal
     */
    // Compares nodes based on frequency to help the priority queue ordering
    @Override
    public int compareTo(Node o) {
        return this.freq - o.freq;
    }
}

public class Entropy {
    private static  Map<Character, String> EncodedChar = new HashMap<>(); //stores character encoding
    private static  Map<Character, Integer> frequencyMap = new HashMap<>(); //stores character frequencies

    public static void main(String[] args) {
        String fileName = "file.txt";
        String text = readFile(fileName);

        if (text.isEmpty()) {
            System.out.println("The file is empty, please add something to it.");
            return;
        }

        countFrequencies(text);
        displayCharacterCount();

        Node root = buildHuffmanTree();
        generateEncodedChar(root, "");
        displayCharacterEncoding();

        String compressedBitPattern = compressText(text);
        System.out.println("\nCOMPRESSED BIT PATTERN:");
        System.out.println(compressedBitPattern);
    }

    /*
     * pre: filename must be a valid string
     * post: Returns the file content as a string
     */
    // Reads file content and returns it as a string using a filereader
    private static String readFile(String fileName) {
    StringBuilder content = new StringBuilder();
    try {
        FileReader fr = new FileReader(fileName);
        int ch;
        while ((ch = fr.read()) != -1) {
            content.append((char) ch);
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }
    return content.toString();
}

    /*
     * pre: text must be a not empty string
     * post: Populates frequencyMap with character counts
     */
    // Counts the frequency of each character in the text
    private static void countFrequencies(String text) {
        for (char c : text.toCharArray()) {
            //citation: geeks for geeks- java hashmap getordefault method
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);  //initialize the count or increment the frequency
        }
    }

    /*
     * pre: 
     * post: Displays character frequencies
     */
    // Displays the frequency of each character in the text
    private static void displayCharacterCount() {
        System.out.println("CHARACTER COUNT:");
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            System.out.print("[" + entry.getValue() + ":" + entry.getKey() + "] ");
        }
        System.out.println("\n");
    }

    /*
     * pre: 
     * post: Returns the root of the tree
     */
    // Builds the tree based on character frequencies
    private static Node buildHuffmanTree() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node(left, right));
        }
        return pq.poll();
    }

    /*
     * pre: node must be a tree root, code must ba string
     * post: Populates EncodedChar with codes
     */
    // Generates the encoding for each character
    private static void generateEncodedChar(Node node, String code) {
        if (node == null) return;
        
        if (node.ch != '\0') { //checks if leaf node
            EncodedChar.put(node.ch, code);
        }
        
        generateEncodedChar(node.left, code + "1");
        generateEncodedChar(node.right, code + "0");
    }

    /*
     * pre: 
     * post: Displays the character encoding map
     */
    // Displays the encoding for each character
    private static void displayCharacterEncoding() {
        System.out.println("CHARACTER ENCODING:");
        System.out.println(EncodedChar);
    }

    /*
     * pre: text must be a string
     * post: Returns the encoded text as a binary string
     */
    // Compresses the text using the encoding
    private static String compressText(String text) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(EncodedChar.get(c));
        }
        return encodedText.toString();
    }
}