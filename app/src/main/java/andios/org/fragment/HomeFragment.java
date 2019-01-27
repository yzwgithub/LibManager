package andios.org.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import andios.org.R;
import andios.org.adapter.HomeRecyclerAdapter;
import andios.org.bean.HomeListBean;
import andios.org.interfice.OnItemClickListener;
import andios.org.view.Banner;
import andios.org.view.RoundView;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter;
    private List<HomeListBean>listBeans;

    private LinearLayoutManager manager;
    private List bannerList;
    private RoundView one,two,three;
    private Banner banner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        loadData();
        initView(view);
        return view;
    }

    private void initView(View view){
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

        OnClick();//设置监听事件

    }

    private void OnClick(){
        one.setOnButtonListerner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "第一个", Toast.LENGTH_SHORT).show();
            }
        });
        two.setOnButtonListerner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "第二个", Toast.LENGTH_SHORT).show();
            }
        });
        three.setOnButtonListerner(new View.OnClickListener() {
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

    private void loadData(){
        listBeans=new ArrayList<>();
        for (int i=0;i<40;i++){
            HomeListBean bean=new HomeListBean();
            bean.setTitle("第"+i+"张图片");
            bean.setContent("第"+i+"张是可爱的“椿”的卡通头像");
            listBeans.add(bean);
        }
    }
}
