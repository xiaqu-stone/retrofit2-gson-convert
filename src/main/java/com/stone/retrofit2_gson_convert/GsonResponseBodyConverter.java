/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stone.retrofit2_gson_convert;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private static final String TAG = "GsonResponseBodyConvert";

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        String bodyString = value.string();
        JsonReader jsonReader = gson.newJsonReader(new StringReader(bodyString));
        try {
            return adapter.read(jsonReader);
        } catch (JsonSyntaxException e) {
            Log.e("GsonResponseConverter", "Gson SyntaxException，" + e.getMessage());
            try {
                //解析失败，重试只解析外层code,msg,data
                String data = new JSONObject(bodyString).optString("data");
                Log.w(TAG, "convert: " + data);
                Result result = gson.fromJson(bodyString, Result.class);
                result.setData(data);
                Log.w(TAG, "convert: " + result);
                return (T) result;
            } catch (JsonSyntaxException e1) {
                Log.e("GsonResponseConverter", "Gson SyntaxException，" + e1.getMessage());
            } catch (JSONException e1) {
                Log.e("GsonResponseConverter", "the bodyString is invalid json" + e1.getMessage());
            }
            //仍然失败，将字符串包装到Result的data中返回
            return (T) new Result(-1, "Response json is error", bodyString);
        } finally {
            value.close();
        }
    }
}