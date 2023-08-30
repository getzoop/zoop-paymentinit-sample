package com.zoop.paymentinit.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zoop.paymentinit.sample.ui.theme.AppTheme
import com.zoop.sdk.plugin.paymentinit.model.PaymentInitFailResponse

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ZoopPaymentInitHelper.init(this)

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(this)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp(activity: ComponentActivity) {
    var errorMessage: String? by remember { mutableStateOf(null) }
    var referenceId: String? by remember { mutableStateOf(null) }
    var hadSuccess: Boolean? by remember { mutableStateOf(null) }
    var failReason: PaymentInitFailResponse.FailReason? by remember { mutableStateOf(null) }

    var amount: Long by remember { mutableStateOf(0) }
    var cpfOrCnpj: String by remember { mutableStateOf(BuildConfig.CPF_OR_CNPJ) }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PaymentInit Sample",
                style = MaterialTheme.typography.headlineMedium
            )
            CpfCnpjTextField(
                modifier = Modifier.fillMaxWidth(),
                value = cpfOrCnpj
            ) {
                cpfOrCnpj = it
            }

            AmountTextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount
            ) {
                amount = it
            }

            Button(
                modifier = Modifier.fillMaxWidth(1f),
                enabled = amount > 0 && cpfOrCnpj.isValidCpfOrCnpj(),
                onClick = {
                    hadSuccess = null
                    errorMessage = null
                    referenceId = null

                    ZoopPaymentInitHelper.pay(
                        activity = activity,
                        amount = amount,
                        personalId = cpfOrCnpj,
                        onSuccess = {
                            hadSuccess = true
                            referenceId = it.referenceId
                        },
                        onFail = {
                            hadSuccess = false
                            errorMessage = it.errorMessage
                            failReason = it.reason
                        }
                    )
                }
            ) {
                Text("PAGAR")
            }

            when(hadSuccess) {
                true -> Text(
                    text = "Pagamento Aprovado! $referenceId",
                    color = colorResource(id = R.color.successTextColor),
                    style = MaterialTheme.typography.bodyLarge
                )
                false -> {
                    Text(
                        text = when(failReason) {
                            PaymentInitFailResponse.FailReason.PAYMENT_CANCELED -> "Pagamento Cancelado"
                            PaymentInitFailResponse.FailReason.PAYMENT_DENIED -> "Pagamento Negado"
                            PaymentInitFailResponse.FailReason.PAYMENT_PENDING -> "Pagamento Pendente"
                            PaymentInitFailResponse.FailReason.ABORTED -> "Operação abortada"
                            else -> "Falha no Pagamento"
                        },
                        color = colorResource(id = R.color.errorTextColor),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = errorMessage ?: "ERRO GENERICO",
                        color = colorResource(id = R.color.errorTextColor),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {}
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CpfCnpjTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    val formatedText = value.formatCpfOrCnpj()

    OutlinedTextField(
        modifier = modifier,
        value = TextFieldValue(formatedText, selection = TextRange(formatedText.length)),
        onValueChange = {it ->
            onValueChange(it.text.filter { it.isDigit() })
        },
        label = {
            Text("CPF / CNPJ")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    value: Long,
    onValueChange: (Long) -> Unit = {}
) {
    val formatedText = value.formatAmount()
    OutlinedTextField(
        modifier = modifier,
        value = TextFieldValue(formatedText, selection = TextRange(formatedText.length)) ,
        onValueChange = { value ->
            onValueChange(value.text.filter { it.isDigit() }.toLongOrNull() ?: 0)
        },
        label = {
            Text("Valor (em centavos)")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}

@Preview(showBackground = true, heightDp = 640, widthDp = 360)
@Composable
fun GreetingPreview() {
    AppTheme {
        MainApp(LocalContext.current as ComponentActivity)
    }
}