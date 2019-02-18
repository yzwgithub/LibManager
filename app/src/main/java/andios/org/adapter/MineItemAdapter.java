package andios.org.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import andios.org.R;
import andios.org.custom_view.CircleImageView;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/7
 */

public class MineItemAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private final String []text={"我的收藏","修改个人信息","退出登录"};
    private final int []img_resource={R.drawable.chun_logo,R.drawable.chisong_logo,R.drawable.lushen_logo};

    public MineItemAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return text[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        MyViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.my_list_item,null);
            holder=new MyViewHolder();
            holder.circleImageView=convertView.findViewById(R.id.item_logo);
            holder.textView=convertView.findViewById(R.id.item_text);
            holder.imageView=convertView.findViewById(R.id.item_in);
            convertView.setTag(holder);
        }else {
            holder= (MyViewHolder) convertView.getTag();
        }
        holder.circleImageView.setImageResource(img_resource[position]);
        holder.textView.setText(text[position]);
        return convertView;
    }

    class MyViewHolder{
        ImageView imageView;
        CircleImageView circleImageView;
        TextView textView;
    }
}
