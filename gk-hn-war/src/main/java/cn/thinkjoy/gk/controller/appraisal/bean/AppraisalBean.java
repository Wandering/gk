package cn.thinkjoy.gk.controller.appraisal.bean;

/**
 * Created by clei on 15/9/28.
 */
public class AppraisalBean {

    private final String affiliateId="2";

    private final String accessKey="8F14E45FCEEA167A5A36DEDD4BEA2544";

    private final String version="V12";

    private final String item = "b438e1582d3331d1fcc8fa4ecc48543f20ada3ca851d79281ef58db137ad7c21";

    private final String api = "getDirectTestUrl";

    private String testerId;

    private String testerNm;

    public String getAffiliateId() {
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