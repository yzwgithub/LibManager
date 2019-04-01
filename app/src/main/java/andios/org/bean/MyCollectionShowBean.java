package andios.org.bean;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/4/1
 */

public class MyCollectionShowBean {
    private int collection_index;
    private int u_index;
    private int show_id;

    public MyCollectionShowBean() {
    }

    public MyCollectionShowBean(int collection_index, int u_index, int show_id) {
        this.collection_index = collection_index;
        this.u_index = u_index;
        this.show_id = show_id;
    }

    public int getCollection_index() {
        return collection_index;
    }

    public void setCollection_index(int collection_index) {
        this.collection_index = collection_index;
    }

    public int getU_index() {
        return u_index;
    }

    public void setU_index(int u_index) {
        this.u_index = u_index;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }
}
