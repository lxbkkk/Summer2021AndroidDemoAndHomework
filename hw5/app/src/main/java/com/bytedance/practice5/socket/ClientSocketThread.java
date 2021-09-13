package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.bytedance.practice5.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;

    //head请求内容
    private static String content = "HEAD / HTTP/1.1\r\nHost:www.zju.edu.cn\r\n\r\n";


    @Override
    public void run() {
        // TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示

        super.run();
        Log.d("sockettt","out of try");

        try{
            Log.d("sockettt","try start");
            Socket socket = new Socket();
            String socketStr = Constants.BASE_URL + "messages";
            Log.d("sockettt","" + socketStr);
            //Socket socket = new Socket("api-android-camp.bytedance.com",30000);
            Log.d("sockettt","after new");
            socket.connect(new InetSocketAddress("www.zju.edu.cn",80),5000);
            Log.d("sockettt","after connect");

            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());

            byte[] data = new byte[1024*5];

            //while(socket.isConnected()){
                Log.d("sockettt","客户端发送" + content);
                os.write(content.getBytes());
                os.flush();
                int reciveLen = is.read(data);
                String receive = new String(data,0,reciveLen);
                Log.d("sockettt","客户端收到" + receive);
                callback.onResponse(receive);
            //}
            Log.d("sockettt","客户端断开");
            os.flush();
            os.close();
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
            Log.d("sockettt","in catch");
        }
    }
}