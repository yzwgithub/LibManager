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
import andios.org.bean.HomeListBean;
import andios.org.listener_interface.OnItemClickListener;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/13
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    private OnItemClickListener listener=null;
    private LayoutInflater inflater;
    private List<HomeListBean>list;
    public HomeRecyclerAdapter(Context context, List<HomeListBean>list) {
        inflater=LayoutInflater.from(context);
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.home_recycler_item,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.content.setText(list.get(i).getContent());
        myViewHolder.title.setText(list.get(i).getTitle());
//        myViewHolder.imageview.setImageResource(R.drawable.xioa_chun);
        myViewHolder.imageview.setImageBitmap(list.get(i).getBitmaps());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,content;
        private ImageView imageview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.recycler_title);
            content= itemView.findViewById(R.id.recycler_content);
            imageview= itemView.findViewById(R.id.recycler_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onClick(v,getAdapterPosition());
            }
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener=listener;

    }
}
