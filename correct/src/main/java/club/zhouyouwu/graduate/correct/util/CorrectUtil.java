package club.zhouyouwu.graduate.correct.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CorrectUtil {

    public static Map<String, String> correctProgramQuestion(String answerFile){
        String cmd = "cmd /c python " + answerFile;
        StringBuilder result = new StringBuilder();

        Map<String, String> map = new HashMap<>();
        map.put("answered", "true");
        Process p;
        try{
            p = Runtime.getRuntime().exec(cmd);
            InputStream fis=p.getInputStream();
            InputStream error = p.getErrorStream();
            InputStreamReader isr=new InputStreamReader(fis, "GBK");
            InputStreamReader isr2=new InputStreamReader(error, "GBK");
            BufferedReader br=new BufferedReader(isr);
            BufferedReader br2=new BufferedReader(isr2);

            String line=null;
            while((line=br.readLine())!=null){
                line+="\n";
                result.append(line);
            }
            while ((line=br2.readLine())!=null){
                line+="\n";
                result.append(line);
                map.put("answered", "false");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        map.put("result", result.toString());
        return map;
    }

    public static void createFile(String filePath, String fileName){
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
}
