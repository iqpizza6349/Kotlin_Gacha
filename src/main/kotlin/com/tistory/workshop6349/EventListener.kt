package com.tistory.workshop6349

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class EventListener(private val plugin: Plugin): Listener {

    @EventHandler
    fun joinEvent(event: PlayerJoinEvent) {
        event.joinMessage = "Hello, " + event.player.name
        BossBarManagement(event.player, plugin)
    }

    @EventHandler
    fun playerInteractEvent(event: PlayerInteractEvent) {
        GachaBox.interactBox(event.player, event, plugin)
    }

    @EventHandler
    fun blockPlaceEvent(event: BlockPlaceEvent) {
        val player = event.player
        if (player.inventory.itemInMainHand.type == Material.SHULKER_BOX
            && player.inventory.itemInMainHand.containsEnchantment(Enchantment.LUCK)) {
            event.isCancelled = true
        }
    }

}