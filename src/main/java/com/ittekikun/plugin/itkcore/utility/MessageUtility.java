package com.ittekikun.plugin.itkcore.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.INFO;
import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.SEVERE;
import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.WARNING;

public class MessageUtility
{
    public static void messageToSender(CommandSender sender, MessageType messageType, String message, String prefix, Logger log)
    {
        if(messageType == INFO)
        {
            if (sender instanceof Player)
            {
                sender.sendMessage(prefix + ChatColor.AQUA + "[" + Level.INFO.getLocalizedName() + "]" + ChatColor.WHITE + message);
            } else
            {
                log.info(message);
            }
        }
        else if(messageType == WARNING)
        {
            if (sender instanceof Player)
            {
                sender.sendMessage(prefix + ChatColor.YELLOW + "[" + Level.WARNING.getLocalizedName() + "]" + ChatColor.WHITE + message);
            } else
            {
                log.warning(message);
            }
        }
        else if(messageType == SEVERE)
        {
            if (sender instanceof Player)
            {
                sender.sendMessage(prefix + ChatColor.RED + "[" + Level.SEVERE.getLocalizedName() + "]" + ChatColor.WHITE + message);
            }
            else
            {
                log.severe(message);
            }
        }
    }

    public enum MessageType
    {
        INFO,
        WARNING,
        SEVERE
    }

    /**
     * メッセージをユニキャスト
     *
     * @param message メッセージ
     */
    public static void message(CommandSender sender, String message)
    {
        if (sender != null && message != null)
        {
            message = message.replaceAll("&([0-9a-fk-or])", "§");
            sender.sendMessage(message);
        }
    }

    /**
     * メッセージをブロードキャスト
     *
     * @param message メッセージ
     */
    public static void broadcastMessage(String message)
    {
        if (message != null)
        {
            message = message.replaceAll("&([0-9a-fk-or])", "§");
            Bukkit.broadcastMessage(message);
        }
    }

    /**
     * メッセージをワールドキャスト
     *
     * @param world
     *
     * @param message
     */
    public static void worldcastMessage(World world, String message, Logger log)
    {
        if (world != null && message != null)
        {
            message = message.replaceAll("&([0-9a-fk-or])", "§");
            for (Player player : world.getPlayers())
            {
                player.sendMessage(message);
            }
            log.info("[Worldcast][" + world.getName() + "]: " + message);
        }
    }

    /**
     * メッセージをパーミッションキャスト(指定した権限ユーザにのみ送信)
     *
     * @param permission 受信するための権限ノード
     *
     * @param message    メッセージ
     */
    public static void permcastMessage(String permission, String message, Logger log)
    {
        // OK
        int i = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers())
        {
            if (player.hasPermission(permission))
            {
                message(player, message);
                i++;
            }
        }
        log.info("Received " + i + "players: " + message);
    }
}