package andios.org.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Method;

import andios.org.R;

public class Home_Details extends AppCompatActivity{

    private Toolbar toolbar;
    private boolean isSaved=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home__details_activity);
        init();
    }
    private void init(){
        String title=getIntents();
        toolbar=findViewById(R.id.home_details_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_Details.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appointment_details_menu,menu);
        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Toast.makeText(this, "分享...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_save:
                if (!isSaved){
                    item.setIcon(R.mipmap.has_saved);
                    isSaved=true;
                    Toast.makeText(this, "收藏...", Toast.LENGTH_SHORT).show();
                }else{
                    item.setIcon(R.mipmap.save);
                    isSaved=false;
                    Toast.makeText(this, "取消收藏...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getIntents(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String title=bundle.getString("title");
        return title;
    }
}
