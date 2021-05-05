package com.peteh.stuff.events;

import com.peteh.stuff.Stuff;
import com.peteh.stuff.cooldowns.CoolDownManager;
import com.peteh.stuff.items.ItemManagers;
import com.peteh.stuff.projectiles.EntityData;
import com.peteh.stuff.projectiles.fireballtest;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.peteh.stuff.projectiles.EntityData.shotprojectiledata;

public class eventthings implements Listener {

    private final CoolDownManager cooldownManager = new CoolDownManager();

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.RED + "this is petehs plugin speaking to you L NEWGEN");

    }
    @EventHandler
    public static void onPlayerWalk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();

        Material block = player.getWorld().getBlockAt(x, y-1, z).getType();
        if(block == Material.CRYING_OBSIDIAN) {
            player.sendMessage(ChatColor.DARK_GREEN + "Damn ur walking on obsidian :D");
        }

        //Aspect of the End Speed
        if(player.getInventory().getItemInMainHand().getItemMeta().equals(ItemManagers.Aspect.getItemMeta())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 2));
        }
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action actioner = Action.RIGHT_CLICK_AIR;
        if(!event.getAction().equals(actioner))
            return;

        if(event.getItem() != null) {
            if(event.getItem().getItemMeta().equals(ItemManagers.wand.getItemMeta())) {
                Player player = event.getPlayer();


                //cooldown stuff
                int cooldownTime = 3; // Get number of seconds from wherever you want
                long secondsLeft = ((cooldownManager.getCooldown(player.getUniqueId())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                if(secondsLeft>0) {
                    // Still cooling down
                    player.sendMessage("You cant use petehs aspect thing for another "+ secondsLeft +" seconds!");
                    return;
                }

                Fireball ball = player.launchProjectile(fireballtest.class);
                ball.setVelocity(ball.getVelocity().multiply(10));
                ball.setYield(10);
                player.sendMessage("whoosh!!!");
            }

            if(event.getItem().getItemMeta().equals(ItemManagers.Aspect.getItemMeta())) {
                //get the player
                Player player = event.getPlayer();

                //cooldown stuff
                int cooldownTime = 1; // Get number of seconds from wherever you want
                long secondsLeft = ((cooldownManager.getCooldown(player.getUniqueId())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                if(secondsLeft>0) {
                    // Still cooling down
                    player.sendMessage("You cant use petehs aspect thing for another "+ secondsLeft +" seconds!");
                    return;
                }

                // No cooldown found or cooldown has expired, save new cooldown
                cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());

                //Do Aspect Teleport
                AspectTeleport(player);
                player.sendMessage("whoosh!!!");
            }
            if(event.getItem().getItemMeta().equals(ItemManagers.Flamer.getItemMeta())) {
                //get the player
                Player player = event.getPlayer();

                int cooldownTime = 5; // Get number of seconds from wherever you want
                long secondsLeft = ((cooldownManager.getCooldown(player.getUniqueId())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                if(secondsLeft>0) {
                    // Still cooling down
                    player.sendMessage("You cant use petehs thing for another "+ secondsLeft +" seconds!");
                    return;
                }

                // No cooldown found or cooldown has expired, save new cooldown
                cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());

                //Dash
                Dash(player);
            }
        }


    }
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball) { //check if the damager is a snowball
            if (shotprojectiledata.containsKey(event.getDamager())) { //check if the snowball is one that is supposed to be doing a different damage
                EntityData eventdata = shotprojectiledata.get(event.getDamager()); //get the data we stored about the projectile
                if (event.getEntity().getLocation().distance(eventdata.getFiredFrom())<=eventdata.getRange()) { //check to see if the event is taking place outside of the range
                    //Spawn Particles on Hit
                    Entity entity = event.getEntity();
                    entity.getWorld().spawnParticle(Particle.FLAME, entity.getLocation(), 40);


                    event.setDamage(eventdata.getDamage()); //set the custom damage to the value stored earlier
                    shotprojectiledata.remove(event.getDamager()); //we can remove the projectile now so that the hashmap won't have unnecessary data in it.
                }
            }
        }
    }
    @EventHandler
    public void eventArrowFired(EntityShootBowEvent e) {
        LivingEntity player = e.getEntity();

        if (e.getEntity() instanceof Player && e.getProjectile() instanceof org.bukkit.entity.Arrow
                && ((HumanEntity)player).getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.RED + "Archer Queen's Bow")) {
            double minAngle = 6.283185307179586D;
            Entity minEntity = null;
            for (Entity entity : player.getNearbyEntities(64.0D, 64.0D, 64.0D)) {
                if (player.hasLineOfSight(entity) && entity instanceof LivingEntity &&
                        !entity.isDead()) {
                    Vector toTarget = entity.getLocation().toVector().clone()
                            .subtract(player.getLocation().toVector());
                    double angle = e.getProjectile().getVelocity().angle(toTarget);
                    if (angle < minAngle) {
                        minAngle = angle;
                        minEntity = entity;
                    }
                }
            }
            if (minEntity != null);
        }
    }
    public void Dash(Player player) {
        //Do flame dash
        Location loc = player.getLocation();
        loc.setPitch(0);
        Vector vec = loc.getDirection();
        player.setVelocity(vec.multiply(6));
        int duration = 2;
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 4));

        player.getWorld().spawnParticle(Particle.FLAME,player.getLocation(), 2000);
        List<Entity> entities = player.getNearbyEntities(5, 5, 5);
        for (int i = 0; i < entities.size(); i++) {

            if(entities.get(i).getType().equals(EntityType.PLAYER)) {
                Player player1 = (Player) entities.get(i);
                player1.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 4));
                player1.sendMessage(Color.RED + "You've been hit by a Dash!!!");
            }
            if(entities.get(i) instanceof  Entity) {
                Entity entity = entities.get(i);
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 4));
                    entity.getWorld().spawnParticle(Particle.REDSTONE,entity.getLocation(), 50);
                }
            }

        }

        player.sendMessage("DashWorks");
    }

    public void AspectTeleport(Player player) {
        //Do Aspect of the End teleport
        ArrayList<Block> Blocks = (ArrayList<Block>) player.getLineOfSight((Set) null, 10);

        Block block = Blocks.get(Blocks.size() - 2);


        Location playerLoc = player.getLocation(); //Get the player's location
        Location loc = block.getLocation();


        loc.setYaw(playerLoc.getYaw()); //Set the yaw of the target location to the player's yaw
        loc.setPitch(playerLoc.getPitch()); //Set the pitch of the target location to the player's pitch
        player.teleport(loc);

        //effects
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,player.getLocation(), 50);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.4F, 1.2F);

    }




}
