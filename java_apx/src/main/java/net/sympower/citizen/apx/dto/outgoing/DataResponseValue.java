package net.sympower.citizen.apx.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DataResponseValue {
    private int hour;
    private BigDecimal netVolume;
    private BigDecimal price;
}
