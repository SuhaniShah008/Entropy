# Huffman Encoding & Entropy Compression (Java)

This project implements **Huffman Coding**, a classic lossless compression algorithm.  
The program reads a text file, counts character frequencies, builds a Huffman Tree, generates binary encodings, and outputs the compressed bit pattern.

---

## Project Files

### **Entropy.java**
Main program containing all functionality:

- Reads input text from `file.txt`
- Counts character frequencies
- Builds a Huffman Tree using a `PriorityQueue`
- Generates binary encodings for each character
- Displays:
  - Character frequency count  
  - Huffman encoding map  
  - Compressed binary output  
- Compresses the file’s content into a single bit string

---

### **Node.java**
Used internally by the Huffman algorithm.

A `Node` contains:

- `char ch` → the character (for leaf nodes)  
- `int freq` → frequency of occurrence  
- `Node left, right` → child references  
- Implements `Comparable<Node>` so `PriorityQueue` orders by frequency  

Two types of nodes:

1. **Leaf Node:**  
   Created by `Node(char ch, int freq)`

2. **Internal Node:**  
   Created by combining two child nodes:  
   `Node(Node left, Node right)`

---

## How Huffman Encoding Works

1. **Count Frequencies**  
   Each character’s occurrences are stored in `frequencyMap`.

2. **Build Priority Queue**  
   Lower-frequency characters get higher priority.

3. **Combine Nodes**  
   Repeatedly remove the two smallest nodes and merge them until one root remains.

4. **Generate Encodings**  
   Traversing the tree assigns:
   - Left edges → `"1"`
   - Right edges → `"0"`

5. **Compress Text**  
   Replace every character with its Huffman binary code.
