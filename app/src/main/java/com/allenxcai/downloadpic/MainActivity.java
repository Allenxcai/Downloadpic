package com.allenxcai.downloadpic;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private String path="http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20180929/E-it-hhuhisn5626936.jpg";
    private TestHandler mHandler;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.iv_image);
        mHandler =new TestHandler(this);
        findViewById(R.id.btn_dl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new GetPicThread(path, mHandler)).start();

            }
        });
    }

    public static class TestHandler extends Handler{

        public final WeakReference<MainActivity> mWeakReference;
        public TestHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mWeakReference.get();
            activity.imageView.setImageResource(R.drawable.ic_launcher_background);


        }
    }
}
