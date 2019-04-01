package andios.org.tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2017/8/31.
 */

public class SharedHelper {
    private Context mContext;

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }


    //定义一个保存数据的方法
    public void save(String user_id,String username, String password,String p_path) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_id",user_id);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("picture_path", p_path);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("user_id", sp.getString("user_id", ""));
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        data.put("picture_path", sp.getString("picture_path", ""));
        return data;
    }
    public int read_u_index(){
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        return sp.getInt("u_index", 1);
    }
    public void save(String p_path){
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("picture_path", p_path);
        editor.commit();
    }

    public void save(int u_index){
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("u_index", u_index);
        editor.commit();
    }
}
