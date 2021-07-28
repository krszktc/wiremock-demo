package net.sympower.citizen.apx.dto.incomming

class Quote(
    val market: String,
    val date_applied: Long,
    val values: List<QuoteValue>
)