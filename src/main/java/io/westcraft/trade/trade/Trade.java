package io.westcraft.trade.trade;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Trade {

    final private Player party1, party2;
    final private TradeInventory inventory1, inventory2;
    private boolean party1Accept, party2Accept;

    public Trade(Player party1, Player party2) {
        this.party1 = party1;
        this.party2 = party2;
        this.inventory1 = new TradeInventory(this, 1);
        this.inventory2 = new TradeInventory(this, 2);
        update();
    }

    public Player getParty1() {
        return party1;
    }

    public Player getParty2() {
        return party2;
    }

    public boolean doesParty1Accept() {
        return party1Accept;
    }

    public boolean doesParty2Accept() {
        return party2Accept;
    }

    public void setParty1Accept(boolean party1Accept) {
        this.party1Accept = party1Accept;
        update();
    }

    public void setParty2Accept(boolean party2Accept) {
        this.party2Accept = party2Accept;
        update();
    }

    private void update() {
        if (party1Accept && party2Accept) {
            acceptTrade();
            return;
        }
        inventory1.build();
        inventory2.build();
    }

    private void acceptTrade() {
        party1.closeInventory();
        party2.closeInventory();

        // Transfer items from party1 to party2 and vise versa
        for (ItemStack item : inventory2.getItems())
            party1.getInventory().addItem(item);

        for (ItemStack item : inventory1.getItems())
            party2.getInventory().addItem(item);

        // TODO: Transfer money from party1 to party2 and vise versa, we will need to include vault as a dependency
    }

    public void declineTrade() {
        party1.closeInventory();
        party2.closeInventory();

        // Give each player their items back
        for (ItemStack item : inventory1.getItems())
            party1.getInventory().addItem(item);

        for (ItemStack item : inventory2.getItems())
            party2.getInventory().addItem(item);
    }

    public Player getParty(int party) {
        if (party == 1) {
            return party1;
        } else if (party == 2) {
            return party2;
        }
        return null;
    }

    public TradeInventory getPartyInventory(int party) {
        if (party == 1) {
            return inventory1;
        } else if (party == 2) {
            return inventory2;
        }
        return null;
    }
}
