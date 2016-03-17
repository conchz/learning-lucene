package com.github.dolphineor.demo01;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author Baymax
 */
public class HelloLucene {

    /**
     * 创建索引
     */
    public void index() {
        // 1、创建Directory
        Directory directory = new RAMDirectory();   // 索引创建在内存中
//        try {
//            Directory directory1 = FSDirectory.open(new File(""));  // 索引创建在硬盘中
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 2、创建IndexWriter
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36));
        try (IndexWriter writer = new IndexWriter(directory, iwc)) {
            // 3、创建Document对象
            Document doc = null;
            // 4、为Document添加Field
            File file = Paths.get("").toFile();
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                if (Objects.nonNull(files)) {
                    for (File _file : files) {
                        doc = new Document();
                        doc.add(new Field("content", new FileReader(_file)));
                        doc.add(new Field("filename", _file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                        doc.add(new Field("path", _file.getAbsolutePath(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                        // 5、通过IndexWriter添加文档到索引中
                        writer.addDocument(doc);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
