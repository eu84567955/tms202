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

package com.msemu.world.client.field.lifes;

import com.msemu.commons.data.templates.NpcTemplate;
import com.msemu.commons.network.packets.OutPacket;
import com.msemu.core.network.GameClient;
import com.msemu.core.network.packets.outpacket.npc.LP_NpcChangeController;
import com.msemu.core.network.packets.outpacket.npc.LP_NpcEnterField;
import com.msemu.core.network.packets.outpacket.npc.LP_NpcLeaveField;
import com.msemu.world.client.field.ScreenInfo;
import com.msemu.world.enums.FieldObjectType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Weber on 2018/4/11.
 */
@Getter
@Setter
public class Npc extends InternalLife {
    private boolean enabled = true;
    private int presentItemID;
    private byte presentItemState;
    private int presentItemTime = -1;
    private int noticeBoardType;
    private int noticeBoardValue;
    private int alpha; // if hideToLocalUser is true
    private String localRepeatEffect = "";
    private String limitedName = "";
    private ScreenInfo screenInfo;
    private NpcTemplate template;
    private boolean hold, useDay, useNight, noFoothold;

    public Npc(int objectId, NpcTemplate template) {
        super(template.getId());
        setObjectId(objectId);
        this.template = template;
    }

    public void encode(OutPacket<GameClient> outPacket) {
        // CNpc::Init
        outPacket.encodePosition(getPosition());
        outPacket.encodeByte(isLeft());
        outPacket.encodeByte(getAction());
        outPacket.encodeShort(getFh());
        outPacket.encodeShort(getRx0()); // rgHorz.low
        outPacket.encodeShort(getRx1()); // rgHorz.high
        outPacket.encodeByte(isEnabled());
        outPacket.encodeInt(getPresentItemID());
        outPacket.encodeByte(getPresentItemState());
        outPacket.encodeInt(getPresentItemTime());
        outPacket.encodeInt(getNoticeBoardType());
        if (getNoticeBoardType() == 1) {
            outPacket.encodeInt(getNoticeBoardValue());
        }
        outPacket.encodeInt(getAlpha());
        outPacket.encodeInt(0);
        outPacket.encodeString(getLocalRepeatEffect());
        ScreenInfo si = getScreenInfo();
        outPacket.encodeByte(si != null);
        if (si != null) {
            si.encode(outPacket);
        }
    }

    public Npc deepCopy() {
        Npc copy = new Npc(getObjectId(), getTemplate());
        copy.getPosition().setX(getPosition().getX());
        copy.getPosition().setY(getPosition().getY());
        copy.setF(getF());
        copy.setHide(isHide());
        copy.setFh(getFh());
        copy.setCy(getCy());
        copy.setRx0(getRx0());
        copy.setRx1(getRx1());
        copy.setLimitedName(getLimitedName());
        copy.setUseDay(isUseDay());
        copy.setUseNight(isUseNight());
        copy.setHold(isHold());
        copy.setNoFoothold(isNoFoothold());
        return copy;
    }

    public String getScript() {
        return getTemplate().getScript();
    }

    @Override
    public FieldObjectType getFieldObjectType() {
        return FieldObjectType.NPC;
    }

    @Override
    public void enterScreen(GameClient client) {
        if (isEnabled() && !isHide()) {
            client.write(new LP_NpcEnterField(this));
            client.write(new LP_NpcChangeController(this, true));
        }
    }

    @Override
    public void outScreen(GameClient client) {
        if (isEnabled() && !isHide() && getAlpha() <= 0) {
            client.write(new LP_NpcLeaveField(this));
            client.write(new LP_NpcChangeController(this, false));
        }
    }
}