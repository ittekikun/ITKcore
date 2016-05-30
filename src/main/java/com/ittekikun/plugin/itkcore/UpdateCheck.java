package com.ittekikun.plugin.itkcore;

import com.ittekikun.plugin.itkcore.utility.MessageUtility;
import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import org.bukkit.Utility;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.INFO;

public class UpdateCheck implements Runnable
{
    private Player player;
    private String nurl;
    private double nowVer;
    private String prefix;
    private Logger log;

    String temp = "§e$Name§fの新しいバージョン§f(§6New=$LastVer§f, §cNow=$NowVer§f)が利用できます。§a$Reason";

    public UpdateCheck(Player player, String url, double nowVer, String prefix, Logger log)
    {
        this.player = player;
        this.nowVer = nowVer;

        this.nurl = "http://verche.ittekikun.com/" + url + "/lastver.txt";

        this.prefix = prefix;
        this.log = log;
    }

    public void run()
    {
        List<String> text = null;
        try
        {
            text = VariousUtility.getHttpServerText(nurl);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String name = text.get(0);

        double ver = Double.valueOf(text.get(2));

        String note = text.get(3);

        if(ver > nowVer)
        {
            String message = replaceKeywords(temp, ver, nowVer, name, note);
            MessageUtility.messageToSender(player, INFO, message, prefix, log);
        }
    }

    private String replaceKeywords(String source,Double LastVer, Double NowVer, String name, String reason)
    {
        String result = source;
        if ( result.contains("$LastVer") )
        {
            result = result.replace("$LastVer", LastVer.toString());
        }
        if ( result.contains("$NowVer") )
        {
            result = result.replace("$NowVer", NowVer.toString());
        }
        if ( result.contains("$Name") )
        {
            result = result.replace("$Name", name);
        }
        if ( result.contains("$Reason") )
        {
            result = result.replace("$Reason", reason);
        }
        return result;
    }
}