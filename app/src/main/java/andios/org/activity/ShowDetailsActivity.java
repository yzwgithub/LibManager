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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import andios.org.R;
import andios.org.appplection.MyApplication;
import andios.org.bean.ShowDetailsBean;
import andios.org.tool.Constance;

public class ShowDetailsActivity extends AppCompatActivity {
    private Gson gson;
    private Toolbar toolbar;
    private boolean isSaved=false;
    private View subView;
    private TextView content;
    private TextView title;
    private TextView subTitle;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_activity);
        init();
        requestShowDetailsBean(Integer.parseInt(getIntents()));
    }

    private void init(){
        gson=new Gson();

        toolbar=findViewById(R.id.show_toolbar);
        subView=findViewById(R.id.include_show_details);
        title=subView.findViewById(R.id.include_show_title);
        subTitle=subView.findViewById(R.id.include_show_subtitle);
        content=subView.findViewById(R.id.include_show_content);

        toolbar.setTitle(getIntents());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDetailsActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_details_menu,menu);
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
                    save(Constance.user_index+"",getIntents());
                    Toast.makeText(this, "收藏...", Toast.LENGTH_SHORT).show();
                }else{
                    item.setIcon(R.mipmap.save);
                    isSaved=false;
                    Toast.makeText(this, "取消收藏...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestShowDetailsBean(final int show_id){
        StringRequest request=new StringRequest(Request.Method.POST, Constance.url+"servlet/ShowDetailsServlet?", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ShowDetailsBean bean=gson.fromJson(s,new TypeToken<ShowDetailsBean>(){}.getType());
                setDetails(bean.getTitle(),bean.getSubTitle());
                requestContent(Constance.url+"showDetailsFile/荷塘月色.txt");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ShowDetailsActivity.this, "请求失败！", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("show_id",""+show_id);
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
                content.setText(Constance.getChar(s));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("requestContent");
        MyApplication.getHttpQueues().add(request);
    }

    private void save(String u_index,String lib_id){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+
                "servlet/SetCollectionServlet?u_index="+u_index+"&lib_id="+lib_id,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(ShowDetailsActivity.this, "收藏...", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("Save");
        MyApplication.getHttpQueues().add(request);
    }
    private String getIntents(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String value=bundle.getString("id");
        return value;
    }

    private void setDetails(String sTitle,String sSubTitle){
        title.setText(sTitle);
        subTitle.setText(sSubTitle);
    }
}
