package com.zzy.translation.service;

import com.zzy.translation.entity.Word;

public interface WordService {
    /**
     * 翻译单词的接口
     * @param query
     * @return
     */
    boolean translationWord(String query);
    /**
     * 将查询出来的单词插入到数据库中
     * @param word
     * @return
     */
    boolean addWord(Word word);
}