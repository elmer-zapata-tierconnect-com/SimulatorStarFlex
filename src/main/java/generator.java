import java.io.*;

/**
 * Created by root on 02-03-17.
 */
public class generator {


    public static void main(String[]arg){
        String[] macId = {
                "001F48F911BE",
                "001F48C4DCF1",
                "001F48F024FF",
                "001F48C4D8C6",
                "001F4881F931",
                "001F48F3DEE5",
                "001F48BBDEF ",
                "001F48C39F44",
                "001F48C5F9B4",
                "001F489004D6",
                "001F4898AF0A",
                "001F48EBAD98",
                "001F48498B33",
                "001F48B73CCE",
                "001F489420C2",
                "001F48D8C087",
                "001F48E6A825",
                "001F48A42992",
                "001F48EEA093",
                "001F48D673E5",
                "001F48E26E9F",
                "001F48D6204B",
                "001F488E05B3",
                "001F48EA378X",
                "001F48103C0F",
                "001F48B95850",
                "001F48D60628",
                "001F4809E1D2",
                "001F48BB6F79",
                "001F4803A86D",
                "001F48882C68",
                "001F488BC174",
                "001F48FDF7E6",
                "001F482B699B",
                "001F4891D43B",
                "001F48A3D77E",
                "001F4861F52A",
                "001F48D1FA72",
                "001F4850029A",
                "001F48F3C9E3",
                "001F48931BB8",
                "001F48A4079F",
                "001F48031FEC",
                "001F482F962A",
                "001F486E703C",
                "001F486C306E",
                "001F48FAA1E3",
                "001F488CDEDB",
                "001f469e4347",
                "001F484EA378",
                "001F48E7B05C",
                "001F485FD694",
                "001F48A06723",
                "001F485AE3CC",
                "001F48199199",
                "001F48EA81EB",
                "001F48F8F401",
                "001F48441F6F",
                "001F48575E47",
                "001F48F0BC28",
                "001F48FB5F03",
                "001F48BD4178",
                "002F48D60628",
                "002F4809E1D2",
                "002F48BB6F79",
                "002F4803A86D",
                "002F48882C68",
                "002F488BC174",
                "002F48FDF7E6",
                "002F482B699B",
                "002F4891D43B",
                "002F48A3D77E",
                "002F4861F52A",
                "002F48D1FA72",
                "002F4850029A",
                "002F48F3C9E3",
                "002F48931BB8",
                "002F48A4079F",
                "002F48031FEC",
                "002F482F962A",
                "002F486E703C",
                "002F486C306E",
                "002F48FAA1E3",
                "002F488CDEDB",
                "002f469e4347",
                "002F484EA378",
                "002F48E7B05C",
                "002F485FD694",
                "002F48A06723",
                "002F485AE3CC",
                "002F48199199",
                "002F48EA81EB",
                "002F48F8F401",
                "002F48441F6F",
                "002F48575E47",
                "002F48F0BC28",
                "002F48FB5F03",
                "002F48BD4178",
                "003F48BD4178",
                "004F48BD4178"
        };

        Writer writer = null;
        String resFinal="Thing Type Code"+","+"Thing Serial"+","+"Thing Name"+","+"ownerGroup"+"\n";
        File file = new File(".");
        file = new File(file.getAbsolutePath() + "thingsToExport.csv");
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file.getAbsolutePath()), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       // for (int i = 0; i <=100 ; i++) {
            for (int j = 0; j <macId.length; j++) {

                   System.out.print("entro");

                    //String res="AUTO"+String.format("%03d",i)+String.format("%014d", j);
                     resFinal=resFinal+"Starflex"+","+macId[j]+","+macId[j]+","+">BP>KW"+"\n";


            }
        //}

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
