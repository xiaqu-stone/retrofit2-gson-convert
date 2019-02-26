



### gradle

```
    implementation "com.sqq.xiaqu:retrofit2-gson-convert:1.0.1"
    
    //内部依赖，建议在主工程中添加
    implementation "com.google.code.gson:gson:$gson"
    implementation "com.squareup.okhttp3:okhttp:$okhttp"
    implementation "com.squareup.retrofit2:retrofit:$retrofit"
```


### 默认外层解析逻辑：Result

```
data class Result<T>(val code: Int, @SerializedName("msg", alternate = ["message"]) val msg: String, val data: T)
```



