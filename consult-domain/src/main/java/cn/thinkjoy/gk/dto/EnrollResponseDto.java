package cn.thinkjoy.gk.dto;

import java.util.List;

/**
 * Created by wpliu on 15/9/26.
 */
public class EnrollResponseDto {
    private String title;
    private List<EnrollInfo> infos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EnrollInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<EnrollInfo> infos) {
        this.infos = infos;
    }
}
