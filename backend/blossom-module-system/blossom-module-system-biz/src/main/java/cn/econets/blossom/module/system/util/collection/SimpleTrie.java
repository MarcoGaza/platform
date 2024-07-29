package cn.econets.blossom.module.system.util.collection;

import cn.hutool.core.collection.CollUtil;

import java.util.*;

/**
 * Based on prefix tree，Realize the verification of sensitive words
 * <p>
 * Compared to Apache Common Provided PatriciaTrie Let's say，Performance may be better。
 *
 */
@SuppressWarnings("unchecked")
public class SimpleTrie {

    /**
     * The corresponding word after a sensitive word key
     */
    private static final Character CHARACTER_END = '\0';

    /**
     * Use sensitive words，Constructed prefix tree
     */
    private final Map<Character, Object> children;

    /**
     * String-based，Build prefix tree
     *
     * @param strs String array
     */
    public SimpleTrie(Collection<String> strs) {
        // Sort，Prefer shorter prefixes
        strs = CollUtil.sort(strs, String::compareTo);
        // Build tree
        children = new HashMap<>();
        for (String str : strs) {
            Map<Character, Object> child = children;
            // Traverse each character
            for (Character c : str.toCharArray()) {
                // If the end has been reached，There is no need to add longer sensitive words。
                // For example，There are two sensitive words：Let's eat、Eat。Enter a sentence “I want to eat”，As long as it matches “Eat” This sensitive word is enough。
                if (child.containsKey(CHARACTER_END)) {
                    break;
                }
                if (!child.containsKey(c)) {
                    child.put(c, new HashMap<>());
                }
                child = (Map<Character, Object>) child.get(c);
            }
            // End
            child.put(CHARACTER_END, null);
        }
    }

    /**
     * Verify whether the text is legal，Does not contain sensitive words
     *
     * @param text Text
     * @return Yes true-Legal false-Illegal
     */
    public boolean isValid(String text) {
        // Traverse text，Use every one [i, n) Segment string，Use children Prefix tree matching，Whether it contains sensitive words
        for (int i = 0; i < text.length(); i++) {
            Map<Character, Object> child = (Map<Character, Object>) children.get(text.charAt(i));
            if (child == null) {
                continue;
            }
            boolean ok = recursion(text, i + 1, child);
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verify that the text starts from the specified position，Whether it does not contain a sensitive word
     *
     * @param text  Text
     * @param index Starting position
     * @param child Node（Currently traversed）
     * @return Whether not included true-Not included false-Include
     */
    private boolean recursion(String text, int index, Map<Character, Object> child) {
        if (child.containsKey(CHARACTER_END)) {
            return false;
        }
        if (index == text.length()) {
            return true;
        }
        child = (Map<Character, Object>) child.get(text.charAt(index));
        return child == null || !child.containsKey(CHARACTER_END) && recursion(text, ++index, child);
    }

    /**
     * Get the illegal sensitive words contained in the text
     *
     * Attention，The shortest matching principle。For example：When sensitive words exist “Stupid”，“Stupid idiot ”Time，will only return “Stupid”。
     *
     * @param text Text
     * @return Matched sensitive words
     */
    public List<String> validate(String text) {
        Set<String> results = new HashSet<>();
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Map<Character, Object> child = (Map<Character, Object>) children.get(c);
            if (child == null) {
                continue;
            }
            StringBuilder result = new StringBuilder().append(c);
            boolean ok = recursionWithResult(text, i + 1, child, result);
            if (!ok) {
                results.add(result.toString());
            }
        }
        return new ArrayList<>(results);
    }

    /**
     * Return text from index Sensitive words at the beginning，and use StringBuilder Parameters are returned
     *
     * Logical AND {@link #recursion(String, int, Map)} It is consistent，Just more result Return results
     *
     * @param text   Text
     * @param index  Beginning unknown
     * @param child  Node（Currently traversed）
     * @param result Return sensitive words
     * @return Are there sensitive words?
     */
    @SuppressWarnings("unchecked")
    private static boolean recursionWithResult(String text, int index, Map<Character, Object> child, StringBuilder result) {
        if (child.containsKey(CHARACTER_END)) {
            return false;
        }
        if (index == text.length()) {
            return true;
        }
        Character c = text.charAt(index);
        child = (Map<Character, Object>) child.get(c);
        if (child == null) {
            return true;
        }
        if (child.containsKey(CHARACTER_END)) {
            result.append(c);
            return false;
        }
        return recursionWithResult(text, ++index, child, result.append(c));
    }

}
