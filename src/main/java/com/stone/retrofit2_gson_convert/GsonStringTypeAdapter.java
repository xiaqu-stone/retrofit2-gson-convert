package com.stone.retrofit2_gson_convert;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created By: sqq
 * Created Time: 17/9/21 下午6:27.
 * 自定义Gson针对String类型解析的适配器
 */
public class GsonStringTypeAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null) {
            out.value("");
            return;
        }
        out.value(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        if (peek == JsonToken.NULL) {
            in.nextNull();
            return "";
        }
        if (peek == JsonToken.BOOLEAN) {
            return Boolean.toString(in.nextBoolean());
        }
        return in.nextString();

    }
}
