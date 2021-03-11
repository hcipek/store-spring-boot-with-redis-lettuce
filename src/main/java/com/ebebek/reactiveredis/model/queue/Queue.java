package com.ebebek.reactiveredis.model.queue;

import com.ebebek.reactiveredis.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Queue extends BaseModel {

    private Long id;
    private String publisher;
    private String message;
}
