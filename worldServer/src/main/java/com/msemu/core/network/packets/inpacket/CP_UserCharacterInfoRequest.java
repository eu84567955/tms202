/*
 * MIT License
 *
 * Copyright (c) 2018 msemu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.msemu.core.network.packets.inpacket;

import com.msemu.commons.network.packets.InPacket;
import com.msemu.core.network.GameClient;
import com.msemu.core.network.packets.outpacket.wvscontext.LP_CharacterInfo;
import com.msemu.world.client.character.Character;
import com.msemu.world.client.field.Field;

/**
 * Created by Weber on 2018/5/23.
 */
public class CP_UserCharacterInfoRequest extends InPacket<GameClient> {

    private int updateTick;
    private int charId;

    public CP_UserCharacterInfoRequest(short opcode) {
        super(opcode);
    }

    @Override
    public void read() {
        this.updateTick = decodeInt();
        this.charId = decodeInt();
    }

    @Override
    public void runImpl() {

        final Character chr = getClient().getCharacter();
        final Field field = chr.getField();
        final Character target = field.getCharacterById(charId);

        if (target != null)
            chr.write(new LP_CharacterInfo(target, target == chr));

    }
}
