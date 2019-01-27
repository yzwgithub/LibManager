package andios.org.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import andios.org.R;

/**
 * 文件描述：
 * 作者：ZheWenYang
 * 创建时间：2019/1/9
 */

public class RoundView extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    public RoundView(Context context) {
        super(context);
    }

    public RoundView(Context context,AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.round_view,this);
        init();

    }

    public void init(){
        imageView=findViewById(R.id.round_image);
        textView=findViewById(R.id.round_text);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnButtonListerner(OnClickListener listerner){
        imageView.setOnClickListener(listerner);
    }

    public void setImage(int resource) {
        imageView.setImageResource(resource);
    }

    public void setText(String text){
        textView.setText(text);
    }
}
