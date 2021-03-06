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
package com.github.beelzebu.coins.bungee;

import com.github.beelzebu.coins.api.config.CoinsConfig;
import com.github.beelzebu.coins.bungee.listener.CommandListener;
import com.github.beelzebu.coins.common.plugin.CommonCoinsPlugin;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Beelzebu
 */
public class CoinsBungeePlugin extends CommonCoinsPlugin<CoinsBungeeMain> {

    CoinsBungeePlugin(@NotNull CoinsBungeeMain bootstrap, CoinsConfig config) {
        super(bootstrap, config);
    }

    @Override
    public void enable() {
        super.enable();
        ProxyServer.getInstance().getPluginManager().registerListener(getBootstrap(), new CommandListener(this));
    }

    @Override
    public void disable() {
        super.disable();
        ProxyServer.getInstance().getScheduler().cancel(getBootstrap());
    }
}
