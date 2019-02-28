package andios.org.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import andios.org.R;
import andios.org.appplection.MyApplication;
import andios.org.bean.LibDetailsBean;
import andios.org.tool.Constance;
import andios.org.tool.IntentTools;

public class LibDetailsActivity extends AppCompatActivity{
    private Gson gson;
    private Toolbar toolbar;
    private boolean isSaved=false;
    private View subView;
    private TextView content;
    private TextView t_title;
    private TextView subTitle;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home__details_activity);
        init();
        requestShowDetailsBean(1);
    }
    private void init(){
        gson=new Gson();

        String title=getIntents();
        toolbar=findViewById(R.id.home_details_toolbar);
        subView=findViewById(R.id.include_home_details);
        t_title=subView.findViewById(R.id.include_appointment_title);
        img=subView.findViewById(R.id.include_appointment_img);
        subTitle=subView.findViewById(R.id.include_appointment_sub_title);
        content=subView.findViewById(R.id.include_appointment_content);

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibDetailsActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appointment_details_menu,menu);
        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Toast.makeText(this, "分享...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_save:
                if (!isSaved){
                    item.setIcon(R.mipmap.has_saved);
                    isSaved=true;
                    Toast.makeText(this, "收藏...", Toast.LENGTH_SHORT).show();
                }else{
                    item.setIcon(R.mipmap.save);
                    isSaved=false;
                    Toast.makeText(this, "取消收藏...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_appointment:
                IntentTools.getInstance().intent(LibDetailsActivity.this,AppointmentActivity.class,null);
        }
        return super.onOptionsItemSelected(item);
    }
    private String getIntents(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String value=bundle.getString("title");
        return value;
    }

    private void requestShowDetailsBean(final int lib_id){
        StringRequest request=new StringRequest(Request.Method.POST, Constance.url+"servlet/LibDetailsServlet?", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LibDetailsBean bean=gson.fromJson(s,new TypeToken<LibDetailsBean>(){}.getType());
                setDetails(bean.getTitle(),bean.getSubTitle());
                requestContent(Constance.url+"showDetailsFile/荷塘月色.txt");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LibDetailsActivity.this, "请求失败！", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("lib_id",""+lib_id);
                return map;
            }
        };
        request.setTag("ShowDetailsActivity");
        MyApplication.getHttpQueues().add(request);
    }

    private void requestContent(String url){
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    content.setText(new String(s.getBytes("ISO-8859-1"),"GBK"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("requestContent");
        MyApplication.getHttpQueues().add(request);
    }
    private void setDetails(String sTitle,String sSubTitle){
        t_title.setText(sTitle);
        subTitle.setText(sSubTitle);
    }
}
