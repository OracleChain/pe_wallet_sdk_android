package com.pocketeco.sample.peopensdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by pocketEos on 2019/02/27.
 *
 */

public class PEManager {

    private static PEManager sInstance;
    private PEListener mListener;
    //回调的状态
    private final static int SUCCESS = 1;
    private final static int CANCEL = 0;
    private final static int ERROR = 2;


    private PEManager() {

    }

    public static PEManager getInstance() {
        if (sInstance == null) {
            synchronized (PEManager.class) {
                if (sInstance == null) {
                    sInstance = new PEManager();
                }
            }
        }
        return sInstance;
    }

    private void setPEListener(PEListener listener) {
        this.mListener = listener;
    }

    /**
     * 执行操作
     */
    private void doAction(Context context, String action ,String param, PEListener listener) {
        //设置监听器
        setPEListener(listener);
        pullUpPE(context,action, param);
    }

    /**
     * 转账
     */
    public void transfer(Context context, String transferData) {
        transfer(context, transferData, null);
    }

    /**
     * 转账
     */
    public void transfer(Context context, String transferData, PEListener listener) {
        doAction(context,"transfer",transferData, listener);
    }

    /**
     * 提交交易
     */
    public void pushTransactions(Context context, String transactionData) {
        pushTransactions(context, transactionData, null);
    }

    /**
     * 提交交易
     */
    public void pushTransactions(Context context, String transactionData, PEListener listener) {
        doAction(context, "pushTransactions",transactionData, listener);
    }

    /**
     * 授权登陆
     */
    public void authLogin(Context context, String authData) {
        authLogin(context, authData, null);
    }

    /**
     * 授权登陆
     */
    public void authLogin(Context context, String authData, PEListener listener) {
        doAction(context,"authLogin", authData, listener);
    }

    /**
     * 解析数据，并回调！
     */
    public void parseIntent(Intent intent) {
        if (mListener == null) {
            return;
        }

        int status = intent.getIntExtra("status", 0);
        String result = intent.getStringExtra("result");
        if (result == null) {
            mListener.onError("Unknown error");
            return;
        }

        switch (status) {
            case ERROR:
                mListener.onError(result);
                break;
            case CANCEL:
                mListener.onCancel(result);
                break;
            case SUCCESS:
            default:
                mListener.onSuccess(result);
                break;
        }
    }

    /**
     * 拉起PE
     * action代表执行的操作
     */
    private void pullUpPE(Context context,  String action ,String param) {
        Intent intent = new Intent();
        //传递包名、类名、app名
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", PEAssistActivity.class.getName());
        intent.putExtra("appName", PEUtil.getAppName(context));
        //拼凑uri
        intent.setData(getParamUri(action,param));
        intent.setAction(Intent.ACTION_VIEW);
        //保证新启动的APP有单独的堆栈，如果希望新启动的APP和原有APP使用同一个堆栈则去掉该项
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onCancel("Please install PocketECO or upgrade to the latest version");
            }
        }
    }

    /**
     * 获取uri
     * action ,需要执行的操作
     */
    private Uri getParamUri(String action ,String param) {
        //将param encode处理
        try {
            param = URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String temp = null;
        if (action.equals("authLogin")){
            temp = Constant.PE_SCHEME_HOST_LOGIN + "?param=" + param;
        }else if (action.equals("pushTransactions")){
            temp =  Constant.PE_SCHEME_HOST_PUSH + "?param=" + param;
        }else if(action.equals("transfer")){
            temp =  Constant.PE_SCHEME_HOST_TRANSFER + "?param=" + param;
        }
        return Uri.parse(temp);
    }

}
