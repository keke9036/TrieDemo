package com.tyrian.smartlist.trie;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author yinweitao <yinweitao@oppo.com>
 * @since 2019-06-05
 */
public class Trie {

    private TrieNode root;

    // 是否最长匹配
    private boolean isMaxMatch = false;

    public Trie() {
        root = new TrieNode();
    }

    public void addNode(String word) {
        Preconditions.checkArgument(StringUtils.isNotBlank(word));

        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            current = current.getChildren().computeIfAbsent(ch, c -> new TrieNode());
        }
        current.setWord(true);
        current.setValue(word);
    }

    /**
     * @param text 输入的文本
     * @return 匹配的敏感词列表
     */
    public List<String> queryWords(String text) {
        Preconditions.checkArgument(StringUtils.isNotBlank(text));

        List<String> matchedList = Lists.newArrayList();
        int matchTextPointer = 0; // 文字匹配的指针
        int currentTextPointer = 0;   // 遍历文本的指针
        TrieNode currentNode = root;

        while (currentTextPointer < text.length()) {
            char ch = text.charAt(matchTextPointer);
            currentNode = currentNode.getChild(ch);

            if (currentNode == null) {
                // Trie树没有匹配到敏感词，文本指针右移一位
                currentTextPointer++;
                matchTextPointer = currentTextPointer;

                currentNode = root;

            } else if (currentNode.isWord()) {
                matchTextPointer++;
                currentTextPointer = matchTextPointer;
                matchedList.add(currentNode.getValue());

                currentNode = root;

            } else {
                matchTextPointer++;
            }
        }

        return matchedList;
    }

    public boolean deleteNode(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        return deleteNode(root, word, 0);
    }

    private boolean deleteNode(TrieNode current, String word, int index) {
        if (word == null || word.length() == 0) {
            return false;
        }

        if (index == word.length()) {
            if (!current.isWord()) {
                return false;
            }
            current.setWord(false);
            return current.getChildren().isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode node = current.getChildren().get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = deleteNode(node, word, index + 1) && !node.isWord();

        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(ch);
            return current.getChildren().isEmpty();
        }
        return false;
    }

    public boolean isMaxMatch() {
        return isMaxMatch;
    }

    public void setMaxMatch(boolean maxMatch) {
        isMaxMatch = maxMatch;
    }
}
