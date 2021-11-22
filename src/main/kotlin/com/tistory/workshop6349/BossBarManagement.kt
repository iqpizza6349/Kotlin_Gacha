package com.tistory.workshop6349

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.*
import kotlin.collections.HashMap

class BossBarManagement(player: Player, private val plugin: Plugin) {

    init {
        overrideBossBar(player)
        run(player)
    }

    private val time = 60

    companion object {
        var timer = HashMap<UUID, Long>()
        var userBossBars = HashMap<UUID, BossBar>()
        fun overrideBossBar(player: Player) {
            timer[player.uniqueId] = System.currentTimeMillis() / 1000
            val bossBar = Bukkit.createBossBar("timer", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC)
            bossBar.isVisible = true
            bossBar.addPlayer(player)
            userBossBars[player.uniqueId] = bossBar
        }
    }

    private fun getBossBar(player: Player):BossBar? {
        return userBossBars[player.uniqueId]
    }

    private fun getAddedTime(player: Player): Long {
        return timer[player.uniqueId]!!
    }

    private fun run(player: Player) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, Runnable {
            val bossBar: BossBar ?= getBossBar(player)
            val addedTime = getAddedTime(player)

            if (bossBar != null && addedTime >= 0) {
                val timeLeft = (addedTime + time - System.currentTimeMillis() / 1000).toDouble()
                val progress = timeLeft / time

                if (timeLeft <= 0) {
                    bossBar.isVisible = false
                    userBossBars.remove(player.uniqueId)
                    timer.replace(player.uniqueId, -1L)
                    GachaBox.createGachaBox(player)
                    return@Runnable
                }
                bossBar.addPlayer(player)
                bossBar.progress = progress

                var sec = timeLeft.toInt()
                var min = sec / 60
                val hour = min / 60
                sec %= 60
                min %= 60

                if (timeLeft > 3600) {
                    bossBar.setTitle("§l" + hour + "시간" + min + "분" + sec + "초 남았습니다.")
                }
                else if (timeLeft > 60) {
                    bossBar.setTitle("§l"  + min + "분" + sec + "초 남았습니다.")
                }
                else {
                    bossBar.setTitle("§l" + timeLeft + "초 남았습니다.")
                }
            }

        }, 0L, 0L)

    }


}