package com.AndroidAssistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AidlService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    // 要调用的方法
    public boolean customMethod(String name){
        if (name.endsWith("张三")){
            return true;
        }else {
            return false;
        }
    }

    // 自定义binder
    private class MyBinder extends IMyAidlInterface.Stub{
        public boolean callCustomMethod(String name){
            return customMethod(name);
        }
    }
}
