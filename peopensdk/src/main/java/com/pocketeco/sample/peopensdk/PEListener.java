package com.pocketeco.sample.peopensdk;

/**
 * Created by pocketEos on 2019/02/27.
 *
 */

public interface PEListener {

    void onSuccess(String data);

    void onError(String data);

    void onCancel(String data);
}
