package com.ittekikun.plugin.itkcore.utility;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class VariousUtility
{
    /**
     * joinArray
     *
     * 指定した所から配列を空白を入れて接続できる。
     * 例: /test a b c d e f
     * ↓
     * args[0] = a, args[1] = b, args[2] = c, args[3] = d....
     * ↓
     * joinArray(args, 1) = "b c d e f"
     *
     * @param par1 繋げたい配列（配列String型）
     * @param par2 どこの配列から繋げたいか（int型）
     *
     * @return 繋げた文字列を返す
     */
    public static String joinArray(String[] par1, int par2)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (int a = par2; a < par1.length; ++a)
        {
            if (a > par2)
            {
                stringBuilder.append(" ");
            }

            String s = par1[a];

            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }


    /**
     * 文字列が整数値に変換可能かどうかを判定する
     *
     * @param source 変換対象の文字列
     * @return 整数に変換可能かどうか
     *
     * @author https://github.com/ucchyocean/
     */
    public static boolean checkIntParse(String source)
    {

        return source.matches("^-?[0-9]{1,9}$");
    }
    /**
     * HTTPサーバー上のテキストの内容を読み込む
     *
     * @param par1 URL
     * @return テキストをListで返す
     */
    public static List getHttpServerText(String par1) throws IOException
    {
        URL url = new URL(par1);
        InputStream i = url.openConnection().getInputStream();

        BufferedReader buf = new BufferedReader(new InputStreamReader(i, "UTF-8"));

        String line;
        List<String> arrayList = new ArrayList();

        while ((line = buf.readLine()) != null)
        {
            arrayList.add(line);
        }
        buf.close();

        return arrayList;
    }

    /**
     * timeGetter
     * フォーマットはここを参照されたし
     * http://java-reference.sakuraweb.com/java_date_format.html
     *
     * @param format 出力する時刻のフォーマット（String）
     *
     * @return 指定したフォーマットの形で現時刻
     */
    public static String timeGetter(String format)
    {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time = sdf.format(date);

        return time;
    }

    /**
     * simpleTimeGetter
     * とりあえず今の時間を返す
     *
     * @return 現時刻
     */
    public static String simpleTimeGetter()
    {
        Calendar calendar = Calendar.getInstance();
        String time = calendar.getTime().toString();

        return time;
    }

    public static Object decodeObject(File file, String fileName) throws IOException, ClassNotFoundException
    {
        InputStream is;
        JarFile jar;

        jar = new JarFile(file);
        ZipEntry zipEntry = jar.getEntry(fileName);
        is = jar.getInputStream(zipEntry);

        byte[] indata = new byte[(int)zipEntry.getSize()];
        is.read(indata);
        is.close();

        byte[] outdata = Base64.decodeBase64(indata);

        ByteArrayInputStream bais = new ByteArrayInputStream(outdata);
        ObjectInputStream ois = new ObjectInputStream(bais);

        Object object= ois.readObject();
        bais.close();
        ois.close();

        return object;
    }
}