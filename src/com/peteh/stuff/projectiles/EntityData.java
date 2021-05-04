package com.peteh.stuff.projectiles;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.WeakHashMap;

public class EntityData {
    public final static WeakHashMap<Entity, EntityData> shotprojectiledata = new WeakHashMap<Entity, EntityData>();

    private Location firedfrom;
    private Integer range;
    private Double damage;

    //constructor
    public EntityData(Location loc, Integer range, Double damage) {
        this.firedfrom = loc;
        this.range = range;
        this.damage = damage;
    }
    public Location getFiredFrom() {
        return firedfrom;
    }
    public Integer getRange() {
        return range;
    }
    public Double getDamage() {
        return damage;
    }

}
