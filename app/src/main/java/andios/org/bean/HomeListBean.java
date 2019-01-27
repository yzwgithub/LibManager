package andios.org.bean;

import android.graphics.Bitmap;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/15
 */

public class HomeListBean {
    private Bitmap bitmaps;
    private String title;
    private String content;

    public void setBitmaps(Bitmap bitmaps) {
        this.bitmaps = bitmaps;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getBitmaps() {
        return bitmaps;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


}
