package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by ZL on 2015/9/23.
 */
public class Information extends BaseDomain{
    private Long id;
    /* ����ʱ��*/
    private Long createDate;
    /* �޸�ʱ��*/
    private Long lastModDate;
    /*�߿��ȵ����*/
    private String hotInformation;
    /*�߿��ȵ��������*/
    private String informationSubContent;
    /*�߿��ȵ�����*/
    public String informationContent;

    @Override
    public Long getId() {return id;}
    @Override
    public void setId(Long id) {this.id = id; }

    public Long getCreateDate() {return createDate;}

    public void setCreateDate(Long createDate) {this.createDate = createDate;}

    public Long getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(Long lastModDate) {
        this.lastModDate = lastModDate;
    }

    public String getHotInformation() {
        return hotInformation;
    }

    public void setHotInformation(String hotInformation) {
        this.hotInformation = hotInformation;
    }

    public String getInformationSubContent() {
        return informationSubContent;
    }

    public void setInformationSubContent(String informationSubContent) {
        this.informationSubContent = informationSubContent;
    }

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }
}
