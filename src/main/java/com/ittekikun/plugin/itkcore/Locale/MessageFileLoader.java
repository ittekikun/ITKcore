package com.ittekikun.plugin.itkcore.locale;

import java.io.*;

import com.ittekikun.plugin.itkcore.utility.FileUtility;
import com.ittekikun.plugin.itkcore.utility.BukkitUtility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageFileLoader
{
    private final String fileName;
    private String fullFileName;

    private final String folderName;
    private final File target;

    private final File pluginFile;
    private final File dataFolder;

    private File messageFile;
    private FileConfiguration fileConfiguration;

    private final String language;

    public MessageFileLoader(File dataFolder, File pluginFile, String folderName, String fileName, String language)
    {
        if(dataFolder == null)
        {
            throw new IllegalStateException();
        }

        this.fileName = fileName;
        this.language = language;
        this.folderName = folderName;

        this.dataFolder = dataFolder;
        this.pluginFile = pluginFile;

        this.target = new File(dataFolder, folderName);

        fullFileName = this.fileName + "_" + this.language + ".yml";
        this.messageFile = new File(target, fullFileName);
    }

    public void reloadConfig()
    {
        fileConfiguration = YamlConfiguration.loadConfiguration(messageFile);
    }

    public FileConfiguration getConfig()
    {
        if(fileConfiguration == null)
        {
            this.reloadConfig();
        }
        return fileConfiguration;
    }

    public void saveMessages()
    {
        if(!target.exists())
        {
            if(BukkitUtility.isCB19orLater())
            {
                FileUtility.copyRawFolderFromJar(pluginFile, dataFolder, folderName);
            }
            else
            {
                FileUtility.copyFolderFromJar(pluginFile, dataFolder, folderName);
            }
        }

        if(!messageFile.exists())
        {
            //選択した言語がなかったら強制的に日本語にする
            fullFileName = this.fileName + "_" + "ja" + ".yml";
            this.messageFile = new File(target, fullFileName);
        }
    }

    public String loadMessage(String source)
    {
        //System.out.println(messageFile.getName());
        String name = getConfig().getString(source, source);
        return name;
    }
}