package com.tistory.workshop6349.commands

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class GachaCommand(private val plugin: Plugin) : CommandExecutor {

    private var time = 0
    private var bossBar: BossBar ?= null
    private var addedTime = 0L

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (label.equals("timer", true)) {
                if (args.size == 2) {
                    if (args[0].equals("add", true)) {
                        if (bossBar != null) {
                            sender.sendMessage("이미 " + bossBar!!.title + " 타이머가 진행 중입니다.")
                            return true
                        }

                        bossBar = Bukkit.createBossBar("timer", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC)
                        time = args[1].toInt()
                        addedTime = System.currentTimeMillis() / 1000

                        for (onLinePlayer in Bukkit.getOnlinePlayers()) {
                            bossBar!!.addPlayer(onLinePlayer)
                        }
                        bossBar!!.isVisible = true
                        sender.sendMessage("타이머를 생성하였습니다.")
                        run()
                    }
                }
                else {
                    sender.sendMessage("잘못된 명령입니다.")
                }
            }
        }

        return true
    }

    private fun run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, Runnable {
            if (bossBar != null) {
                val timeLeft = (addedTime + time - System.currentTimeMillis() / 1000).toDouble()
                val progress = timeLeft / time

                if (timeLeft <= 0) {
                    bossBar!!.isVisible = false
                    bossBar = null
                    return@Runnable
                }

                for (player in Bukkit.getOnlinePlayers()) {
                    bossBar!!.addPlayer(player)
                }

                bossBar!!.progress = progress

                var sec = timeLeft.toInt()
                var min = sec / 60
                val hour = min / 60
                sec %= 60
                min %= 60

                if (timeLeft > 3600) {
                    bossBar!!.setTitle("§l" + hour + "시간" + min + "분" + sec + "초 남았습니다.")
                }
                else if (timeLeft > 60) {
                    bossBar!!.setTitle("§l"  + min + "분" + sec + "초 남았습니다.")
                }
                else {
                    bossBar!!.setTitle("§l" + timeLeft + "초 남았습니다.")
                }

            }
        }, 0L, 0L)
    }
}
