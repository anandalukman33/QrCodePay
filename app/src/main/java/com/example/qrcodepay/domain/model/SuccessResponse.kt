package com.example.qrcodepay.domain.model

import com.google.gson.annotations.SerializedName

data class SuccessResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("sending")
	val sending: Boolean
)
