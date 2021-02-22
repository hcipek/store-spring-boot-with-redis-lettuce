package com.ebebek.reactiveredis.model.ui.store;

import com.ebebek.reactiveredis.model.ui.store.UIStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UIStoreRequest {

    private List<UIStore> requestList;

}