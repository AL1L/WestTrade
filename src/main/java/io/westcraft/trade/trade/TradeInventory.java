package io.westcraft.trade.trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        // TODO: Insert items
    }

    public void onInventoryClicked(InventoryClickEvent event) {
        // TODO: Add buttons for money and accept/decline, make sure that the player is not taking any items they are not supposed to be
    }
}
