package andios.org.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import andios.org.R;
import andios.org.appplection.MyApplication;
import andios.org.bean.AppointmentBean;
import andios.org.tool.BasisTimesUtils;
import andios.org.tool.Constance;

public class AppointmentActivity extends AppCompatActivity {
    private Calendar calendar;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private View subView;
    private DatePicker appointment_data_picker;
    private TimePicker appointment_time_picker;
    private Spinner appointment_spinner;
    private EditText college_name;
    private EditText class_name;
    private EditText number_of_class;
    private EditText experiment_name;
    private EditText experiment_subject;
    private Button appointment_submit;
    private Gson gson;

    private AppointmentBean bean;

    private String s_lib_name;

    private String s_college_name;
    private String s_class_name;
    private String s_number_of_class;
    private String s_experiment_name;
    private String s_experiment_subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        init();
        onClick();
    }

    private void init(){
        gson=new Gson();
        calendar=Calendar.getInstance();
        toolbar = findViewById(R.id.toolbar);

        fab = findViewById(R.id.fab);

        subView = findViewById(R.id.appointment_sub_view);
        appointment_data_picker=subView.findViewById(R.id.appointment_data_picker);
        appointment_time_picker=subView.findViewById(R.id.appointment_time_picker);
        appointment_spinner=subView.findViewById(R.id.appointment_spinner);
        college_name=subView.findViewById(R.id.college_name);
        class_name=subView.findViewById(R.id.class_name);
        number_of_class=subView.findViewById(R.id.number_of_class);
        experiment_name=subView.findViewById(R.id.experiment_name);
        experiment_subject=subView.findViewById(R.id.experiment_subject);
        appointment_submit=subView.findViewById(R.id.appointment_submit);


        toolbar.setTitle("预约");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

    }

    private void onClick(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentActivity.this.finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        appointment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        appointment_data_picker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        BasisTimesUtils.s_date=year+"-"+monthOfYear+"-"+dayOfMonth;
                    }
                });

        appointment_time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                BasisTimesUtils.s_time=hourOfDay+":" + minute;
            }
        });
    }

    private AppointmentBean getAppointmentBean(){
        s_lib_name=appointment_spinner.getSelectedItem().toString();
        s_college_name=college_name.getText().toString();
        s_class_name=class_name.getText().toString();
        s_number_of_class=number_of_class.getText().toString();
        s_experiment_name=experiment_name.getText().toString();
        s_experiment_subject=experiment_subject.getText().toString();

        AppointmentBean bean=new AppointmentBean(s_lib_name,
                BasisTimesUtils.s_date,BasisTimesUtils.s_time,
                s_college_name,s_class_name,s_number_of_class,
                s_experiment_name,s_experiment_subject);
        return bean;
    }

    private void requestAppointmentBean(final String jsonAppointmentBean){
        StringRequest request=new StringRequest(Request.Method.POST, Constance.url+"servlet/AppointmentServlet?", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(AppointmentActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AppointmentActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("appointmentBean",jsonAppointmentBean);
                return map;
            }
        };
        request.setTag("requestAppointmentBean");
        MyApplication.getHttpQueues().add(request);
    }
    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提交申请").
                setMessage("确定要提交申请吗？").
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AppointmentActivity.this, "取消！", Toast.LENGTH_SHORT).show();
            }
        }).setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bean=getAppointmentBean();
                requestAppointmentBean(gson.toJson(bean));
            }
        }).show();
    }
}
