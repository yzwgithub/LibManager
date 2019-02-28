package andios.org.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import andios.org.activity.AppointmentActivity;
import andios.org.activity.LibDetailsActivity;
import andios.org.activity.MyAppointmentActivity;
import andios.org.activity.SearchLibActivity;
import andios.org.adapter.HomeRecyclerAdapter;
import andios.org.appplection.MyApplication;
import andios.org.bean.HomeListBean;
import andios.org.bean.LibInformationBean;
import andios.org.listener_interface.OnItemClickListener;
import andios.org.custom_view.Banner;
import andios.org.custom_view.RoundView;
import andios.org.tool.Constance;
import andios.org.tool.IntentTools;


public class LibFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;

    private LinearLayoutManager manager;
    private List bannerList;
    private RoundView one,two,three;
    private Banner banner;

    private Gson gson;
    private List<LibInformationBean> showInformationBeanList;
    private HomeListBean homeListBean;
    private List<HomeListBean>listBeans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointment_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

        one.setText("预约");
        one.setImage(R.drawable.one);

        two.setText("我的预约");
        two.setImage(R.drawable.two);

        three.setText("查询");
        three.setImage(R.drawable.three);

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
                IntentTools.getInstance().intent(getContext(),AppointmentActivity.class,null);
            }
        });
        two.setOnButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(),MyAppointmentActivity.class,null);
            }
        });
        three.setOnButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(),SearchLibActivity.class,null);
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("title",listBeans.get(position).getTitle());
                IntentTools.getInstance().intent(getActivity(),LibDetailsActivity.class,bundle);
            }
        });
    }

    private void getScanBeanJson(){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+"servlet/LibInformationServlet", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                showInformationBeanList =gson.fromJson(s,new TypeToken<List<LibInformationBean>>(){}.getType());
                for (int i = 0; i< showInformationBeanList.size(); i++){
                    homeListBean =new HomeListBean();
                    homeListBean.setTitle(showInformationBeanList.get(i).getShow_title());
                    homeListBean.setContent(showInformationBeanList.get(i).getShow_information());
                    requestBitmaps(showInformationBeanList.get(i).getPicture_url(),i);
                    listBeans.add(homeListBean);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                for (int i=0;i<3;i++){
                    homeListBean =new HomeListBean();
                    homeListBean.setTitle("");
                    homeListBean.setContent("");
                    listBeans.add(homeListBean);
                }
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
