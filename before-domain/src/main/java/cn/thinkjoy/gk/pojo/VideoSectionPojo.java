package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/23.
 */
public class VideoSectionPojo implements Serializable{
    private Long sectionId;
    private Long courseId;
    private String sectionName;
    private Integer sectionSort;
    private Integer isAccept;
    private String fileUrl;
}
