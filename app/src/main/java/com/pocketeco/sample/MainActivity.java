package com.pocketeco.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pocketeco.sample.peopensdk.PEListener;
import com.pocketeco.sample.peopensdk.PEManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //transfer
        findViewById(R.id.tv_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PEManager.getInstance().transfer(MainActivity.this, getTransferData(), new PEListener() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //pushTransactions
        findViewById(R.id.tv_push_transactions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PEManager.getInstance().pushTransactions(MainActivity.this, getPushTransactionData(), new PEListener() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //authLogin
        findViewById(R.id.tv_auth_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PEManager.getInstance().authLogin(MainActivity.this, getAuthLoginData(), new PEListener() {
                    @Override
                    public void onSuccess(String data) {Log.i("==========>\n",data);
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    /**
     * 按照协议构建json 字符串
     */
    private String getTransferData() {
        return "{\n" +
                "\t\"amount\": \"1.0001\",\n" +
                "\t\"contract\": \"octtothemoon\",\n" +
                "\t\"from\": \"oraclechain4\",\n" +
                "\t\"memo\": \"hi\",\n" +
                "\t\"precision\": 4,\n" +
                "\t\"symbol\": \"OCT\",\n" +
                "\t\"to\": \"test1111\",\n" +
                "\t\"action\": \"transfer\",\n" +
                "\t\"blockchain\": \"eosType\",\n" +
                "\t\"callbackUrl\": \"https://newdex.io/api/account/walletVerify\",\n" +
                "\t\"dappIcon\": \"http://newdex.io/static/logoicon.png\",\n" +
                "\t\"dappName\": \"Newdex\",\n" +
                "\t\"desc\": \"transfer\",\n" +
                "\t\"protocol\": \"pe_sdk\",\n" +
                "\t\"serialNumber\": \"app_123452312121\",\n" +
                "\t\"version\": \"1.0\"\n" +
                "}";
    }

    private String getPushTransactionData() {
        return "{\n" +
                "\t\"actions\": [{\n" +
                "\t\t\"account\": \"eosio.token\",\n" +
                "\t\t\"authorization\": [{\n" +
                "\t\t\t\"actor\": \"oraclechain4\",\n" +
                "\t\t\t\"permission\": \"active\"\n" +
                "\t\t}],\n" +
                "\t\t\"data\": {\n" +
                "\t\t\t\"from\": \"oraclechain4\",\n" +
                "\t\t\t\"to\": \"testtesttest\",\n" +
                "\t\t\t\"quantity\": \"0.0001 EOS\",\n" +
                "\t\t\t\"memo\": \"pushTransactions111\"\n" +
                "\t\t},\n" +
                "\t\t\"name\": \"transfer\"\n" +
                "\t}, {\n" +
                "\t\t\"account\": \"eosio.token\",\n" +
                "\t\t\"authorization\": [{\n" +
                "\t\t\t\"actor\": \"oraclechain4\",\n" +
                "\t\t\t\"permission\": \"active\"\n" +
                "\t\t}],\n" +
                "\t\t\"data\": {\n" +
                "\t\t\t\"from\": \"oraclechain4\",\n" +
                "\t\t\t\"to\": \"testtesttest\",\n" +
                "\t\t\t\"quantity\": \"0.0001 EOS\",\n" +
                "\t\t\t\"memo\": \"pushTransactions222\"\n" +
                "\t\t},\n" +
                "\t\t\"name\": \"transfer\"\n" +
                "\t}],\n" +
                "\t\"action\": \"pushTransactions\",\n" +
                "\t\"blockchain\": \"eosType\",\n" +
                "\t\"callbackUrl\": \"https://newdex.io/api/account/walletVerify\",\n" +
                "\t\"dappIcon\": \"http://newdex.io/static/logoicon.png\",\n" +
                "\t\"dappName\": \"Newdex\",\n" +
                "\t\"desc\": \"pushActions\",\n" +
                "\t\"protocol\": \"pe_sdk\",\n" +
                "\t\"serialNumber\": \"app_123452312121\",\n" +
                "\t\"version\": \"1.0\"\n" +
                "}";
    }

    public String getAuthLoginData() {
        return "{\n" +
                "\t\"action\": \"login\",\n" +
                "\t\"blockchain\": \"eosType\",\n" +
                "\t\"callbackUrl\": \"https://newdex.io/api/account/walletVerify\",\n" +
                "\t\"dappIcon\": \"http://newdex.io/static/logoicon.png\",\n" +
                "\t\"dappName\": \"Newdex\",\n" +
                "\t\"desc\": \"login\",\n" +
                "\t\"protocol\": \"pe_sdk\",\n" +
                "\t\"serialNumber\": \"app_123452312121\",\n" +
                "\t\"version\": \"1.0\"\n" +
                "}";
    }



}
