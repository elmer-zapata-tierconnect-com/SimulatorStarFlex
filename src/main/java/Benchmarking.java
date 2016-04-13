import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;


/**
 * Created by ezapata on 21-Mar-16.
 */

public class Benchmarking {
    public static String buildMessage(int cant, String timestamp,  int start) {
        String res = "[";
        String as[] = {"PORT_1", "PORT_2", "PORT_3", "PORT_4"};
        for (int j = start; j < (start + cant-1); j++) {
            System.out.print(j + " ");
            res += "{\"type\":\"TagReadData\",\"timestamp\":" + timestamp + ",\"seqNum\":587775,\"txAntennaPort\":\"" + as[(j*(int)(Math.random()*4)) % 4] + "\",\"txExpanderPort\":\"NONE\",\"transmitSource\":\"INTERNAL\",\"data\":\"0x3000" + String.format("%021d", j) + "426A\"},";
        }
        res += "{\"type\":\"TagReadData\",\"timestamp\":" + timestamp + ",\"seqNum\":587775,\"txAntennaPort\":\"" + as[0] + "\",\"txExpanderPort\":\"NONE\",\"transmitSource\":\"INTERNAL\",\"data\":\"0x3000" + String.format("%021d",(start+cant)-1) + "426A\"}";
        res += "]";
        System.out.println();

        return res;
    }

    public static void main(String[] args) throws MqttException {

        int NUM_CLIENTS = Integer.parseInt(args[0]);
        String macId = args[1];
        String HOST = args[2];
        int THING_ID_START = Integer.parseInt(args[3]);
        int THING_ID_END = Integer.parseInt(args[4]);
        int NUM_MESSAGES = Integer.parseInt(args[5]);
        int SLEEP = Integer.parseInt(args[6]);
        int FREQ = Integer.parseInt(args[7]);

       /* Scanner lee = new Scanner(System.in);
        String macId = lee.next();
        int NUM_CLIENTS = 10;
        int THING_ID_START = 1;
        int THING_ID_END = 10;
        int NUM_MESSAGES = 100;
        int SLEEP = 1000;*/
        String message = "a";
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

        time_start = System.currentTimeMillis();
        Date now = new Date();

        System.out.println(" Time Begin. " + format.format(now));

        clientPub.connect();
        while (clientPub.isConnected() == false) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String message2 = "{\"statusCode\": 200,\"uuid\":\"12345\",\"body\": [\"default_vizix_subscription\"]}";
        MqttMessage mqttMessage2 = new MqttMessage(message2.getBytes());
        try {
            clientPub.publish("/v1/flex/" + macId + "/response", mqttMessage2);

        } catch (MqttPersistenceException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        int idStart = THING_ID_START;
        for (int i1 = 1; i1 <= NUM_MESSAGES; i1++) {

            Date now1 = new Date();

           // System.out.println(" Time Begin. " + format.format(now));

            message = buildMessage(FREQ, now1.toGMTString(), idStart);

            idStart += FREQ;
            if (idStart >= THING_ID_END) {
                idStart=THING_ID_START;
            }
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            try {
                client[0].publish("/v1/flex/" + macId + "/data", mqttMessage);



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


        Date out = new Date();
        System.out.println(" Time end. " + format.format(out));
        time_end = System.currentTimeMillis();
        time = time_end - time_start;
        System.out.println("time=>" + time);
        double res = (double) (THING_ID_END - THING_ID_START) / time;

        System.out.printf(" is sending %d msg %.4f by second %n  ", NUM_MESSAGES, res);
        for (int i = 0; i < client.length; i++) client[i].close();

        clientPub.close();

    }
}
