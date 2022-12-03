package vitalir.io.common.infrastructure.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import vitalir.io.common.domain.CountryId
import vitalir.io.common.domain.money.Currency
import vitalir.io.common.domain.money.Money
import vitalir.io.common.domain.time.Duration
import vitalir.io.common.domain.time.Time
import vitalir.io.common.infrastructure.DefaultTime

inline fun <reified T : Any> SchemaBuilder.type(name: String) {
    type(T::class) {
        this.name = name
    }
}

inline fun <reified T : Any> SchemaBuilder.inputType(name: String) {
    inputType(T::class) {
        this.name = "${name}Input"
    }
}

fun Money.toGraphQLModel(): GraphQLMoney {
    return GraphQLMoney(
        currencyCode = currency.currencyCode.code,
        units = amount,
    )
}

fun GraphQLMoney.toDomainModel(): Money {
    return Money(
        currency = Currency(
            currencyCode = Currency.CurrencyCode(currencyCode),
            countryId = CountryId.Code.US,
        ),
        amount = units,
    )
}

fun GraphQLDayDuration.toDomainModel(): Duration {
    return Duration(
        from = from.toDomainModel(),
        to = to.toDomainModel(),
    )
}

fun GraphQLTimeOfDay.toDomainModel(): Time {
    return DefaultTime(
        hours = hours,
        minutes = minutes,
        seconds = seconds,
    )
}

fun Duration.toGraphQLModel(): GraphQLDayDuration {
    return GraphQLDayDuration(
        from = from.toGraphQLModel(),
        to = to.toGraphQLModel(),
    )
}

fun Time.toGraphQLModel(): GraphQLTimeOfDay {
    return GraphQLTimeOfDay(
        hours = hours,
        minutes = minutes,
        seconds = seconds,
    )
}
