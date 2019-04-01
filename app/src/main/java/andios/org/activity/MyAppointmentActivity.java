package andios.org.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import andios.org.R;
import andios.org.adapter.MyAppointmentAdapter;
import andios.org.appplection.MyApplication;
import andios.org.bean.AppointmentBean;
import andios.org.bean.MyAppointmentBean;
import andios.org.bean.MyCollectionLibBean;
import andios.org.tool.Constance;

public class MyAppointmentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private MyAppointmentAdapter appointmentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<AppointmentBean> list;
    private List<MyAppointmentBean> myAppointmentBeanList;
    private AppointmentBean bean;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);
        init();
    }
    private void init(){
        gson=new Gson();
        list=new ArrayList<>();
//        initData();
        getAppointmentIndex(Constance.user_index);
        appointmentAdapter=new MyAppointmentAdapter(MyAppointmentActivity.this,list);
        recyclerView=findViewById(R.id.content_my_appointment_list);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("我的预约");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        recyclerView.setAdapter(appointmentAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAppointmentActivity.this.finish();
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        manager=new LinearLayoutManager(MyAppointmentActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyAppointmentActivity.this,DividerItemDecoration.VERTICAL));

    }

    private void getAppointmentIndex(int u_index){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+
                "servlet/MyAppointmentServlet?u_index="+u_index
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                myAppointmentBeanList =gson.fromJson(s,new TypeToken<List<MyAppointmentBean>>(){}.getType());
                for (int i = 0; i< myAppointmentBeanList.size(); i++){
                    getAppointment(myAppointmentBeanList.get(i).getAppointment_id());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("getAppointmentIndex");
        MyApplication.getHttpQueues().add(request);
    }

    private void getAppointment(int appointment_id){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+
                "servlet/GetAppointmentServlet?appointment_id="+appointment_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                bean=gson.fromJson(s,new TypeToken<AppointmentBean>(){}.getType());
                list.add(bean);
                appointmentAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("getAppointment");
        MyApplication.getHttpQueues().add(request);
    }

}
