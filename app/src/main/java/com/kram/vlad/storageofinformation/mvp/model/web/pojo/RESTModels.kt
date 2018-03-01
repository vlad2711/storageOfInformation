package com.kram.vlad.storageofinformation.mvp.model.web.pojo

import com.kram.vlad.storageofinformation.models.SignUpModel
import com.kram.vlad.storageofinformation.models.LogInModel

/**
 * Created by vlad on 10.02.2018.
 * Models for rest api
 */

class RESTModels{
    data class SignUpModelResponse(val signUpModel: SignUpModel, val result: String)
    data class NotationAddResponse(val logInModel: LogInModel, val result: String)
    data class LogInModelResponse(val logInModel: LogInModel, val result: String)
    data class NotationResponse(val response: List<NotationResponseItem>)
    data class NotationResponseItem(val id: Int, val email: String, val notation: String)
}