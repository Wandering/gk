package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by ZL on 2015/9/23.
 */
public class Information extends BaseDomain{
    private Long id;
    /*�߿��ȵ����*/
    private String hotInformation;
    /*�߿��ȵ��������*/
    private String informationContent;

    @Override
    public Long getId() {return id;}
    @Override
    public void setId(Long id) {this.id = id; }

    public String getHotInformation() {
        return hotInformation;
    }

    public void setHotInformation(String hotInformation) {
        this.hotInformation = hotInformation;
    }

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }
}
