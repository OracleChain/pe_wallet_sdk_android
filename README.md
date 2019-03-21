# PocketECO Android SDK

该SDK用于拉起PE钱包，实现APP与PE，H5与PE之间相互调用，使用PE进行相关action操作。

**提示：该****SDK****仅仅支持****3.4.0****以上版本的****PE****钱包。**

## 导入

### 方式一：

1.在根目录的build.gradle中添加:

```
allprojects {
   repositories {
	        …
          maven { url 'https://jitpack.io' }
   }
}
```

2.在app下的build.gradle中添加:

```
dependencies {
    implementation 'com.github.oraclechain:pe_wallet_sdk_android:1.0.0'
}
```

### 方式二：

1:下载 [peopensdk.aar]()

2:导入project下的app中的libs中编译

3.在app下的build.gradle中添加:

```
android {  
    ...
     repositories{
        flatDir(dirs: 'libs')
    } 
}  
  
dependencies {  
    ...
   implementation(name:'peopensdk',ext:'aar') 
}
```

### 

## 使用

**目前支持以下操作：**

1. **transfer**: 拉起PE钱包转账，类似微信、支付宝转账;
2. **pushTransactions**: push actions 进行交易;
3. **authLogin**: 授权登陆.



## PE钱包的回调

调起PE钱包后，如需要监听结果，可使用PEListener监听回调：

```
new PEListener() {
    @Override
    public void onSuccess(String data) {
      //成功
    }

    @Override
    public void onError(String data) {
      //错误
    }

    @Override
    public void onCancel(String data) {
      //取消
    }
}
```

## 一. Transfer

App使用示例

```
 PEManager.getInstance().transfer(MainActivity.this, getTransferData(), new PEListener() {
                    @Override
                    public void onSuccess(String data) {
                        Log.i("onSuccess",data);
                        Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String data) {
                        Log.i("onError",data);
                        Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(String data) {
                        Log.i("onCancel",data);
                        Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show();
                    }
                });

```

H5使用示例

```
window.location.href="transfer://pull.activity?param='XXX'"
param是下面data实例UrlEncode编码之后的结果
由于钱包无法直接通知H5，H5需要轮询Dapp Server收到的callbackUrl结果，来判断处理结果。
```



Transfer Data 格式实例

```
{
	"amount": "1.0001",// 金额
	"contract": "octtothemoon",//合约
	"from": "oraclechain4",//转账人
	"memo": "hi",//memo
	"precision": 4,//精度
	"symbol": "OCT",//token
	"to": "testtesttest",//收款人
	"action": "transfer",// 转账时赋值为transfer
	"blockchain": "eosType",//底层“eosType、bosType”
	"callbackUrl": "https://newdex.io/api/account/walletVerify",//通知服务端结果的回调url，例如https://newdex.io/api/account/walletVerify?result=1&protocol=pe_sdk&ref=PocketECO&serialNumber=app_123452312121&action=transfer&version=1.0&timestamp=1553140313645&txID=xxx，其中result(0为用户取消，1为成功,2为失败)
	"dappIcon": "http://newdex.io/static/logoicon.png", // dapp图标Url，用于在钱包APP中展示
	"dappName": "Newdex",// dapp名字，用于在钱包APP中展示
	"desc": "transfer",// 交易的说明信息，钱包在付款UI展示给用户
	"protocol": "pe_sdk",// 协议名
	"serialNumber": "app_123452312121",//此次业务流水号，唯一标示
	"version": "1.0"//协议版本号
}
```

Transfer 成功后的回调示例

```
{
	"action": "transfer",
	"data": {
		"txID": "efd04f98fc6a4698e9e205a247886afe7dbb13c5a0ab3a4adc29d73bbf4c3426"
	},
	"message": "Success",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"timestamp": "1553140058177",
	"version": "1.0"
}
```

Transfer 取消后的回调示例

```
{
	"action": "transfer",
	"message": "Cancel",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"version": "1.0"
}
```

Transfer 异常后的回调示例

```
{
	"action": "transfer",
	"message": "ERROR:assertion failure with message: 6000031 TO_ACCOUNT_DOES_NOT_EXIST",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"timestamp": "1553140313645",
	"version": "1.0"
}
```

## 



## 二. pushTransactions

App使用示例

```
  PEManager.getInstance().pushTransactions(MainActivity.this, getPushTransactionData(),new 		PEListener() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(MainActivity.this, dataToast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String data) {
                        Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(String data) {
                        Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show();
                    }
                });
```

