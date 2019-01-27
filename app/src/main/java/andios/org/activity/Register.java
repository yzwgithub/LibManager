package andios.org.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import andios.org.R;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Init();
    }
    void Init(){
        imageView=findViewById(R.id.back);
        register=findViewById(R.id.register);
        register.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent intent_back=new Intent(Register.this,Login.class);
                startActivity(intent_back);
                Register.this.finish();
                break;
            case R.id.register:
                Intent intent_register=new Intent(Register.this,MainActivity.class);
                startActivity(intent_register);
                Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                Register.this.finish();
                break;
        }
    }
}
