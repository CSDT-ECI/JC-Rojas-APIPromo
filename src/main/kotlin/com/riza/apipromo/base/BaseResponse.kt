package com.riza.apipromo.base

data class BaseResponse<T>(
    var message: String? = "Success",
    var success: Boolean = true,
    var data: T? = null,
)
