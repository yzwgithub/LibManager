package andios.org.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import andios.org.R;
import andios.org.adapter.SearchLibActivityAdapter;

public class SearchLibActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private SearchLibActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lib);
        init();
    }
    private void init(){
        adapter=new SearchLibActivityAdapter(SearchLibActivity.this);
        recyclerView=findViewById(R.id.search_lib_list);
        manager=new LinearLayoutManager(SearchLibActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(SearchLibActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("search_function_01");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchLibActivity.this.finish();
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_function_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_function_01:
                Toast.makeText(this, "search_function_01", Toast.LENGTH_SHORT).show();
                toolbar.setTitle("search_function_01");
                break;
            case R.id.search_function_02:
                Toast.makeText(this, "search_function_02", Toast.LENGTH_SHORT).show();
                toolbar.setTitle("search_function_02");
                break;
            case R.id.search_function_03:
                Toast.makeText(this, "search_function_03", Toast.LENGTH_SHORT).show();
                toolbar.setTitle("search_function_03");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
