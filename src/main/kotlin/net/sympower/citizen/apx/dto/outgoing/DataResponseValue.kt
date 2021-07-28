package net.sympower.citizen.apx.dto.outgoing

import java.math.BigDecimal

class DataResponseValue(
    val hour: Int,
    val netVolume: BigDecimal,
    val price: BigDecimal
)