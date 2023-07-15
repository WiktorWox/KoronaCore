package com.korona.koronaswiat.events;

import com.korona.koronaswiat.heartofthebase.INetwork;
import fr.mosca421.worldprotector.api.event.RegionEvent;
import fr.mosca421.worldprotector.core.IRegion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class NetworkEvent extends Event {
    private final INetwork network;
    private final PlayerEntity player;

    public NetworkEvent(INetwork network, PlayerEntity player) {
        this.network = network;
        this.player = player;
    }

    public INetwork getNetwork() {
        return this.network;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public static class UpdateNetworkEvent extends NetworkEvent {
        public UpdateNetworkEvent(INetwork network, PlayerEntity player) {
            super(network, player);
        }
    }

    public static class RemoveNetworkEvent extends NetworkEvent {
        public RemoveNetworkEvent(INetwork network, PlayerEntity player) {
            super(network, player);
        }
    }

    public static class CreateNetworkEvent extends NetworkEvent {
        public CreateNetworkEvent(INetwork network, PlayerEntity player) {
            super(network, player);
        }
    }
}
