package com.zoop.paymentinit.sample

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale


fun String.formatCpfOrCnpj(): String {
    val cleanInput = this.filter { it.isDigit() }

    return if (cleanInput.length <= 11) { // CPF
        buildString {
            append(cleanInput)
            if (cleanInput.length > 3) insert(3, '.')
            if (cleanInput.length > 6) insert(7, '.')
            if (cleanInput.length > 9) insert(11, '-')
        }
    } else { // CNPJ
        buildString {
            append(cleanInput.take(14)) // Limit to CNPJ size
            if (cleanInput.length > 2) insert(2, '.')
            if (cleanInput.length > 5) insert(6, '.')
            if (cleanInput.length > 8) insert(10, '/')
            if (cleanInput.length > 12) insert(15, '-')
        }
    }
}

fun String.isValidCpfOrCnpj(): Boolean {
    val cleanInput = this.filter { it.isDigit() }
    return cleanInput.length == 11 || cleanInput.length == 14
}

fun Long.formatAmount(): String {
    val decimal =  BigDecimal(this).run {
        setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
    }
    val locale = Locale("pt", "BR")
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(decimal)
}
