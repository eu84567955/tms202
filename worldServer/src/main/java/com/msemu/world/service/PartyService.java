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

package com.msemu.world.service;


import com.msemu.core.startup.StartupComponent;
import com.msemu.world.client.character.Character;
import com.msemu.world.client.character.party.Party;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class PartyService {

    private static final AtomicReference<PartyService> instance = new AtomicReference<>();

    private final Map<Integer, Party> parties = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> invitations = new ConcurrentHashMap<>();


    public static PartyService getInstance() {
        PartyService value = instance.get();
        if (value == null) {
            synchronized (PartyService.instance) {
                value = instance.get();
                if (value == null) {
                    value = new PartyService();
                }
                instance.set(value);
            }
        }
        return value;
    }

    public Map<Integer, Party> getAllParties() {
        return Collections.unmodifiableMap(parties);
    }

    public boolean registerParty(Party party) {
        if (this.parties.containsKey(party.getPartyLeaderId())) {
            return false;
        } else {
            this.parties.put(party.getPartyLeaderId(), party);
            return true;
        }
    }

    public boolean unregisterParty(Party party) {
        if (this.parties.containsKey(party.getPartyLeaderId())) {
            this.parties.remove(party.getPartyLeaderId());
            return true;
        }
        return false;
    }

    public void addInvitation(Character invited, Party toParty) {
        this.invitations.put(invited.getId(), toParty.getId());
    }

    public void removeInvitation(Character chr) {
        this.invitations.remove(chr.getId());
    }

    public boolean hasInvitation(Character chr, Party party) {
        return this.invitations.containsKey(chr.getId())
                && this.invitations.get(chr.getId()) == party.getId();
    }

    public Party getPartyById(int partyId) {
        return getAllParties().values().stream().
                filter(party -> party.getId() == partyId)
                .findFirst().orElse(null);
    }

    public Party getPartyByLeaderId(int leaderId) {
        return this.parties.getOrDefault(leaderId, null);
    }

    public void updatePartyLeader(int oldLeaderId, Party party) {
        if (this.parties.containsKey(oldLeaderId)) {
            this.parties.remove(oldLeaderId);
            this.parties.put(party.getPartyLeaderId(), party);
        }
    }

    public Party findCharacterParty(int id) {
        return getAllParties().values()
                .stream()
                .filter(party -> party.getMemberById(id) != null)
                .findFirst().orElse(null);
    }
}
