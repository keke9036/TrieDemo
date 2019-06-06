package com.tyrian.smartlist.trie;

import java.io.Serializable;
import java.util.List;

/**
 * @author yinweitao <yinweitao@oppo.com>
 * @since 2019-06-06
 */
public class SmartList implements Serializable {

    private static final long serialVersionUID = -736725652558025286L;
    /**
     * 词语列表
     */
    private List<String> words;

    /**
     * 是否黑名单
     */
    private boolean isBlack;

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }
}
