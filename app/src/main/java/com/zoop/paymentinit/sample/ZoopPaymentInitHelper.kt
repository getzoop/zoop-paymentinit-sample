package com.zoop.paymentinit.sample

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import com.zoop.sdk.Zoop
import com.zoop.sdk.plugin.paymentinit.PaymentInitPlugin
import com.zoop.sdk.plugin.paymentinit.model.PaymentInitFailResponse
import com.zoop.sdk.plugin.paymentinit.model.PaymentInitSuccessResponse
import com.zoop.sdk.type.Environment

object ZoopPaymentInitHelper {
    fun init(context: Context) {
        val result = Zoop.initialize(context) {
            credentials {
                marketplace = BuildConfig.MARKETPLACE_ID
                seller = BuildConfig.SELLER_ID
                terminal = "paymentinit-sample-1"
                accessKey = BuildConfig.ACCESS_KEY
            }
        }
        Log.d("PaymentInitHelper", "Zoop.initialize: $result")

        Zoop.setEnvironment(Environment.Production)
        Zoop.setStrict(false)

        Zoop.make<PaymentInitPlugin>().apply {
            configureTheme {
//                playbook.primaryColor = R.color.
//                playbook.textPrimaryColor = R.color.
//                playbook.accentColor = R.color.green
            }
        }.run(Zoop::plug)
    }
//
    fun pay(
        activity: ComponentActivity,
        amount: Long,
        personalId: String,
        onSuccess: (PaymentInitSuccessResponse) -> Unit,
        onFail: (PaymentInitFailResponse) -> Unit
    ) {
        PaymentInitPlugin.createPaymentRequest(
            activity = activity,
            amount = amount,
            personalId = personalId,
            onSuccess = onSuccess,
            onFail = onFail
        ).apply(Zoop::post)
    }

}