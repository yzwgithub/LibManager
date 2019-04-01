package andios.org.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import andios.org.R;
import andios.org.bean.AppointmentBean;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/3/29
 */

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<AppointmentBean>list;
    public MyAppointmentAdapter(Context context,List<AppointmentBean>list) {
        inflater=LayoutInflater.from(context);
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=inflater.inflate(R.layout.my_appointment_item,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.my_appointment_item_tx.setText(list.get(i).getLib_name());
        myViewHolder.my_appointment_item_tx_02.setText(list.get(i).getDate()+"  "+list.get(i).getTime());
        myViewHolder.my_appointment_item_tx_03.setText(list.get(i).getCollege_name());
        myViewHolder.my_appointment_item_tx_04.setText(list.get(i).getClass_name());
        myViewHolder.my_appointment_item_tx_05.setText(list.get(i).getNumber_of_class());
        myViewHolder.my_appointment_item_tx_06.setText(list.get(i).getExperiment_name());
        myViewHolder.my_appointment_item_tx_07.setText(list.get(i).getExperiment_subject());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView my_appointment_item_tx;
        private TextView my_appointment_item_tx_02;
        private TextView my_appointment_item_tx_03;
        private TextView my_appointment_item_tx_04;
        private TextView my_appointment_item_tx_05;
        private TextView my_appointment_item_tx_06;
        private TextView my_appointment_item_tx_07;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            my_appointment_item_tx=itemView.findViewById(R.id.my_appointment_item_tx);
            my_appointment_item_tx_02=itemView.findViewById(R.id.my_appointment_item_tx_02);
            my_appointment_item_tx_03=itemView.findViewById(R.id.my_appointment_item_tx_03);
            my_appointment_item_tx_04=itemView.findViewById(R.id.my_appointment_item_tx_04);
            my_appointment_item_tx_05=itemView.findViewById(R.id.my_appointment_item_tx_05);
            my_appointment_item_tx_06=itemView.findViewById(R.id.my_appointment_item_tx_06);
            my_appointment_item_tx_07=itemView.findViewById(R.id.my_appointment_item_tx_07);
        }
    }
}
