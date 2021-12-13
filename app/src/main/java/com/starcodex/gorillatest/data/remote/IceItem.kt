package com.starcodex.gorillatest.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IceItem (
    @SerializedName("name")
    @Expose
    var name: String = "",
    @SerializedName("price")
    @Expose
    var price: String = "",
    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String = ""
)