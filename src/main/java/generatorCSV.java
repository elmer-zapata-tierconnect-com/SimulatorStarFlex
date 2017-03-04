import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by root on 02-03-17.
 */
public class generatorCSV {


    public static void main(String[]arg){

        File input = new File("/home/elmer/repos/SimulatorStarFlex/cpuFile.csv");
        Writer writer = null;
        String resFinal="TIME"+","+"TOMCATCPU"+","+"TOMCATMEMORY"+","+"CORECPU"+","+"COREMEMORY"+"\n";
        File file = new File(".");
        file = new File(file.getAbsolutePath() + "cpuFileConvertedWThread.csv");
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file.getAbsolutePath()), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileReader fr = null;
        try {
            fr = new FileReader(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            int c=0;String tmp="";String tmp2="";
            while ((line = br.readLine()) != null){c++;
                switch (c%3){
                    case 1:{StringTokenizer a =new StringTokenizer(line," ");tmp=a.nextToken()+","+a.nextToken()+",";};break;
                    case 2:{StringTokenizer a =new StringTokenizer(line," ");tmp2=a.nextToken()+","+a.nextToken();};break;
                    case 0:{resFinal+=line.trim()+","+tmp+tmp2+"\n";tmp="";tmp2="";break;}

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.write(resFinal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