H5使用示例

```
window.location.href="push://pull.activity?param='XXX'"
param是下面data实例UrlEncode编码之后的结果
由于钱包无法直接通知H5，H5需要轮询Dapp Server收到的callbackUrl结果，来判断处理结果。
```



pushTransactions Data示例

```
{
	"actions": [{   // actions 数组 为EOS、BOS标准action内容
		"account": "eosio.token",
		"authorization": [{
			"actor": "oraclechain4",
			"permission": "active"
		}],
		"data": {
			"from": "oraclechain4",
			"to": "testtesttest",
			"quantity": "0.0001 EOS",
			"memo": "pushTransactions111"
		},
		"name": "transfer"
	}, {
		"account": "eosio.token",
		"authorization": [{
			"actor": "oraclechain4",
			"permission": "active"
		}],
		"data": {
			"from": "oraclechain4",
			"to": "testtesttest",
			"quantity": "0.0001 EOS",
			"memo": "pushTransactions222"
		},
		"name": "transfer"
	}],
	"action": "pushTransactions",
	"blockchain": "eosType",
	"callbackUrl": "https://newdex.io/api/account/walletVerify",
	"dappIcon": "http://newdex.io/static/logoicon.png",
	"dappName": "Newdex",
	"desc": "pushActions",
	"protocol": "pe_sdk",
	"serialNumber": "app_123452312121",
	"version": "1.0"
}
```

pushTransactions 成功后的回调示例

```
{
	"action": "transfer",
	"data": {
		"txID": "efd04f98fc6a4698e9e205a247886afe7dbb13c5a0ab3a4adc29d73bbf4c3426"
	},
	"message": "Success",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"timestamp": "1553140058177",
	"version": "1.0"
}
```

pushTransactions 取消后的回调示例

```
{
	"action": "transfer",
	"message": "Cancel",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"version": "1.0"
}
```

pushTransactions 异常后的回调示例

```
{
	"action": "transfer",
	"message": "ERROR:assertion failure with message: 6000031 TO_ACCOUNT_DOES_NOT_EXIST",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"timestamp": "1553140313645",
	"version": "1.0"
}
```

## 

## 三. authLogin

App使用示例

```
  PEManager.getInstance().authLogin(MainActivity.this, getAuthLoginData(), new  			PEListener() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String data) {
                        Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
```

H5使用示例

```
window.location.href="login://pull.activity?param='XXX'"
param是下面data实例UrlEncode编码之后的结果
由于钱包无法直接通知H5，H5需要轮询Dapp Server收到的callbackUrl结果，来判断处理结果。
```



authLogin Data示例

```
{
	"action": "login",
	"blockchain": "eosType",
	"callbackUrl": "https://newdex.io/api/account/walletVerify",
	"dappIcon": "http://newdex.io/static/logoicon.png",
	"dappName": "Newdex",
	"desc": "login",
	"protocol": "pe_sdk",
	"serialNumber": "app_123452312121",
	"version": "1.0"
}
```

authLogin 成功后的回调示例

```
{
	"action": "login",
	"data": {
		"account": "oraclechain4",
		"public_key": "EOS78rMUpjdDMJuKhMwSrZCdviBf2FB6BvF2bhMmGpiCJQuPwLSvK",
		"sign": "SIG_K1_KazMJhgxeZ7e7LR7sNHFZtbSxXEPUNyXg9gPuoZTxbieyFosoMXtQn8eGovSe2svLSCXpFkz2KVhGhrPnJPPzuupXKkn8E" //钱包对登录相关数据进行签名结果
	},
	"message": "Success",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"timestamp": "1553149375817",
	"version": "1.0"
}
```

钱包对登录相关数据进行签名

```
// 生成sign算法
let data = timestamp + account + serialNumber + ref   
sign = ecc.sign(data, privateKey)

接收到成功后的回调结果，需要验证sign签名数据，若验证成功，将该账户设为已登录状态。
```

authLogin 取消后的回调示例

```
{
	"action": "login",
	"message": "Cancel",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"version": "1.0"
}
```

authLogin 异常后的回调示例

```
{
	"action": "login",
	"message": "Error",
	"protocol": "pe_sdk",
	"ref": "PocketECO",
	"serialNumber": "app_123452312121",
	"version": "1.0"
}
```

## 
