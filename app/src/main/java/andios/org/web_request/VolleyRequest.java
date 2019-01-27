package andios.org.web_request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import andios.org.appplection.MyApplication;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/13
 */

public class VolleyRequest {
    public static StringRequest mStringRequest;

    /**
     * get请求
     * @param url
     * @param tag
     * @param vif
     */
    public static void requestGet(String url,String tag,VolleyInterface vif){
        MyApplication.getHttpQueues().cancelAll(tag);
        mStringRequest=new StringRequest(Request.Method.GET,url,vif.loadingListener(),vif.errorListener());
        mStringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(mStringRequest);
        MyApplication.getHttpQueues().start();
    }

    /**
     * post请求（带有map传递参数）
     * @param url
     * @param tag
     * @param params
     * @param vif
     */
    public static void requestPost(String url, String tag, final Map<String, String> params,VolleyInterface vif){
        MyApplication.getHttpQueues().cancelAll(tag);
        mStringRequest = new StringRequest(Request.Method.POST,url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println("params:"+params);
                return params;
            }
        };

        mStringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(mStringRequest);
        MyApplication.getHttpQueues().start();

    }

    /**
     * post请求(带有参数map 重写传递参数方法)
     * @param url 地址
     * @param tag  标签
     * @param params 参数
     * @param vif 接口
     * void
     *
     */
    public static void requestSpecPost(String url, String tag, final Map<String, String> params,
                                       VolleyInterface vif) {
        MyApplication.getHttpQueues().cancelAll(tag);
        mStringRequest = new StringRequest(Request.Method.POST,url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println("params:"+params);
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = getParams();
                if (params != null && params.size() > 0) {
                    try {
                        return params.get("data").getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        mStringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(mStringRequest);
        MyApplication.getHttpQueues().start();
    }
}
