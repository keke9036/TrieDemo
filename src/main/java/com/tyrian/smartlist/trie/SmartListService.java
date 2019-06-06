package com.tyrian.smartlist.trie;

import com.google.common.base.Preconditions;
import com.tyrian.util.PrintUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author yinweitao <yinweitao@oppo.com>
 * @since 2019-06-05
 */
public class SmartListService {

    private Trie trie;

    public SmartListService() {
        trie = new Trie();
    }

    public void loadList(String filePath) {

        Long begin = System.currentTimeMillis();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("Load file not exists: " + filePath);
            return;
        }

        try {
            System.out.println(
                "Load file {" + path.getFileName() + "} size: " + PrintUtils.getHumanReadableSize(Files.size(path)));
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            addWords(allLines);
        } catch (Exception e) {
            System.out.println("Load file exception: " + filePath);
        }

        System.out
            .println("Load list {" + path.getFileName() + "} costs: " + (System.currentTimeMillis() - begin) + " ms");
    }

    public void addWord(String word) {
        trie.addNode(word);
    }

    public void addWords(List<String> words) {
        words.forEach(this::addWord);
    }

    public void deleteWord(String word) {
        trie.deleteNode(word);

    }

    public void deleteWords(List<String> words) {
        words.forEach(this::deleteWord);
    }

    public List<String> search(String text) {
        Preconditions.checkArgument(StringUtils.isNotBlank(text));

        long begin = System.currentTimeMillis();
        List<String> words = trie.queryWords(text);

        System.out.println("query word costs: " + (System.currentTimeMillis() - begin) + " ms");
        return words;
    }

    public static void main(String[] args) {
        SmartListService smartListService = new SmartListService();
       
       smartListService.addWord("操你");
       smartListService.addWord("操你妈");
       smartListService.addWord("公主包厢");

        List<String> result = smartListService.search("操你妈，走去公主包厢");
        if (result != null && !result.isEmpty()) {
            System.out.println("Matched sensitive word: " + result);
        }
    }
}
