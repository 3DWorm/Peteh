package com.peteh.stuff.cooldowns;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoolDownManager {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public static final long DEFAULT_COOLDOWN = 3;

    public void setCooldown(UUID player, long time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public Long getCooldown(UUID player){
        return cooldowns.getOrDefault(player, DEFAULT_COOLDOWN);
    }
}
