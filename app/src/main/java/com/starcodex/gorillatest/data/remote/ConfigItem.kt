package com.starcodex.gorillatest.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ConfigItem (
    @SerializedName("storeName")
    @Expose
    var storeName: String? = null,
    @SerializedName("colors")
    @Expose
    var colors: ConfigColors? = null
)
class ConfigColors(
    @SerializedName("main")
    @Expose
    var main: String? = null,
    @SerializedName("secondary")
    @Expose
    var secondary: String? = null
)