package vitalir.io.common.domain.money

import vitalir.io.common.domain.CountryId

data class Currency(
    val currencyCode: CurrencyCode,
    val countryId: CountryId,
) {

    @JvmInline
    value class CurrencyCode(val code: String) {

        init {
            check(code.length == CURRENCY_CODE_LENGTH) { "Invalid currency code" }
        }

        companion object {
            const val CURRENCY_CODE_LENGTH = 3
        }
    }

    companion object {
        val US_DOLLAR = Currency(
            currencyCode = CurrencyCode("USD"),
            countryId = CountryId.Code.US,
        )
    }
}
