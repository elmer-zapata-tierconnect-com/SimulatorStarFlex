import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;


/**
 * Created by ezapata on 21-Mar-16.
 */

public class Benchmarking {
    public static String buildMessage(int index,int cant, String timestamp, int start) {
        String res = "[";
        String as[] = {"PORT_1", "PORT_2", "PORT_3", "PORT_4"};
        for (int j = start; j < (start + cant - 1); j++) {
           // System.out.print(String.format("%03d",index)+String.format("%018d", j));
            res += "{\"type\":\"TagReadData\",\"timestamp\":" + timestamp + ",\"seqNum\":587775,\"txAntennaPort\":\"" + as[(j) % 4] + "\",\"txExpanderPort\":\"NONE\",\"transmitSource\":\"INTERNAL\",\"data\":\"0x3000"+"AUTO" + String.format("%03d",index)+String.format("%014d", j) + "426A\",\"alias\":\"test\"},";
        }
        res += "{\"type\":\"TagReadData\",\"timestamp\":" + timestamp + ",\"seqNum\":587775,\"txAntennaPort\":\"" + as[(int)(Math.random()*4)] + "\",\"txExpanderPort\":\"NONE\",\"transmitSource\":\"INTERNAL\",\"data\":\"0x3000"+"AUTO"+ String.format("%03d",index) + String.format("%014d", (start + cant) - 1) + "426A\",\"alias\":\"test\"}";
        res += "]";
        //System.out.println();

        return res;
    }

    public static void main(String[] args) throws MqttException, InterruptedException {

        //int NUM_CLIENTS = Integer.parseInt(args[0]);
        int NUM_CLIENTS = 100;
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
//        System.out.println("SAS"+macId.length);
//        String HOST = args[2];
//        int THING_ID_START = Integer.parseInt(args[3]);
//        int THING_ID_END = Integer.parseInt(args[4]);
//        int NUM_MESSAGES = Integer.parseInt(args[5]);
//        int SLEEP = Integer.parseInt(args[6]);
//        int FREQ = Integer.parseInt(args[7]);
        String HOST = "10.100.1.195";
        int THING_ID_START = 100;
        int THING_ID_END = 200;
        int NUM_MESSAGES = 200;
        int SLEEP = 200;
        int FREQ = 10;
        String message[] = new String[NUM_CLIENTS];
        long time_start, time_end, time;
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss SSS");

        MqttAsyncClient client[] = new MqttAsyncClient[NUM_CLIENTS];
        //MqttAsyncClient clientSub = new MqttAsyncClient("tcp://"+ HOST + ":" + 1883,System.currentTimeMillis()+"");
        MqttAsyncClient clientPub = new MqttAsyncClient("tcp://" + HOST + ":" + 1883, System.currentTimeMillis() + "");
        for (int i = 0; i < client.length; i++) {
            String clientId = System.currentTimeMillis() + "";
            client[i] = new MqttAsyncClient("tcp://"
                    + HOST + ":"
                    + 1883, clientId);
            client[i].connect();
            Thread.sleep(50);
        }

        for (int i = 0; i < client.length; i++) {

            while (client[i].isConnected() == false) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }



        clientPub.connect();
        while (clientPub.isConnected() == false) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        int idStart = THING_ID_START;
        for (int i1 = 1; i1 <= NUM_MESSAGES; i1++) {

            Date now1 = new Date();

            // System.out.println(" Time Begin. " + format.format(now));


            idStart += FREQ;
            if (idStart >= THING_ID_END) {
                idStart = THING_ID_START;
            }
            time_start = System.currentTimeMillis();
            Date now = new Date();

            System.out.println(" Time Begin. "+i1+"  " + format.format(now));
            for (int i = 0; i < NUM_CLIENTS; i++) {
                message[i] = buildMessage(i,FREQ, now1.getTime() + "", idStart);
            }

            try {

                for (int i = 0; i < NUM_CLIENTS; i++) {
                    MqttMessage mqttMessage = new MqttMessage(message[i].getBytes());
                    client[i].publish("/v1/flex/" + macId[i] + "/data", mqttMessage);
                }
                Date out = new Date();
                System.out.println(" Time end. "+i1+" " + format.format(out));
                time_end = System.currentTimeMillis();
                time = time_end - time_start;
                //System.out.println("time=>" + time);


            } catch (MqttPersistenceException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            } catch (MqttException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }



       // double res = (double) (THING_ID_END - THING_ID_START) / time;

     //   System.out.printf(" is sending %d msg %.4f by second %n  ", NUM_MESSAGES, res);
        for (int i = 0; i < client.length; i++) client[i].close();

        clientPub.close();

    }
}
