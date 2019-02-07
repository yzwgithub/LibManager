package andios.org.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import andios.org.adapter.HomeRecyclerAdapter;
import andios.org.appplection.MyApplication;
import andios.org.bean.HomeListBean;
import andios.org.bean.ScanBean;
import andios.org.listener_interface.OnItemClickListener;
import andios.org.custom_view.Banner;
import andios.org.custom_view.RoundView;
import andios.org.tool.Constance;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;

    private LinearLayoutManager manager;
    private List bannerList;
    private RoundView one,two,three;
    private Banner banner;

    private Gson gson;
    private List<ScanBean> scanBeanList;
    private HomeListBean homeListBean;
    private List<HomeListBean>listBeans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("onCreateView","onCreateView");
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("onViewCreated","onViewCreated");
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("onActivityCreated", "onActivityCreated");
        OnClick();//设置监听事件
        getScanBeanJson();
    }

    private void initView(View view){
        gson=new Gson();

        listBeans=new ArrayList<>();

        banner=view.findViewById(R.id.banner);

        one=view.findViewById(R.id.view_one);

        two=view.findViewById(R.id.view_two);

        three=view.findViewById(R.id.view_three);

        one.setText("凤");
        one.setImage(R.drawable.feng_logo);

        two.setText("句芒");
        two.setImage(R.drawable.goumang_logo);

        three.setText("祝融");
        three.setImage(R.drawable.zhurong_logo);

        bannerList=new ArrayList();
        bannerList.add(R.drawable.haitang_one);
        bannerList.add(R.drawable.haitang_two);
        bannerList.add(R.drawable.haitang_three);
        bannerList.add(R.drawable.haitang_four);
        banner.setData(bannerList);
        banner.bannerPlay(4000);

        recyclerView=view.findViewById(R.id.recycler_home);
        adapter=new HomeRecyclerAdapter(view.getContext(),listBeans);
        manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void OnClick(){
        one.setOnButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "第一个", Toast.LENGTH_SHORT).show();
            }
        });
        two.setOnButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "第二个", Toast.LENGTH_SHORT).show();
            }
        });
        three.setOnButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "第三个", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(getActivity(), "点击了第"+position+"个图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getScanBeanJson(){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+"servlet/ScanServlet", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                scanBeanList =gson.fromJson(s,new TypeToken<List<ScanBean>>(){}.getType());
                for (int i = 0; i< scanBeanList.size(); i++){
                    homeListBean =new HomeListBean();
                    homeListBean.setTitle(scanBeanList.get(i).getShow_title());
                    homeListBean.setContent(scanBeanList.get(i).getShow_information());
                    requestBitmaps(scanBeanList.get(i).getPicture_url(),i);
                    listBeans.add(homeListBean);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("getScanBeanJson");
        MyApplication.getHttpQueues().add(request);
    }

    private void requestBitmaps(String url, final int i){

        ImageRequest request=new ImageRequest(Constance.url+url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                listBeans.get(i).setBitmaps(bitmap);
                adapter.notifyDataSetChanged();
            }
        }, 100, 90, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("getBitmaps");
        MyApplication.getHttpQueues().add(request);
    }

}
