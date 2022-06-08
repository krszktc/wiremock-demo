package net.sympower.citizen.apx.dto.incomming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    private String market;
    private long date_applied;
    private List<QuoteValue> values;
}
