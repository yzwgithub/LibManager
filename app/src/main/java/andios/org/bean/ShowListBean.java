package andios.org.bean;

import android.graphics.Bitmap;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/15
 */

public class ShowListBean {

    private Bitmap bitmap;
    private String context;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getContext() {
        return context;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
