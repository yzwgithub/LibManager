package andios.org.fragment;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import andios.org.R;
import andios.org.activity.LoginActivity;
import andios.org.activity.MyStoreActivity;
import andios.org.activity.ResetActivity;
import andios.org.adapter.MineItemAdapter;
import andios.org.custom_view.CircleImageView;
import andios.org.tool.IntentTools;
import andios.org.tool.SharedHelper;

public class MineFragment extends Fragment {
    private ListView listView;
    private CircleImageView imageView;
    private SharedHelper sharedHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    private void listener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        IntentTools.getInstance().intent(getContext(),MyStoreActivity.class,null);
                        break;
                    case 1:
                        IntentTools.getInstance().intent(getContext(),ResetActivity.class,null);
                        break;
                    case 2:
                        sharedHelper.save("0","","","");
                        getActivity().finish();
                        break;
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(),LoginActivity.class,null);
            }
        });
    }
    private void init(View view){
        imageView=view.findViewById(R.id.mine_logo);
        listView=view.findViewById(R.id.list_item);
        MineItemAdapter mineItemAdapter =new MineItemAdapter(view.getContext());
        listView.setAdapter(mineItemAdapter);

        sharedHelper=new SharedHelper(getContext());
        listener();
    }


}
