package andios.org.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import andios.org.activity.Show_Details;
import andios.org.adapter.ShowRecyclerViewAdapter;
import andios.org.appplection.MyApplication;
import andios.org.bean.LibInformationBean;
import andios.org.bean.ShowListBean;
import andios.org.listener_interface.OnItemClickListener;
import andios.org.tool.Constance;
import andios.org.tool.IntentTools;

public class ShowFragment extends Fragment {

    private EditText textSearch;
    private LinearLayout layoutSearch;
    private Toolbar toolbar;

    boolean isExpand=false;

    private TransitionSet set;
    private ViewGroup rootView;
    private InputMethodManager imm;
    private RecyclerView recyclerView;
    private ShowRecyclerViewAdapter adapter;
    private LinearLayoutManager manager;

    private List<ShowListBean> listBeans;
    private List<LibInformationBean>libInformationBeans;
    private Gson gson;
    private ShowListBean showListBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);//初始化控件
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OnClick();//添加监听事件
        getLibInformationBeanJson();
    }

    private void initView(View view){
        gson=new Gson();

        listBeans =new ArrayList<>();

        textSearch=view.findViewById(R.id.tv_search);
        layoutSearch=view.findViewById(R.id.ll_search);
        toolbar=view.findViewById(R.id.toolbar);
        rootView = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        adapter=new ShowRecyclerViewAdapter(getContext(), listBeans);
        recyclerView=view.findViewById(R.id.recycler_scan);
        manager=new LinearLayoutManager(getActivity());

        //设置全屏透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewCompat.setFitsSystemWindows(rootView, false);
            rootView.setClipToPadding(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //设置toolbar初始透明度为0
        toolbar.getBackground().mutate().setAlpha(0);
        losePoint(textSearch);

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void OnClick(){

        //搜索框点击事件
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand){
                    expand();
                    isExpand = true;
                }else if(isExpand){
                    reduce();
                    isExpand = false;
                }

            }
        });

        textSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand){
                    expand();

                    isExpand = true;
                }else if(isExpand){
                    reduce();
                    isExpand = false;
                }

            }
        });

        adapter.setOnItemClickClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                IntentTools.getInstance().intent(getContext(),Show_Details.class,null);
            }
        });
    }

    private void expand() {
        //设置伸展状态时的布局
        textSearch.setHint("搜索你想搜索的内容");
        searchPoint(textSearch);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) layoutSearch.getLayoutParams();
        LayoutParams.width = LayoutParams.MATCH_PARENT;
        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        layoutSearch.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(layoutSearch);
    }

    private void reduce() {
        //设置收缩状态时的布局
        textSearch.setHint("");
        losePoint(textSearch);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) layoutSearch.getLayoutParams();
        LayoutParams.width = dip2px(80);
        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        layoutSearch.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(layoutSearch);
    }

    private void beginDelayedTransition(ViewGroup view) {
        set = new AutoTransition();
        set.setDuration(300);
        TransitionManager.beginDelayedTransition(view, set);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

    private void searchPoint(View view){
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.findFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    private void losePoint(View view){
        view.setFocusable(false);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(textSearch.getWindowToken(), 0);
        }
    }

    private void getLibInformationBeanJson(){
        StringRequest request=new StringRequest(Request.Method.GET, Constance.url+"servlet/LibInformationServlet", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                libInformationBeans=gson.fromJson(s,new TypeToken<List<LibInformationBean>>(){}.getType());
                for (int i=0;i<libInformationBeans.size();i++){
                    showListBean =new ShowListBean();
                    showListBean.setContext(libInformationBeans.get(i).getLib_information());
                    requestBitmaps(libInformationBeans.get(i).getPicture_url(),i);
                    listBeans.add(showListBean);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                for (int i=0;i<3;i++){
                    showListBean =new ShowListBean();
                    showListBean.setContext("");
                    listBeans.add(showListBean);
                }
            }
        });

        request.setTag("getLibInformationBeanJson");
        MyApplication.getHttpQueues().add(request);
    }

    private void requestBitmaps(String url, final int i){
        ImageRequest request=new ImageRequest(Constance.url+url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                listBeans.get(i).setBitmap(bitmap);
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
