package vitalir.io.common.domain.money

data class Money(
    val currency: Currency,
    val amount: Long,
)
