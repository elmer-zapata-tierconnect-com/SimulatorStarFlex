import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;


/**
 * Created by ezapata on 21-Mar-16.
 */
public class Benchmarking {
    public static void main(String[] args) throws MqttException {

                		int NUM_CLIENTS = Integer.parseInt(args[0]);
                        String macId=args[1];
                		String HOST=args[2];
                int THING_ID_START = Integer.parseInt(args[3]);
        		int THING_ID_END = Integer.parseInt(args[4]);
        		int NUM_MESSAGES =Integer.parseInt(args[5]);
        		int SLEEP =Integer.parseInt(args[6]);

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

        for (int i1 = 0; i1 < NUM_MESSAGES; i1++) {
            for (int i = THING_ID_START; i <= THING_ID_END && i1 < NUM_MESSAGES; i++) {

                message = "[{\"type\":\"TagReadData\",\"timestamp\":" + System.currentTimeMillis() + ",\"seqNum\":155899,\"txAntennaPort\":\"PORT_1\"," +
                        "\"txExpanderPort\":\"NONE\",\"transmitSource\":\"INTERNAL\",\"data\":\"0x3000"+i+"2426A\"}]";
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                try {
                    client[i % NUM_CLIENTS].publish("/v1/flex/" + macId + "/data", mqttMessage);
                    i1++;

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
        }


        Date out = new Date();
        System.out.println(" Time end. " + format.format(out));
        time_end = System.currentTimeMillis();
        time = time_end - time_start;
        System.out.println("time=>" + time);
        double res = (double) (THING_ID_END - THING_ID_START) / time;

        System.out.printf(" is sending %d msg %.4f by second %n  ", NUM_MESSAGES, res);

    }
}
