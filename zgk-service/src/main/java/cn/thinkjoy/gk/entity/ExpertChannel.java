package cn.thinkjoy.gk.entity;

/**
 * Created by yangguorong on 16/11/22.
 */
public class ExpertChannel {

    private long id;
    private int status; // 状态  -1：删除   0：正常直播  1：已结束
    private long createDate; // 创建时间
    private long modifyDate; // 修改时间
    private long expertId; // 专家ID
    private long stuId; // 学生ID
    private int type; // 频道类型 0：专家主播  1：学生主播
    private String cid; // 频道ID，32位字符串
    private long ctime; // 创建频道的时间戳
    private String name; // 频道名称
    private String pushUrl; // 推流地址
    private String httpPullUrl; // http拉流地址
    private String hlsPullUrl; // hls拉流地址
    private String rtmpPullUrl; // rtmp拉流地址
    private int code; // 状态码
    private String msg; // 错误信息

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public long getExpertId() {
        return expertId;
    }

    public void setExpertId(long expertId) {
        this.expertId = expertId;
    }

    public long getStuId() {
        return stuId;
    }

    public void setStuId(long stuId) {
        this.stuId = stuId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getHttpPullUrl() {
        return httpPullUrl;
    }

    public void setHttpPullUrl(String httpPullUrl) {
        this.httpPullUrl = httpPullUrl;
    }

    public String getHlsPullUrl() {
        return hlsPullUrl;
    }

    public void setHlsPullUrl(String hlsPullUrl) {
        this.hlsPullUrl = hlsPullUrl;
    }

    public String getRtmpPullUrl() {
        return rtmpPullUrl;
    }

    public void setRtmpPullUrl(String rtmpPullUrl) {
        this.rtmpPullUrl = rtmpPullUrl;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ExpertChannel{" +
                "id=" + id +
                ", status=" + status +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", expertId=" + expertId +
                ", stuId=" + stuId +
                ", type=" + type +
                ", cid='" + cid + '\'' +
                ", ctime=" + ctime +
                ", name='" + name + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", httpPullUrl='" + httpPullUrl + '\'' +
                ", hlsPullUrl='" + hlsPullUrl + '\'' +
                ", rtmpPullUrl='" + rtmpPullUrl + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
