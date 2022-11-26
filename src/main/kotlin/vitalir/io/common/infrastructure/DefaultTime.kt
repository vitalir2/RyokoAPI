package vitalir.io.common.infrastructure

import vitalir.io.common.domain.time.Time

internal class DefaultTime(
    override val seconds: Int = 0,
    override val minutes: Int = 0,
    override val hours: Int = 0,
) : Time
