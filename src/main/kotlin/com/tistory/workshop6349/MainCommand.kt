package com.tistory.workshop6349

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object MainCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        
        if (sender is Player) {
            sender.sendMessage("Hello")
            when (args[0]) {
                "name" -> {
                    sender.sendMessage("Hello, " + sender.name + "!")
                }
            }

        }

        return false
    }

}