package com.baidu.lucene;

/**
 * Created by dllo on 17/11/24.
 */
public class MainTest {

    public static void main(String[] args) {

        Index index = new Index();

        index.index();

        Search search = new Search();
        search.search("分布式");

    }
}
