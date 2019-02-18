package andios.org.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import andios.org.R;
import andios.org.tool.IntentTools;
import andios.org.tool.PictureUtil;
import andios.org.tool.ProgressDialogUtil;
import andios.org.custom_view.CircleImageView;
import andios.org.tool.Constance;
import andios.org.appplection.MyApplication;
import andios.org.tool.SharedHelper;

import static andios.org.tool.Constance.picture_path;
import static andios.org.tool.Constance.user_id;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener ,CompoundButton.OnCheckedChangeListener,AdapterView.OnItemSelectedListener {
    private EditText user_name;
    private EditText password;
    private Button login;
    private TextView forget_password;
    private CheckBox checkBox1,checkBox2;
    private SharedHelper sharedHelper;
    private Context context;
    private Spinner spinner;
    private List<String> list;
    private ArrayAdapter adapter;
    private CircleImageView circleImageView;
    private PictureUtil pictureUtil;

    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    /** 首先默认个文件保存路径 */
    private static final String SAVE_REAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/good/savePic";//保存的确切位置

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * 初始化控件
     */
    private void init(){
        initData();

        pictureUtil=new PictureUtil();

        user_name=findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        checkBox1=findViewById(R.id.remember);
        checkBox2=findViewById(R.id.self_login);
        forget_password=findViewById(R.id.login_forget_password);
        spinner = findViewById(R.id.user_id);
        circleImageView=findViewById(R.id.u_logo);

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        context=getApplicationContext();
        sharedHelper=new SharedHelper(context);

        login.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        circleImageView.setOnClickListener(this);
    }

    private void initData(){
        list=new ArrayList<>();
        list.add("请选择登录者身份");
        list.add("管理员");
        list.add("教师");
        list.add("学生");
    }

    /**
     * 设置监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String url= Constance.url+"servlet/LoginServlet?";
                String u_username = user_name.getText().toString();
                String u_password = password.getText().toString();
                String u_id=String.valueOf(spinner.getSelectedItemPosition());
                if(u_id.equals("0")){
                    Toast.makeText(this, "请选择用户身份！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (checkBox2.isChecked()){
                    sharedHelper.save(u_id,u_username,u_password,picture_path);
                }
                login(url,u_id,u_username,u_password);
                break;
            case R.id.login_forget_password:
                IntentTools.getInstance().intent(LoginActivity.this,ForgetActivity.class,null);
                break;
            case R.id.u_logo:
                createDialog();
                break;
        }
    }

    private void login(String url,final String userId,final String userName, final String password){
        ProgressDialogUtil.showProgressDialog(LoginActivity.this,"登录中请稍后...");
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                if (s.equals("200")){
                    ProgressDialogUtil.dismiss();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else if(s.equals("404")) {
                    ProgressDialogUtil.dismiss();
                    Toast.makeText(LoginActivity.this,"用户名或密码输入错误",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ProgressDialogUtil.dismiss();
                Toast.makeText(LoginActivity.this,"网络连接超时，请检查您的网络设置！",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("user_id",userId);
                map.put("user_name",userName);
                map.put("password",password);
                return map;
            }
        };
        request.setTag("LoginActivity");
        MyApplication.getHttpQueues().add(request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String> data=sharedHelper.read();
        user_name.setText(data.get("username"));
        password.setText(data.get("password"));//如果没有设置密码，值为“”，不为null。
        if (!data.get("username").equals("")){
            spinner.setSelection(Integer.parseInt(data.get("user_id")),true);
            checkBox1.setChecked(true);
            checkBox2.setChecked(true);
        }
        if (!data.get("picture_path").equals("")){
            circleImageView.setImageBitmap(pictureUtil.getLocalBitmap(data.get("picture_path")));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String u_username = user_name.getText().toString();
        String p_password = password.getText().toString();
        switch (buttonView.getId()){
            case R.id.remember:
                if (isChecked){
                    checkBox2.setChecked(true);
                    user_id=String.valueOf(spinner.getSelectedItemPosition());
                    sharedHelper.save(user_id,u_username, p_password,picture_path);
                }else {
                    checkBox2.setChecked(false);
                    sharedHelper.save(null,null, null,null);
                }
                break;
            case R.id.self_login:
                if (isChecked){
                    checkBox2.setChecked(true);
                    user_id=String.valueOf(spinner.getSelectedItemPosition());
                    sharedHelper.save(user_id,u_username, p_password,picture_path);
                }else {
                    checkBox2.setChecked(false);
                    sharedHelper.save(null,null, null,null);
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        user_id=String.valueOf(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 弹出对话框
     */
    public void createDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择");
        builder.setItems(new String[]{"启动照相机", "打开手机相册", "取消选择"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                switch (arg1) {
                    case 0:
                        camera();
                        break;
                    case 1:
                        gallery();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 从相机获取
     */
    public void camera() {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    PHOTO_FILE_NAME);
            // 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /*
     * 判断sdcard是否被挂载
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                pictureUtil.saveImageToGallery(LoginActivity.this,bitmap);
                circleImageView.setImageBitmap(bitmap);
                sharedHelper.save(picture_path);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
