package com.msemu.core.network.packets.outpacket.user;

import com.msemu.commons.enums.OutHeader;
import com.msemu.commons.network.packets.OutPacket;
import com.msemu.commons.utils.types.Position;
import com.msemu.core.network.GameClient;
import com.msemu.world.client.character.Character;

/**
 * Created by Weber on 2018/4/14.
 */
public class LP_FoxManEnterField extends OutPacket<GameClient> {

    public LP_FoxManEnterField(Character character) {
        super(OutHeader.LP_FoxManEnterField);

        // TODO FoxMainLife
        Position position = character.getPosition();

        encodeInt(character.getId());
        encodeShort(0);   // 1 = Haku Old Form,  0 = Haku New Form
        encodePosition(position);
        encodeByte(4); //MoveAction
        encodeShort(0); //Foothold
        encodeInt(0); //nUpgrade
        encodeInt(0); //FanID Equipped by Haku

    }
}