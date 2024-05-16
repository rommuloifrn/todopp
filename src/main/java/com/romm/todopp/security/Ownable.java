package com.romm.todopp.security;

import com.romm.todopp.entity.User;

public abstract class Ownable {
    User owner;

    public User getOwner() {
        return owner;
    }
}
