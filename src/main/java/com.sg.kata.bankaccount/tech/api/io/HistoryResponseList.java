package org.account.manager.tech.api.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryResponseList {
    private List<HistoryResponse> history;
}
