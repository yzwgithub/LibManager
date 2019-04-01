package andios.org.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import andios.org.R;
import andios.org.bean.ShowListBean;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/3/31
 */

public class MyStoreActivityAdapter extends RecyclerView.Adapter<MyStoreActivityAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<ShowListBean> list;
    public MyStoreActivityAdapter(Context context,List<ShowListBean>list){
        inflater=LayoutInflater.from(context);
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView=inflater.inflate(R.layout.my_store_list_item,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.content.setText(list.get(i).getContext());
        myViewHolder.icon.setImageBitmap(list.get(i).getBitmap());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView content;
        private ImageView icon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.my_store_content);
            icon=itemView.findViewById(R.id.my_store_icon);
        }
    }
}
