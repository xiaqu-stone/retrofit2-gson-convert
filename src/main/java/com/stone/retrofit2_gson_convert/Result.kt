package com.stone.retrofit2_gson_convert

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Result<T>(val code: Int, @SerializedName("msg", alternate = ["message"]) val msg: String, var data: T)