package de.theredend2000.advancedegghunt.util;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder(XMaterial mat){
        itemStack = mat.parseItem();
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayname(String s){
        itemMeta.setDisplayName(s);
        return this;
    }

    public ItemBuilder setOwner(String name){
        SkullMeta skullMeta = (SkullMeta) this.itemMeta;
        skullMeta.setOwner(name);
        return this;
    }

    public ItemBuilder withGlow(boolean s){
        if(s) {
            itemMeta.addEnchant(Enchantment.LURE, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder setLore(String... s){
        itemMeta.setLore(Arrays.asList(s));
        return this;
    }

    public ItemBuilder setDefaultLore(List<String> s){
        itemMeta.setLore(s);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean s){
        itemMeta.setUnbreakable(s);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... s){
        itemMeta.addItemFlags(s);
        return this;
    }

    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }

    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemBuilder setCustomId(String id){
        itemMeta = ItemHelper.setCustomId(itemMeta, id);

        return this;
    }

    public ItemBuilder setSkullOwner(String texture) {
        itemStack = build();

        var version = Bukkit.getBukkitVersion().split("-",2);   //TODO:TEMP Fix

        if (VersionComparator.isGreaterThanOrEqual(version[0], "1.20.5")) {
            // Workaround for Minecraft 1.20.5+
            NBT.modifyComponents(itemStack, nbt -> {
                ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
                profileNbt.setUUID("id", UUID.randomUUID());
                ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
                propertiesNbt.setString("name", "textures");
                propertiesNbt.setString("value", texture);
            });
        } else {
            NBT.modify(itemStack, nbt -> {
                final ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");

                // The owner UUID. Note that skulls with the same UUID but different textures will misbehave and only one texture will load.
                // They will share the texture. To avoid this limitation, it is recommended to use a random UUID.
                skullOwnerCompound.setUUID("Id", UUID.randomUUID());

                skullOwnerCompound.getOrCreateCompound("Properties")
                        .getCompoundList("textures")
                        .addCompound()
                        .setString("Value", texture);
            });
        }

        itemMeta = itemStack.getItemMeta();
        return this;
    }

    public ItemBuilder setColor(Color color) {
        try {
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) this.itemMeta;
            armorMeta.setColor(color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        itemMeta.addEnchant(ench, level, true);
        return this;
    }
}
