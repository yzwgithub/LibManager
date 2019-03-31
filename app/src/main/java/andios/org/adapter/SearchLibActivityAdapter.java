package andios.org.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import andios.org.R;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/3/30
 */

public class SearchLibActivityAdapter extends RecyclerView.Adapter<SearchLibActivityAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    public SearchLibActivityAdapter(Context context){
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.search_lib_item,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        myViewHolder.textView.setText("sadsfdsfsdf");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.search_lib_item_tx);
        }
    }
}
