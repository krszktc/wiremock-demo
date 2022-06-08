package net.sympower.citizen.apx.dto.outgoing;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DataResponse {
    private String market;
    private long date;
    private List<DataResponseValue> values;
}
