/*
 * This file is part of coins3
 *
 * Copyright © 2020 Beelzebu
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.beelzebu.coins.common.messaging;

import com.github.beelzebu.coins.api.messaging.AbstractMessagingService;
import com.github.beelzebu.coins.api.messaging.MessagingServiceType;
import com.github.beelzebu.coins.api.plugin.CoinsBootstrap;
import com.github.beelzebu.coins.common.plugin.CommonCoinsPlugin;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * @author Beelzebu
 */
public class DummyMessaging extends AbstractMessagingService {

    public DummyMessaging(CommonCoinsPlugin<? extends CoinsBootstrap> coinsPlugin) {
        super(coinsPlugin);
    }

    @Override
    protected void sendMessage(JsonObject jsonObject) {
    }

    @Override
    public void start() {
    }

    @NotNull
    @Override
    public MessagingServiceType getType() {
        return MessagingServiceType.NONE;
    }

    @Override
    public void stop() {
    }
}
