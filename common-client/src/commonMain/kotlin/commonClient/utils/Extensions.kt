package commonClient.utils

import kotlin.math.round

fun calculatePercent(target: Float, sum: Int) = round(
    (target / sum * 100)
).toInt().toString() + "%"
