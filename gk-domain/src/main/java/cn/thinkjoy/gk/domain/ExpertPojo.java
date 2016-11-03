package cn.thinkjoy.gk.domain;

/**
 * Created by yangyongping on 2016/11/2.
 */
public class ExpertPojo {
    //服务内容
    String serviceType;
    //视频方式(微信:weixin,QQ:qq)
    String channel;
    //账号
    String channelNum;
    //联系人
    String contactPerson;
    //联系电话
    String contactPhone;
    //问题描述
    String questionDesc;
    //可服务日期 格式:2016-10-31
    String serviceDay;
    //服务时间  格式:9:00-10:00
    String serviceTime;
    //是否同意条款
    String agreeMark;
    //评分
    Integer score;
    //服务专家id
    String expertId;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getServiceDay() {
        return serviceDay;
    }

    public void setServiceDay(String serviceDay) {
        this.serviceDay = serviceDay;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getAgreeMark() {
        return agreeMark;
    }

    public void setAgreeMark(String agreeMark) {
        this.agreeMark = agreeMark;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public ExpertPojo(String serviceType, String channel, String channelNum, String contactPerson, String contactPhone, String questionDesc, String serviceDay, String serviceTime, String agreeMark, Integer score, String expertId) {
        this.serviceType = serviceType;
        this.channel = channel;
        this.channelNum = channelNum;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.questionDesc = questionDesc;
        this.serviceDay = serviceDay;
        this.serviceTime = serviceTime;
        this.agreeMark = agreeMark;
        this.score = score;
        this.expertId = expertId;
    }
    public ExpertPojo(){}
}
