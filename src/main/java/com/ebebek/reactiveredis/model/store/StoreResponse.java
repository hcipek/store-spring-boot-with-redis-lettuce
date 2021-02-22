package com.ebebek.reactiveredis.model.store;

import com.ebebek.reactiveredis.model.store.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {

    private List<Store> results;
    private String resultMessage;
    private int resultCode;
}
