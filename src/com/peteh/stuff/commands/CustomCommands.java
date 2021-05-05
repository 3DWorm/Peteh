package com.peteh.stuff.commands;

import com.peteh.stuff.Stuff;
import com.peteh.stuff.cooldowns.CoolDownManager;

import com.peteh.stuff.items.ItemManagers;
import com.peteh.stuff.projectiles.EntityData;
import com.peteh.stuff.projectiles.arrow;
import com.peteh.stuff.projectiles.fireballtest;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSnowball;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.peteh.stuff.projectiles.EntityData.shotprojectiledata;


public class CustomCommands implements CommandExecutor {



    Stuff plugin;
    private final CoolDownManager cooldownManager = new CoolDownManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;

        //Now we know the sender is a Player
        Player player = (Player) sender;
        //sender.sendMessage("SUCCESS!!!");
        // Do Command Here
        if (command.getName().equalsIgnoreCase("heall")) {
            //btw my brother is raping the piano so that's why im muted
            double maxhealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
            double currenthealth = player.getHealth();
            int h = 2;
            player.setHealth(currenthealth + h);
            if (player.getHealth() > maxhealth) {
                player.setHealth(maxhealth);
            }
            return true;
        }

        //if does /brad
        else if (command.getName().equalsIgnoreCase("brad")) {

            player.performCommand("kill The_Fruit_Guy");
            return true;
        }

