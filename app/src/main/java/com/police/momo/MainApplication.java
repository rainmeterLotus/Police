package com.police.momo;

import android.app.Application;
import android.os.Handler;

import com.police.momo.bean.BasicInfo;
import com.police.momo.bean.Question;
import com.police.momo.util.AssetsCopyer;
import com.police.momo.util.SDCardUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2015/9/25.
 */
public class MainApplication extends Application {
    private Handler currentHandler;
    private static final String TAG = "MainApplication";
    private static MainApplication mInstance = null;
    private BasicInfo info;
    private List<Question> questions;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
//        copyFiles();
    }

    /**
     * 向手机上copyPDF文件
     */
    private void copyFiles() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                AssetsCopyer copyer = new AssetsCopyer(mInstance);
                try {
                    copyer.copy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public List<Question> getQuestions() {
        if (null == questions) {
            questions = new ArrayList<>();
        }
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public BasicInfo getInfo() {
        if (null == info) {
            info = new BasicInfo();
        }
        return info;
    }

    public void setInfo(BasicInfo info) {
        this.info = info;
    }

    private void init() {
        //初始化PDF文件
        if (SDCardUtil.externalMemoryAvailable()) {
            File dirFile = new File(Constant.DIRCONFIG.ROOT_PATH);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }
    }

    public static MainApplication getInstance() {
        return mInstance;
    }

    public Handler getCurrentHandler() {
        return currentHandler;
    }

    public void setCurrentHandler(Handler currentHandler) {
        this.currentHandler = currentHandler;
    }
}
