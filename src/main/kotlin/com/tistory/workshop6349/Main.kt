package com.tistory.workshop6349

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        var instance: Main ?= null
        private set
    }

    override fun onEnable() {
        logger.info("Plugin enabled")
        getCommand("hello")?.setExecutor(MainCommand)
        instance = this

    }

    override fun onDisable() {
        logger.info("Plugin disabled")
    }

}