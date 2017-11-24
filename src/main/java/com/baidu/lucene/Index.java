package com.baidu.lucene;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * Created by dllo on 17/11/24.
 */
public class Index {

    /**
     * 使用Lucene做文件索引
     */
    public void index() {

        IndexWriter indexWriter = null;
        // 创建索引的目录对象
        try {
            FSDirectory directory = FSDirectory.open(FileSystems.getDefault()
                    .getPath("/Users/dllo/Documents/Lucene/Solr_Day1/index"));

            // 创建分词器(通用分词器:StandardAnalyzer   汉字分词器: HanLPAnalyzer)
//            Analyzer analyzer = new StandardAnalyzer();
            Analyzer analyzer = new HanLPAnalyzer();

            //创建写入索引对象的配置
            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            // 创建写入索引对象, 需要传入索引的保存路径和分词器配置
            indexWriter = new IndexWriter(directory, config);

            // 写入之前清除之前的所有索引, TODO: 实际使用时注意删除这一行
            indexWriter.deleteAll();

            // 获取要进行索引的文件
            File dirFile = new File("/Users/dllo/Documents/Lucene/Solr_Day1/data");

            // 获取目录下的所有txt文件
            File[] txtFile = dirFile.listFiles();

            for (File file : txtFile) {
                // 将每个文件写入到索引中
                Document doc = new Document();

                /*
                Field构造函数的三个参数:
                    name : 自定义的 key 值, 为了方便搜索时确定范围
                    value: 写入需要写入索引的内容
                    type : 是否需要持久化
                 */
                doc.add(new Field("content", FileUtils.readFileToString(file, "utf-8"), TextField.TYPE_STORED));
                doc.add(new Field("fileName", file.getName(), TextField.TYPE_STORED));

                // 写入
                indexWriter.addDocument(doc);

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (indexWriter != null) {
                    indexWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
