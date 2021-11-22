package com.tistory.workshop6349

import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.Random

class GachaBoxResult(private val player: Player, plugin: Plugin) {

    companion object {
        private val entities = mutableListOf<EntityType>(*EntityType.values())
        private val materials = mutableListOf<Material>(*Material.values())
        private val effects = mutableListOf<PotionEffectType>(*PotionEffectType.values())
        val objects = mutableListOf<Any>(entities, materials, effects)
    }

    init {
        val random = Random()
        object : BukkitRunnable() {
            var count = 0

            override fun run() {
                val r = random.nextInt(objects.size)
                val o = objects[r]
                val next: String = o.toString()

                if (count > 50) {
                    result(player, o)
                    BossBarManagement.overrideBossBar(player)
                    this.cancel()
                }
                else {
                    player.sendTitle("가챠 상자 여는 중", next, 0, 3, 0)
                    count++
                }
            }
        }.runTaskTimer(plugin, 3L, 3L)

    }

    private fun customFireWork(firework: Firework, meta: FireworkMeta,
        power: Int, type: FireworkEffect.Type, color: Color) {
        meta.power = power
        meta.addEffect(FireworkEffect.builder().with(type).withColor(color).flicker(true).build())

        firework.fireworkMeta = meta
        firework.detonate()
    }

    private fun result(player: Player, item: Any) {
        player.sendTitle("Selected from Gacha Box", item.toString(), -1, -1, -1)
        val firework: Firework = player.world.spawnEntity(player.location, EntityType.FIREWORK) as Firework
        val meta = firework.fireworkMeta

        if (item is EntityType) {
            customFireWork(firework, meta, 2, FireworkEffect.Type.CREEPER, Color.BLACK)
            spawnEntity(player, item)
        }

        if (item is Material) {
            customFireWork(firework, meta, 3, FireworkEffect.Type.BURST, Color.OLIVE)
            giveItem(player, item)
        }

        if (item is PotionEffectType) {
            customFireWork(firework, meta, 5, FireworkEffect.Type.STAR, Color.AQUA)
            giveEffect(player, item)
        }
    }

    private fun spawnEntity(player: Player, entityType: EntityType) {
        player.world.spawnEntity(player.location, entityType)
    }

    private fun giveItem(player: Player, material: Material) {
        val itemStack = ItemStack(material)
        player.inventory.addItem(itemStack)
    }

    private fun giveEffect(player: Player, potionEffectType: PotionEffectType) {
        player.addPotionEffect(PotionEffect(potionEffectType, 120, 1, true))
    }

}