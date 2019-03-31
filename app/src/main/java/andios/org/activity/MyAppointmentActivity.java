package andios.org.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import andios.org.R;
import andios.org.adapter.MyAppointmentAdapter;

public class MyAppointmentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private MyAppointmentAdapter appointmentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);
        init();
    }
    private void init(){
        appointmentAdapter=new MyAppointmentAdapter(MyAppointmentActivity.this);
        recyclerView=findViewById(R.id.content_my_appointment_list);


        manager=new LinearLayoutManager(MyAppointmentActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyAppointmentActivity.this,DividerItemDecoration.VERTICAL));

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
    }

}
