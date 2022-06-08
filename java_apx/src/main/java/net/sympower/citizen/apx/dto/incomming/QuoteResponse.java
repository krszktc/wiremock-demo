package net.sympower.citizen.apx.dto.incomming;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {
    private List<Quote> quote;
}
