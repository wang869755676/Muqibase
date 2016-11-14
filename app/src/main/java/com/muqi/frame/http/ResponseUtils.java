package com.muqi.frame.http;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.muqi.frame.project.application.ActivitiesStack;
import com.utils.ShowToast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseUtils {

	@SuppressWarnings("unused")
	public static boolean isJsonObj(String strJson) {
		try {
			JSONObject getObj = new JSONObject(strJson.toString());
			if (getObj != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static JSONObject isResponseErr(Context context, String resultJson) {
		try {
			JSONObject resultAllata = null;
			resultAllata = new JSONObject(resultJson.toString());
			int errcode = resultAllata.getInt("errcode");
			String errdesc = resultAllata.getString("errdesc");
			if (errcode == 0) {
				return resultAllata;
			} else if (errcode == 90000) {
				ShowToast.showShort(context, errdesc);
				Intent intent = new Intent();
				//intent.setClass(context, LoginActivity.class);
				context.startActivity(intent);
				ActivitiesStack.getInstance().popAll(true);
			} else {
				ShowToast.showShort(context, errdesc);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 判断对api操作是否成功
	 *
	 * @param context
	 * @param resultJson
	 * @return
	 */
	public static boolean isOperationErr(Context context, String resultJson) {
		try {
			JSONObject resultAllata = null;
			resultAllata = new JSONObject(resultJson.toString());
			int errcode = resultAllata.getInt("errcode");
			String errdesc = resultAllata.getString("errdesc");
			if (errcode == 9000) {
				ShowToast.showShort(context, errdesc);
				Intent intent = new Intent();
				//intent.setClass(context, LoginActivity.class);
				context.startActivity(intent);
				ActivitiesStack.getInstance().popAll(true);
				return false;
			} else if (errcode == 0) {
				return true;
			} else {
				ShowToast.showShort(context, errdesc);
				return false;
			}
		} catch (Exception e) {

		}
		return false;
	}


}
