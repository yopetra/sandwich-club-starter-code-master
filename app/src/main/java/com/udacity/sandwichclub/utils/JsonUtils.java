package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String LOG_TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        if(TextUtils.isEmpty(json)){
            Log.w(LOG_TAG, "Json is empty!");
            return null;
        }

        String sMainName = null;
        List<String> sAlsoKnownAs = null;
        String sPlaceOfOrigin = null;
        String sDescription = null;
        String sImage = null;
        List<String> sIngredients = null;

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONObject jsonName = jsonObject.getJSONObject("name");
            sMainName = jsonName.getString("mainName");
            JSONArray arrAlsoKnownAs = jsonName.getJSONArray("alsoKnownAs");
            sAlsoKnownAs = convertJsonArr2StringArr(arrAlsoKnownAs);
            sPlaceOfOrigin = jsonObject.getString("placeOfOrigin");
            sDescription = jsonObject.getString("description");
            sImage = jsonObject.getString("image");
            JSONArray arrIngredients = jsonObject.getJSONArray("ingredients");
            sIngredients = convertJsonArr2StringArr(arrIngredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(sMainName, sAlsoKnownAs, sPlaceOfOrigin, sDescription, sImage, sIngredients);
    }

    private static List<String> convertJsonArr2StringArr(JSONArray jsonArray){
        if(TextUtils.isEmpty(jsonArray.toString())){
            return null;
        }

        List<String> listOfStrings = new ArrayList<>();

        int size = jsonArray.length();
        for(int i = 0; i < size; i++){
            try {
                String s = jsonArray.getString(i);
                listOfStrings.add(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listOfStrings;
    }
}
