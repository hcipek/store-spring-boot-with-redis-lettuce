package com.ebebek.reactiveredis.hello;

import lombok.Data;

@Data
public class KeyRequest {
    private String oldKey;
    private String newKey;
}
