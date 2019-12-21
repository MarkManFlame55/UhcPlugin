/*
 * UhcPlugin
 * Copyright (c) 2019  Pau Machetti Vallverdu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.PauMAVA.UhcPlugin;

import me.PauMAVA.UhcPlugin.commands.UhcCmdHub;
import me.PauMAVA.UhcPlugin.commands.UhcCompleteTab;
import me.PauMAVA.UhcPlugin.gameplay.CustomRecipes;
import me.PauMAVA.UhcPlugin.match.UhcMatchHandler;
import me.PauMAVA.UhcPlugin.teams.UhcTeamsManager;
import me.PauMAVA.UhcPlugin.util.EventsRegister;
import me.PauMAVA.UhcPlugin.util.PacketIntercepter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class UhcPluginCore extends JavaPlugin {
		
		
		/*Logger to handle plugin's output*/
		private static final Logger UhcLogger = Bukkit.getServer().getLogger();
		private static UhcPluginCore instance;
		private Boolean matchStatus = false;
		private UhcMatchHandler matchHandler;
		
		@Override
		public void onEnable() {
			instance = this;
			this.matchHandler = new UhcMatchHandler(this);
			UhcLogger.info("Enabled UhcPlugin!");
			this.saveDefaultConfig();
			UhcTeamsManager.createTeamsFile();
			this.getServer().getPluginManager().registerEvents(new EventsRegister(), this);
			this.getCommand("uhc").setExecutor(new UhcCmdHub());
			this.getCommand("uhc").setTabCompleter(new UhcCompleteTab());
			//this.getCommand("abort").setExecutor(new AbortCmd(this));
			CustomRecipes recipes = new CustomRecipes(true);
		}
		
		@Override
		public void onDisable() {
			for(Player p: Bukkit.getServer().getOnlinePlayers()) {
				PacketIntercepter.rmPlayer(p);
			}
			UhcLogger.info("Disabled UhcPlugin!");
		}
		
		public static UhcPluginCore getInstance() {
			return instance;
		}
		
		public Logger getPluginLogger() {
			return UhcLogger;
		}

		public UhcMatchHandler getMatchHandler() {
			return this.matchHandler;
		}
}
