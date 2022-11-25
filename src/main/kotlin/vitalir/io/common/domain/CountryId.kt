package vitalir.io.common.domain

sealed interface CountryId {

    data class Code(
        val intValue: Int,
        val isoCode: String,
    ) : CountryId
}
