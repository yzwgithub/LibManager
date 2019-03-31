package andios.org.bean;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/3/29
 */

public class MyCollectionBean {
    private int collection_index;
    private int u_index;
    private int lib_id;

    public MyCollectionBean() {
    }

    public MyCollectionBean(int collection_index, int u_index, int lib_id) {
        this.collection_index = collection_index;
        this.u_index = u_index;
        this.lib_id = lib_id;
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

    public int getLib_id() {
        return lib_id;
    }

    public void setLib_id(int lib_id) {
        this.lib_id = lib_id;
    }
}
