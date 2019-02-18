package andios.org.bean;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/2/7
 */

public class ShowBean {
    private int show_id;
    private String picture_url;
    private String show_title;
    private String show_information;

    public ShowBean() {

    }

    public ShowBean(String picture_url, String show_title, String show_information) {
        this.picture_url = picture_url;
        this.show_title = show_title;
        this.show_information = show_information;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getShow_title() {
        return show_title;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public String getShow_information() {
        return show_information;
    }

    public void setShow_information(String show_information) {
        this.show_information = show_information;
    }
}
