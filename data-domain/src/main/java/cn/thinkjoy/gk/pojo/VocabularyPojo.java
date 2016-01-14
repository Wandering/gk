package cn.thinkjoy.gk.pojo;

/**
 * Created by zuohao on 15/12/29.
 */
public class VocabularyPojo {
    private long id;
    /** 分类id */
    private long categoryId;
    /** 标题 */
    private String title;
    /** 摘要 */
    private String summary;
    /** 富文本内容 */
    private String content;
    /** 排序 */
    private Integer sortId;
    /** 点击量 */
    private Integer hits;
    /** 区域Id */
    private long areaId;
    /** 创建时间 */
    private long createDate;
    /** 最后修改时间 */
    private long lastModDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(long lastModDate) {
        this.lastModDate = lastModDate;
    }
}
