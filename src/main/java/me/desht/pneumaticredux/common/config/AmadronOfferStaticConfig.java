package me.desht.pneumaticredux.common.config;

import me.desht.pneumaticredux.common.recipes.AmadronOffer;
import me.desht.pneumaticredux.common.recipes.AmadronOfferManager;

import java.util.Collection;

public class AmadronOfferStaticConfig extends AmadronOfferConfig {
    public static AmadronOfferStaticConfig INSTANCE = new AmadronOfferStaticConfig();

    @Override
    public String getFolderName() {
        return "AmadronOffersStatic";
    }

    @Override
    protected String getComment() {
        return "Offers in here are static, meaning they will always exist to be traded with, unlike periodic offers.";
    }

    @Override
    protected Collection<AmadronOffer> getOffers() {
        return AmadronOfferManager.getInstance().getStaticOffers();
    }

}
