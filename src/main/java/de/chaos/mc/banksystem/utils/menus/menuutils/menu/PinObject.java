package de.chaos.mc.banksystem.utils.menus.menuutils.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.function.Consumer;

@AllArgsConstructor
@Setter
@Getter
public class PinObject {
    public UUID uuid;
    public String output;
    public Consumer consumer;
}