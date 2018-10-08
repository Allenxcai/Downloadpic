package com.allenxcai.downloadpic;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Project:Downloadpic
 * Author:Allenxcai
 * Date:2018/10/8/008
 * Description:
 */
public class GetPicThread implements Runnable {
    /**
     *
     */
    Handler mHandler;
    String  mPath;

    public GetPicThread(String path, Handler handler) {
        mHandler=handler;
        mPath=path;
    }

    @Override
    public void run() {
         //下载图片
        download(mPath);

        //下载图片完成后发消息给主线程显示图片
        Message message = Message.obtain();
        message.what = 0x123;
        mHandler.sendMessage(message);


    }

    private void download(String appUrl) {
        try {
            URL url = new URL(appUrl);
            URLConnection urlConnection = url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            /**
             * 获取文件的总长度
             */

            int contentLength = urlConnection.getContentLength();

            String downloadFolderName = Environment.getExternalStorageDirectory()
                    + File.separator + "imooc" + File.separator;

            File file = new File(downloadFolderName);
            if (!file.exists()) {
                file.mkdir();
            }

            String fileName = downloadFolderName + "imooc.jpg";

            File apkFile = new File(fileName);

            if(apkFile.exists()){
                apkFile.delete();
            }

            int downloadSize = 0;
            byte[] bytes = new byte[1024];

            int length;

            OutputStream outputStream = new FileOutputStream(fileName);
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                downloadSize += length;
                /**
                 * update UI
                 */

                Message message = Message.obtain();
                message.what = 0x111;
                mHandler.sendMessage(message);

            }
            inputStream.close();
            outputStream.close();


        } catch (MalformedURLException e) {
            notifyDownloadFaild();
            e.printStackTrace();
        } catch (IOException e) {
            notifyDownloadFaild();
            e.printStackTrace();
        }
    }

    private void notifyDownloadFaild() {
        Message message = Message.obtain();
        message.what = 0x321;
        mHandler.sendMessage(message);
    }
}