        //if farm time cow S
        else if (command.getName().equalsIgnoreCase("farmtime")) {

            if (args.length >= 2) {
                try {
                    EntityType entity = EntityType.valueOf(args[0].toUpperCase());
                    int amount = Integer.parseInt(args[1]);
                    for (int i = 0; i < amount; i++) {
                        player.getWorld().spawnEntity(player.getLocation(), entity);
                    }
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.GREEN + "farmtime <mob> <amount>");
                }

            } else {
                player.sendMessage(ChatColor.GREEN + "farmtime <mob> <amount>");
            }
        } else if (command.getName().equalsIgnoreCase("netherblast")) {

            if (args.length >= 1) {
                try {
                    String person = args[0];
                    Player senderPlayer = Bukkit.getPlayer(person);
                    senderPlayer.getInventory().addItem(ItemManagers.wand);
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.GREEN + "bruh why r u bad at using commands");
                }

            } else {
                player.sendMessage(ChatColor.GREEN + "farmtime <mob> <amount>");
            }
        } else if (command.getName().equalsIgnoreCase("aspect")) {

            if (args.length >= 1) {
                try {
                    String person = args[0];
                    Player senderPlayer = Bukkit.getPlayer(person);
                    senderPlayer.getInventory().addItem(ItemManagers.Aspect);
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.GREEN + "bruh why r u bad at using commands");
                }

            } else {
                player.sendMessage(ChatColor.GREEN + "what");
            }
        } else if (command.getName().equalsIgnoreCase("flamer")) {

            if (args.length >= 1) {
                try {
                    String person = args[0];
                    Player senderPlayer = Bukkit.getPlayer(person);
                    senderPlayer.getInventory().addItem(ItemManagers.Flamer);
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.GREEN + "bruh why r u bad at using commands");
                }

            } else {
                player.sendMessage(ChatColor.GREEN + "what");
            }
        } else if (command.getName().equalsIgnoreCase("dash")) {
            player.sendMessage("WHOOSH!");
            Dash(player);
        } else if (command.getName().equalsIgnoreCase("aspectTeleport")) {
            player.sendMessage("WHOOSH!");
            AspectTeleport(player);
        } else if (command.getName().equalsIgnoreCase("terrablade")) {
            player.sendMessage(ChatColor.GREEN + "WHOOSH!");
            Terrablade(player);
        }
        else if (command.getName().equalsIgnoreCase("blast")) {
            player.sendMessage(ChatColor.GOLD + "WHOOSH!");
            blast(player);
        }
        else if (command.getName().equalsIgnoreCase("throwaxe")) {
            player.sendMessage(ChatColor.GOLD + "WHOOSH!");
            ThrowAxe(player);
        }


        return true;
    }

    public void blast(Player player) {
        //Launch Fireball
        Fireball ball = player.launchProjectile(fireballtest.class);
        ball.setVelocity(ball.getVelocity().multiply(10));
        ball.setYield(10);
        player.sendMessage(ChatColor.RED + "whoosh!!!");
    }

    public void ThrowAxe(Player player) {

        //instantiate armor stand that holds a sword
        Entity armorstand = player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        if(armorstand instanceof ArmorStand) {
            ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
            //apply to armor stand
            ((ArmorStand) armorstand).getEquipment().setItemInMainHand(item);
            armorstand.setGravity(false);
            ((ArmorStand) armorstand).setVisible(false);
            ((ArmorStand) armorstand).setCanPickupItems(false);
            
            //move forward
            armorstand.setVelocity(((ArmorStand) armorstand).getLocation().getDirection().multiply(10));
            new BukkitRunnable()
            {
                private int count = 2;
                @Override
                public void run()
                {
                    // do your stuff here

                    count--;
                    if (count <= 0) {
                        armorstand.remove();
                        cancel();
                    }
                }

            }.runTaskTimer(plugin, 0, 20);


        }
        Location location = player.getEyeLocation();
        BlockIterator blocksToAdd = new BlockIterator(location, 0, 50);
        Location blockToAdd;
        Snowball projectile = player.launchProjectile(Snowball.class);
        projectile.setGravity(false);
        projectile.setVelocity(projectile.getVelocity().multiply(1));
        EntityData data = new EntityData(projectile.getLocation(), 50, 80.0);
        shotprojectiledata.put(projectile, data);

        while (blocksToAdd.hasNext()) {
            blockToAdd = blocksToAdd.next().getLocation();

            if (blockToAdd.getBlock().getType() != Material.AIR) {
                break;
            }
            //player.getWorld().spawnParticle(Particle.COMPOSTER, blockToAdd,20, 0.1, 0.1, 0.1, 0);
        }
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 0.4F, 1.2F);



    }

    public void Dash(Player player) {
        //Do flame dash
        Location loc = player.getLocation();
        loc.setPitch(0);
        Vector vec = loc.getDirection();
        //player.setVelocity(vec.multiply(6));
        player.setVelocity(player.getLocation().getDirection().multiply(5));
        int duration = 2;
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 4));

        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 500);
        List<Entity> entities = player.getNearbyEntities(5, 5, 5);
        for (int i = 0; i < entities.size(); i++) {

            if (entities.get(i).getType().equals(EntityType.PLAYER)) {
                Player player1 = (Player) entities.get(i);
                player1.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 4));
                player1.sendMessage(Color.RED + "You've been hit by a Dash!!!");
            }
            if (entities.get(i) instanceof Entity) {
                Entity entity = entities.get(i);
                if (entity instanceof LivingEntity) {
                    //((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 1));
                    //entity.getWorld().spawnParticle(Particle.REDSTONE,entity.getLocation(), 50);
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
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 50);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.4F, 1.2F);

    }

    public void Terrablade(Player player) {

        //Fires Projectile
        Location location = player.getEyeLocation();
        BlockIterator blocksToAdd = new BlockIterator(location, 0, 50);
        Location blockToAdd;
        Snowball projectile = player.launchProjectile(Snowball.class);
        projectile.setGravity(false);
        projectile.setVelocity(projectile.getVelocity().multiply(4));
        EntityData data = new EntityData(projectile.getLocation(), 50, 65.0);
        shotprojectiledata.put(projectile, data);

        while (blocksToAdd.hasNext()) {

            blockToAdd = blocksToAdd.next().getLocation();

            if (blockToAdd.getBlock().getType() != Material.AIR) {
                break;
            }

            player.getWorld().spawnParticle(Particle.COMPOSTER, blockToAdd,20, 0.1, 0.1, 0.1, 0);
        }

        for (Player p : Bukkit.getOnlinePlayers()) {

            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) projectile).getHandle().getId()));
        }

        //effects
        //player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 50);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.4F, 1.2F);
    }

    public void homing(Player player) {

        //fires homing arrow
        Arrow arrow = player.launchProjectile(arrow.class);


    }


}

