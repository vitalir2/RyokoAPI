package vitalir.io.common.infrastructure.graphql

import vitalir.io.common.domain.money.Money

fun Money.toGraphQLModel(): GraphQLMoney {
    return GraphQLMoney(
        currencyCode = currency.currencyCode.code,
        units = amount,
    )
}
