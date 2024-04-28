package com.riza.apipromo.application.adapters.controller.responses

data class BaseResponse<T>(
    var message: String? = "Success",
    var success: Boolean = true,
    var data: T? = null,
)
