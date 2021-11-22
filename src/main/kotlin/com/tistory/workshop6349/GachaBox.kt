package com.tistory.workshop6349

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.Plugin

object GachaBox {
    private val gachaBox: ItemStack = ItemStack(Material.SHULKER_BOX)
    private val gachaBoxMeta: ItemMeta ?= gachaBox.itemMeta

    fun createGachaBox(player: Player) {
        gachaBoxMeta?.addEnchant(Enchantment.LUCK, 1, false)
        gachaBoxMeta?.setDisplayName("가챠 상자")
        gachaBox.itemMeta = gachaBoxMeta
        player.inventory.addItem(gachaBox)
    }

    fun interactBox(player: Player, event: PlayerInteractEvent, plugin: Plugin) {
        if (player.inventory.itemInMainHand.type == Material.SHULKER_BOX
            && player.inventory.itemInMainHand.containsEnchantment(Enchantment.LUCK)
            && (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) {
            openBox(player, plugin)
            event.isCancelled = true
            player.inventory.itemInMainHand.type = Material.AIR
        }
    }

    private fun openBox(player: Player, plugin: Plugin) {
        GachaBoxResult(player, plugin)
    }

}