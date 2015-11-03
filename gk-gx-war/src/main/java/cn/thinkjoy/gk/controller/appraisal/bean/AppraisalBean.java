package cn.thinkjoy.gk.controller.appraisal.bean;

/**
 * Created by clei on 15/9/28.
 */
public class AppraisalBean {

    private final Integer affiliateId=2;

    private final String accessKey="8F14E45FCEEA167A5A36DEDD4BEA2543";

    private final String version="V12";

    private final String item = "810457431018a6dd2ae80cde555d89175919b75968947a2acf010737a0c18fe4";

    private final String api = "getDirectTestUrl";

    private String testerId;

    private String testerNm;

    public Integer getAffiliateId() {
        return affiliateId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getVersion() {
        return version;
    }

    public String getItem() {
        return item;
    }

    public String getApi() {
        return api;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    public String getTesterNm() {
        return testerNm;
    }

    public void setTesterNm(String testerNm) {
        this.testerNm = testerNm;
    }
}
