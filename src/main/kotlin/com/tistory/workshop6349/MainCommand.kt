package com.tistory.workshop6349

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import javax.swing.text.html.HTML.Tag.P

class MainCommand: Main(), Listener, CommandExecutor {

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        event.joinMessage = "Hello, " + event.player.name
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        
        if (sender is Player) {
            when (command.name) {
                name -> {
                    sender.sendMessage("Hello, " + sender.name + "!")
                }
            }

        }

        return false
    }

}