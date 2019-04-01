package andios.org.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import andios.org.R;
import andios.org.adapter.MyStoreActivityAdapter;
import andios.org.appplection.MyApplication;
import andios.org.bean.MyCollectionLibBean;
import andios.org.bean.ShowInformationBean;
import andios.org.bean.ShowListBean;
import andios.org.tool.Constance;

public class MyStoreActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MyStoreActivityAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<ShowListBean>list;
    private ShowListBean bean;
    private List<ShowInformationBean> showInformationBeanList;
    private List<MyCollectionLibBean> myCollectionLibBeanList;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        init();
    }
    private void init(){
        gson=new Gson();

        list=new ArrayList<>();
        getStoreIndex(Constance.user_index);

        toolbar=findViewById(R.id.my_store_toolbar);
        toolbar.setTitle("store_class_01");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreActivity.this.finish();
            }
        });

        adapter=new MyStoreActivityAdapter(MyStoreActivity.this,list);
        recyclerView=findViewById(R.id.my_store_list);
        manager=new LinearLayoutManager(MyStoreActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyStoreActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_store_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.store_class_01:
                Toast.makeText(this, "store_class_01", Toast.LENGTH_SHORT).show();
                toolbar.setTitle("store_class_01");
                break;
            case R.id.store_class_02:
                Toast.makeText(this, "store_class_02", Toast.LENGTH_SHORT).show();
                toolbar.setTitle("store_class_02");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getStoreIndex(int u_index){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+
                "servlet/MyCollectionServlet?u_index="+u_index
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                myCollectionLibBeanList =gson.fromJson(s,new TypeToken<List<MyCollectionLibBean>>(){}.getType());
                for (int i = 0; i< myCollectionLibBeanList.size(); i++){
                    getMyStoreList(myCollectionLibBeanList.get(i).getLib_id());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("getStoreIndex");
        MyApplication.getHttpQueues().add(request);
    }

    private void getMyStoreList(int show_id){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+
                "servlet/ShowInformationServlet?show_id="+show_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                showInformationBeanList=gson.fromJson(s,new TypeToken<List<ShowInformationBean>>(){}.getType());
                for (int i = 0; i< showInformationBeanList.size(); i++){
                    bean =new ShowListBean();
                    bean.setContext(showInformationBeanList.get(i).getShow_information());
                    requestBitmaps(showInformationBeanList.get(i).getPicture_url(),i);
                    list.add(bean);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("getMyStoreList");
        MyApplication.getHttpQueues().add(request);
    }

    private void requestBitmaps(String url, final int i){
        ImageRequest request=new ImageRequest(Constance.url+url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                list.get(i).setBitmap(bitmap);
                adapter.notifyDataSetChanged();
            }
        }, 1000, 200, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("requestBitmaps");
        MyApplication.getHttpQueues().add(request);
    }
}
