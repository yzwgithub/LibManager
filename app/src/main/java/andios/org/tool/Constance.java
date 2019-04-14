package andios.org.tool;

import java.io.UnsupportedEncodingException;

/**
 * Created by ZheWenYang on 2018/2/5.
 */

public class Constance {
    public static final String url = "http://yangzhewen.utools.club/";
    public static String user_id="0";
    public static String picture_path="";
    public static int user_index=0;

    public static String getChar(String s){
        try {
            return new String(s.getBytes("ISO-8859-1"),"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }
}
