package andios.org;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.Map;
import andios.org.activity.MainActivity;
import andios.org.tool.SharedHelper;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/15
 */

public class AppStartActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.app_start, null);
        setContentView(view);
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(5000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {

            }
            @Override
            public void onAnimationStart(Animation arg0) {

            }

        });
    }
    protected void redirectTo() {
        sharedHelper=new SharedHelper(AppStartActivity.this);
        Map<String,String> data=sharedHelper.read();
        Intent intent;
        intent= new Intent(this, MainActivity.class);//测试
//        if (!data.get("username").equals("")||!data.get("password").equals("")){
//            intent= new Intent(this, MainActivity.class);
//        }else intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
