package com.hexamigos.aispaceserver.action.email

import com.google.gson.annotations.SerializedName

data class Email(
        @SerializedName("actionType") var actionType: String? = null,
        @SerializedName("to") var to: ArrayList<String> = arrayListOf(),
        @SerializedName("cc") var cc: ArrayList<String> = arrayListOf(),
        @SerializedName("sub") var sub: String? = null,
        @SerializedName("body") var body: String? = null

)
