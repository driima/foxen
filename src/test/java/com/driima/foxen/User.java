package com.driima.foxen;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements CommandSender {

    private String name;

    @Override
    public String getName() {
        return name;
    }
}
