package net.sympower.citizen.apx.dto.outgoing


class DataResponse(
    val market: String,
    val date: Long,
    val values: List<DataResponseValue>
)