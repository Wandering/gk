package cn.thinkjoy.gk.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndexUtil {
	
	private IndexWriter indexWriter = null;
	
	public LuceneIndexUtil(){
		
	}
	
//	public LuceneIndexUtil(String indexPath,Version version) throws Exception {
//		//索引库路径不存在则新建一个
//        File indexFile=new File(indexPath);
//        
//        if(!indexFile.exists()) {
//        	indexFile.mkdir();
//        }
//        
//        Directory fsDirectory = FSDirectory.open(indexFile);
//        //有网后换IKAnalyzer
//        Analyzer analyzer = new StandardAnalyzer(version);  
//        IndexWriterConfig confIndex = new IndexWriterConfig(Version.LUCENE_46, analyzer);
//        confIndex.setOpenMode(OpenMode.CREATE_OR_APPEND);
//        if (IndexWriter.isLocked(fsDirectory)) {
//            IndexWriter.unlock(fsDirectory);
//        }
//        this.indexWriter =new IndexWriter(fsDirectory, confIndex);
//	}
	
	public IndexWriter getIndexWriter(){
        return this.indexWriter;
    }
	
	public void close() throws IOException{
		if(null!=this.indexWriter){
			this.indexWriter.close();
		}
    }
	
	public void init(String indexPath,Version version) throws Exception{
		//索引库路径不存在则新建一个
        File indexFile=new File(indexPath);
        
        if(!indexFile.exists()) {
        	indexFile.mkdir();
        }
        
        Directory fsDirectory = FSDirectory.open(indexFile);
        //有网后换IKAnalyzer
        Analyzer analyzer = new StandardAnalyzer(version);  
        IndexWriterConfig confIndex = new IndexWriterConfig(Version.LUCENE_46, analyzer);
        confIndex.setOpenMode(OpenMode.CREATE);
        if (IndexWriter.isLocked(fsDirectory)) {
            IndexWriter.unlock(fsDirectory);
        }
        this.indexWriter =new IndexWriter(fsDirectory, confIndex);
    }
	
	/**
     * 创建索引
     * @throws Exception
     */
    public boolean isLocked(String indexPath) throws Exception{
    	File indexFile=new File(indexPath);
        
        if(!indexFile.exists()) {
        	indexFile.mkdir();
        }
        
        Directory fsDirectory = FSDirectory.open(indexFile);
        if (IndexWriter.isLocked(fsDirectory)) {
        	return true;
        }
        return false;
    }
    
    /**
     * 创建索引
     * 
     * @param doc
     * @throws Exception
     */
    public boolean createIndex(Document doc) throws Exception {
        List<Document> docs = new ArrayList<Document>();
        docs.add(doc);
        return createIndex(docs);
    }
    
    /**
     * 创建索引
     * 
     * @param docs
     * @throws Exception
     */
    public boolean createIndex(List<Document> docs) throws Exception{
        boolean flag = false;
        try {
            for (Document doc : docs) {
            	this.indexWriter.addDocument(doc);
            }
            // 优化操作
            this.indexWriter.commit();
            //优化压缩段，大规模添加数据的时候建议，少使用本方法，会影响性能
            this.indexWriter.forceMerge(1); // forceMerge代替optimize
            //log.info("lucene create success.");
            flag =  true;
        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        } finally {
            if (getIndexWriter() != null) {
                try {
                    getIndexWriter().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    
    /**
     * 更新索引
     * 
     * 例如：Term term = new Term("id","1234567");
     * 先去索引文件里查找id为1234567的Document，如果有就更新它(如果有多条，最后更新后只有一条)，如果没有就新增。
     * 数据库更新的时候，我们可以只针对某个列来更新，而lucene只能针对一行数据更新。
     * 
     * @param field Document的Field(类似数据库的字段)
     * @param value Field中的一个关键词
     * @param doc
     * @return
     */
    public boolean updateIndex(String field, String value, Document doc) throws Exception{
        boolean flag = false;
        try {
        	this.indexWriter.updateDocument(new Term(field, value), doc);
            //log.info("lucene update success.");
            flag = true;
        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }finally{
            if(this.indexWriter!=null){
                try {
                	this.indexWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }  
            }
        }
        return flag;
    }
    
    /**
     * 删除索引
     * 
     * @param field Document的Field(类似数据库的字段)
     * @param value Field中的一个关键词
     * @return
     */
    public boolean deleteIndex(String field, String value) throws Exception{
        boolean flag = false;
        try {
        	this.indexWriter.deleteDocuments(new Term(field, value));
            //log.info("lucene delete success.");
            flag = true;
        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }finally{
            if(this.indexWriter!=null){
                try {
                	this.indexWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }  
            }
        }
        return flag;
    }
    
    /**
     * 删除整个索引库
     * 
     * @return
     */
    public boolean deleteAllIndex() throws Exception{
        boolean flag = false;
        try {
        	this.indexWriter.deleteAll();
            //log.info("lucene delete all success.");
            flag =  true;
        } catch (Exception e) {
            new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }finally{
            if(getIndexWriter()!=null){
                try {
                    getIndexWriter().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }  
            }
        }
        return flag;
    }
    

    /**
     * 判断索引库是否已创建
     * 
     * @return true:存在，false：不存在
     * @throws Exception
     */
    public boolean existsIndex(String indexPath) throws Exception {
        File file = new File(indexPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String indexSufix = "/segments.gen";
        // 根据索引文件segments.gen是否存在判断是否是第一次创建索引
        File indexFile = new File(indexPath + indexSufix);
        return indexFile.exists();
    }
  
}