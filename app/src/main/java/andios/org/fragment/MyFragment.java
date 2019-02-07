package andios.org.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import andios.org.R;
import andios.org.activity.Login;
import andios.org.adapter.MineItemAdapter;
import andios.org.custom_view.CircleImageView;

public class MyFragment extends Fragment {
    private ListView listView;
    private CircleImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        init(view);
        return view;
    }

    private void listener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(getContext(), "点击了第"+position+"个item", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(), "点击了第1个item", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "点击了第2个item", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Login.class);
                startActivity(intent);
            }
        });
    }
    private void init(View view){
        imageView=view.findViewById(R.id.mine_logo);
        listView=view.findViewById(R.id.list_item);
        MineItemAdapter mineItemAdapter =new MineItemAdapter(view.getContext());
        listView.setAdapter(mineItemAdapter);
        listener();
    }


}
