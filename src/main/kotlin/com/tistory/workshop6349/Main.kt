package com.tistory.workshop6349

import com.tistory.workshop6349.commands.GachaCommand
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        var instance: Main ?= null
        private set
    }

    override fun onEnable() {
        logger.info("Plugin enabled")
        getCommand("timer")?.setExecutor(GachaCommand(this))
        server.pluginManager.registerEvents(EventListener(this), this)
        BossBarManagement.userBossBars.clear()
        BossBarManagement.timer.clear()
        instance = this
    }

    override fun onDisable() {
        logger.info("Plugin disabled")
    }

}