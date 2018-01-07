package io.westcraft.trade.trade;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TradeInventory {

    private final Player player;
    private final int party, otherParty;
    private final Trade trade;
    private Inventory inventory;
    private List<ItemStack> items;
    private double moneyIn;

    public TradeInventory(Trade trade, int party) {
        this.trade = trade;
        if (!(party == 1 || party == 2))
            throw new IllegalArgumentException("Party must be 1 or 2");
        this.party = party;
        this.player = (party == 1) ? trade.getParty1() : trade.getParty2();
        this.otherParty = (party == 1) ? 2 : 1;
        build();
    }

    public int getParty() {
        return party;
    }

    public Player getPlayer() {
        return player;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public double getMoneyIn() {
        return moneyIn;
    }

    public void build() {
        this.inventory = Bukkit.createInventory(player, 54, ""); // TODO: Insert title

        ItemStack divider = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemStack acceptButton = new ItemStack(Material.EMERALD_BLOCK);
        ItemStack declineButton = new ItemStack(Material.REDSTONE_BLOCK);
        ItemStack oneDollar = new ItemStack(Material.GOLD_NUGGET);
        ItemStack tenDollar = new ItemStack(Material.GOLD_INGOT);
        ItemStack hundredDollar = new ItemStack(Material.DIAMOND);
        ItemStack totalMoney;
        if (moneyIn > 0) {
            totalMoney = new ItemStack(Material.LAVA_BUCKET);
        } else {
            totalMoney = new ItemStack(Material.BUCKET);
        }

        divider = setItemName(divider, ChatColor.BLACK + "Divider");
        acceptButton = setItemName(acceptButton, ChatColor.GREEN + "Accept");
        declineButton = setItemName(declineButton, ChatColor.RED + "Decline");
        oneDollar = setItemName(oneDollar, ChatColor.WHITE + "Add $1");
        tenDollar = setItemName(tenDollar, ChatColor.WHITE + "Add $10");
        hundredDollar = setItemName(hundredDollar, ChatColor.WHITE + "Add $100");
        totalMoney = setItemName(totalMoney, ChatColor.WHITE + "Total: $" + moneyIn);

        inventory.setItem(4, divider);
        inventory.setItem(13, divider);
        inventory.setItem(22, divider);
        inventory.setItem(31, divider);
        inventory.setItem(36, divider);
        inventory.setItem(37, divider);
        inventory.setItem(38, divider);
        inventory.setItem(39, divider);
        inventory.setItem(40, divider);
        inventory.setItem(41, divider);
        inventory.setItem(42, divider);
        inventory.setItem(43, divider);
        inventory.setItem(44, divider);
        inventory.setItem(45, oneDollar);
        inventory.setItem(46, tenDollar);
        inventory.setItem(47, hundredDollar);
        inventory.setItem(48, totalMoney);
        inventory.setItem(49, divider);
        inventory.setItem(50, acceptButton);
        inventory.setItem(51, acceptButton);
        inventory.setItem(52, declineButton);
        inventory.setItem(53, declineButton);

        player.openInventory(inventory);
    }

    private ItemStack setItemName(ItemStack is, String name) {
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        is.setItemMeta(m);
        return is;
    }

    public void onInventoryClicked(InventoryClickEvent event) {
        // TODO: Add buttons for money and accept/decline, make sure that the player is not taking any items they are not supposed to be
    }
}
