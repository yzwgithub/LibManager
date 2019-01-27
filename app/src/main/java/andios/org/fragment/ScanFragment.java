package andios.org.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import andios.org.R;
import andios.org.adapter.ScanRecyclerViewAdapter;
import andios.org.bean.ScanListBean;
import andios.org.interfice.OnItemClickListener;

public class ScanFragment extends Fragment {

    private EditText textSearch;
    private LinearLayout layoutSearch;
    private Toolbar toolbar;
    boolean isExpand=false;
    private TransitionSet set;
    private ViewGroup rootView;
    private InputMethodManager imm;
    private RecyclerView recyclerView;
    private ScanRecyclerViewAdapter adapter;
    private LinearLayoutManager manager;
    private List<ScanListBean>list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_fragment, container, false);
        setListData();//加载RecyclerView的数据
        initView(view);//初始化控件
        return view;
    }

    private void initView(View view){
        textSearch=view.findViewById(R.id.tv_search);
        layoutSearch=view.findViewById(R.id.ll_search);
        toolbar=view.findViewById(R.id.toolbar);
        rootView = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        adapter=new ScanRecyclerViewAdapter(getContext(),list);
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

        OnClick();//添加监听事件

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
                Toast.makeText(getActivity(), "点击了第"+position+"个item", Toast.LENGTH_SHORT).show();
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

    void beginDelayedTransition(ViewGroup view) {
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
    private void setListData(){
        list=new ArrayList<>();
        for (int i=0;i<10;i++){
            ScanListBean bean=new ScanListBean();
            bean.setContext("第"+i+"张是“椿”与可爱的的“鲲”在雨中翩翩起舞的情景！");
            list.add(bean);
        }
    }
}