package com.tyrian.smartlist.trie;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinweitao <yinweitao@oppo.com>
 * @since 2019-06-05
 */
@Data
public class TrieNode {

    private Map<Character, TrieNode> children = new HashMap<>();

    private boolean isWord;

    private String value;

    public TrieNode getChild(Character key) {
        return children.get(key);
    }

}
