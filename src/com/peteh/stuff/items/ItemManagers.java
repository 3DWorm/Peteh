package com.peteh.stuff.items;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemManagers {


    public static ItemStack wand;
    public static ItemStack Aspect;
    public static ItemStack Flamer;

    public static void init() {
        createWand();
        createAspect();
        createFlamer();
    }

    private static void createWand() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Nether Blast");
        List<String> lore = new ArrayList<>();
        lore.add("this thing shoots stuff (custom plugin)");
        lore.add("created by xpeteh boi");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        item.setItemMeta(meta);
        wand = item;
    }

    private static void createAspect() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Aspect of the End Knockoff");
        List<String> lore = new ArrayList<>();
        lore.add("this thing teleports u (custom plugin)");
        lore.add("created by xpeteh boi");
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "GENERIC_ATTACK_DAMAGE", 50, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        meta.setLore(lore);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);

        item.setItemMeta(meta);
        Aspect= item;
    }
    private static void createFlamer() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Flamer");
        List<String> lore = new ArrayList<>();
        lore.add("Dash boi");
        lore.add("created by xpeteh boi");
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "GENERIC_ATTACK_DAMAGE", 30, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        meta.setLore(lore);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);

        item.setItemMeta(meta);
        Flamer= item;
    }

}

