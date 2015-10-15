package cn.thinkjoy.gk.controller.question.dto;

import java.io.Serializable;

/**
 * Created by clei on 15/5/26.
 */
public class QuestionContentDto implements Serializable{

    private static final long serialVersionUID = -2670480239314036496L;

    private String text;

    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
