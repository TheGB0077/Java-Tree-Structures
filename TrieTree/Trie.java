
import java.util.stream.Collectors;

public class Trie {

    private final TrieNode root;
    private int size;

    public Trie() {
        root = new TrieNode();
    }

    public TrieNode put(String str, Object value) {
        //optional check
        if (isStringofLetters(str)) {
            return put(root, str, value, 0);
        }
        return null;
    }

    private static boolean isStringofLetters(String str) {
        //highly optional and not performant
        //only included to include accented letters and hyphen containing words and to exclude all other elements from ever being accepted into the Tree
        char hyphen = 45;
        return str.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()).stream().allMatch(c -> Character.isLetter(c) || c.equals(hyphen));
    }

    private TrieNode put(TrieNode node, String str, Object value, int position) {
        if (position == str.length()) {
            if (node.getValue() == null) {
                size++;
            }
            node.setValue(value);
            return node;
        } else {
            char c = str.charAt(position);
            TrieNode nextNode = node.getNext(c);
            if (nextNode == null) {
                nextNode = new TrieNode();
                node.setNext(c, nextNode);
            }
            return put(nextNode, str, value, position + 1);
        }
    }

    public Object get(String str) {
        TrieNode node = get(root, str, 0);
        if (node != null) {
            return node.getValue();
        } else {
            return null;
        }
    }

    private TrieNode get(TrieNode node, String str, int position) {
        if (position == str.length()) {
            return node;
        } else {
            char c = str.charAt(position);
            TrieNode nextNode = node.getNext(c);
            if (nextNode == null) {
                return null;
            }
            return get(nextNode, str, position + 1);
        }
    }

    public int getWordsCount() {
        int result = 0;
        return getWordsCount(root, result);
    }

    static int getWordsCount(TrieNode node, int result)
    {
        if (node.getValue() != null){
            result++;
        }

        for (int i = 0; i < TrieNode.ASCII_SIZE; i++)
            if (node.getNext()[i] != null ) {
                result = getWordsCount(node.getNext()[i], result);
            }
        return result;
    }

    public boolean contains(String str) {
        TrieNode node = get(root, str, 0);
        return node != null;
    }

    public boolean findPrefix(String str) {
        char[] data = str.toCharArray();
        TrieNode node = root;

        for (char c : data) {
            if (node.getNext(c) == null) {
                return false;
            }
            node = node.getNext(c);
        }
        return true;
    }

    public void remove(String str) {
        remove(root, str, 0);
    }

    private boolean remove(TrieNode node, String str, int position) {
        if (position == str.length()) {
            node.setValue(null);
            return true;
        } else {
            char c = str.charAt(position);
            TrieNode nextNode = node.getNext(c);
            if (nextNode == null) {
                return false;
            }
            boolean result = remove(nextNode, str, position + 1);
            if (result) {
                if (nextNode.getSize() == 0 && nextNode.getValue() == null) {
                    node.removeNext(c);
                } else {
                    result = false;
                }
            }
            return result;
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[root]");
        buildString(root, sb, 1);
        return sb.toString();
    }

    private void buildString(TrieNode node, StringBuilder sb, int level) {
        if (node.getValue() != null) {
            sb.append(node);
        }
        int i = 0;
        while (i < TrieNode.ASCII_SIZE) {
            TrieNode next = node.getNext((char) i);
            if (next != null) {
                sb.append("\n").append("  ".repeat(Math.max(0, level)));
                sb.append("(").append((char) i).append(")");
                buildString(next, sb, level + 1);
            }
            ++i;
        }
    }
}
