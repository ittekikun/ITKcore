package com.ittekikun.plugin.itkcore;

import com.ittekikun.plugin.itkcore.logger.LogFilter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ITKCore  extends JavaPlugin
{
    public static ITKCore instance;
    public static Logger log;
    public static final String prefix = "[MineTweet_for_Plugin] ";
    public static PluginManager pluginManager;
    public static boolean forceDisableMode;

    @Override
    public void onEnable()
    {
        instance = this;
        pluginManager = instance.getServer().getPluginManager();

        log = Logger.getLogger("ITKCore");
        log.setFilter(new LogFilter(prefix));

        if(!(Double.parseDouble(System.getProperty("java.specification.version")) >= 1.7))
        {
            //JAVA6以前の環境では動きません
            log.severe("JAVA7以上がインストールされていません。");
            log.severe("プラグインを無効化します。");
            forceDisableMode = true;
            pluginManager.disablePlugin(this);

            return;
        }
    }

    @Override
    public void onDisable()
    {

    }
}
