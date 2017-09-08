package me.desht.pneumaticredux.client.gui;

public interface INeedTickUpdate {
    /**
     * When an instance of this interface gets added to the ClientTickHandler's list, this method will invoke every tick.
     */
    public void update();
}
