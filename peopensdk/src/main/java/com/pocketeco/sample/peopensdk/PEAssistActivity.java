package com.pocketeco.sample.peopensdk;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by pocketEos on 2019/02/27.
 */

public class PEAssistActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在回调的activity处理逻辑
        PEManager.getInstance().parseIntent(getIntent());
        this.finish();
    }

}
