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
                playbook.brandImage = R.mipmap.ic_launcher_round
                playbook.primaryColor = R.color.primary
                playbook.textPrimaryColor = R.color.text_primary
                playbook.accentColor = R.color.accent
            }
        }.run(Zoop::plug)
    }

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
            onFail = {
                println(it.reason)
                onFail(it)
            }
        ).apply(Zoop::post)
    }

}