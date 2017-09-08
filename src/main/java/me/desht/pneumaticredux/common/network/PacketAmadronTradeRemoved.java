package me.desht.pneumaticredux.common.network;

import me.desht.pneumaticredux.client.gui.widget.WidgetAmadronOffer;
import me.desht.pneumaticredux.common.config.AmadronOfferSettings;
import me.desht.pneumaticredux.common.recipes.AmadronOfferCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

public class PacketAmadronTradeRemoved extends PacketAbstractAmadronTrade<PacketAmadronTradeRemoved> {

    public PacketAmadronTradeRemoved() {
    }

    public PacketAmadronTradeRemoved(AmadronOfferCustom offer) {
        super(offer);
    }

    @Override
    public void handleClientSide(PacketAmadronTradeRemoved message, EntityPlayer player) {
        if (AmadronOfferSettings.notifyOfTradeRemoval)
            player.sendStatusMessage(new TextComponentTranslation("message.amadron.playerRemovedTrade",
                    message.getOffer().getVendor(),
                    WidgetAmadronOffer.getStringForObject(message.getOffer().getInput()),
                    WidgetAmadronOffer.getStringForObject(message.getOffer().getOutput())),
                    false);
    }

    @Override
    public void handleServerSide(PacketAmadronTradeRemoved message, EntityPlayer player) {
    }
}
