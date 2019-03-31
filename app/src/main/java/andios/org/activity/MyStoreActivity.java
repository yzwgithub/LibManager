package andios.org.activity;

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

import andios.org.R;
import andios.org.adapter.MyStoreActivityAdapter;

public class MyStoreActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MyStoreActivityAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        init();
    }
    private void init(){
        adapter=new MyStoreActivityAdapter(MyStoreActivity.this);
        recyclerView=findViewById(R.id.my_store_list);
        manager=new LinearLayoutManager(MyStoreActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(MyStoreActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
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
}
