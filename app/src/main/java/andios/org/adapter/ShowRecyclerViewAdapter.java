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
import andios.org.listener_interface.OnItemClickListener;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/15
 */

public class ShowRecyclerViewAdapter extends RecyclerView.Adapter<ShowRecyclerViewAdapter.MyViewHolder> {

    private List<ShowListBean>list;
    private OnItemClickListener listener=null;
    private LayoutInflater inflater;
    public ShowRecyclerViewAdapter(Context context, List<ShowListBean> list){
        inflater=LayoutInflater.from(context);
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=inflater.inflate(R.layout.show_recycler_item,viewGroup,false);
        return new MyViewHolder(view);
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

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView content;
        private ImageView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.scan_item_content);
            icon=itemView.findViewById(R.id.scan_item_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onClick(v,getAdapterPosition());
            }
        }
    }

    public void setOnItemClickClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
