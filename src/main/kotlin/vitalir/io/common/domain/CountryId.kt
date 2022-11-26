package vitalir.io.common.domain

sealed interface CountryId {

    data class Code(
        val intValue: Int,
        val shortIso: String,
    ) : CountryId {

        companion object {
            val US = Code(
                intValue = 1,
                shortIso = "US",
            )
        }
    }
}
