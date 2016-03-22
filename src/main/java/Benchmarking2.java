import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;


/**
 * Created by ezapata on 21-Mar-16.
 */
public class Benchmarking2 {
    public static void main(String[] args) throws MqttException {

   /*     int NUM_CLIENTS = Integer.parseInt(args[0]);
        String macId=args[1];
        String HOST=args[2];
        int THING_ID_START = Integer.parseInt(args[3]);
        int THING_ID_END = Integer.parseInt(args[4]);
        int NUM_MESSAGES =Integer.parseInt(args[5]);
        int SLEEP =Integer.parseInt(args[6]);
*/
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
        String HOST="10.100.1.195";
        String macId="001F48E729BB";
        String msj2="{\"body\":\"OK\",\"statusCode\": 200,\"uuid\":\"38e953bd-370d-4d05-b652-d5fd430f9425\"}";

        MqttAsyncClient clientSub = new MqttAsyncClient("tcp://"+ HOST + ":" + 1883,System.currentTimeMillis()+"");
        clientSub.connect();
        while (clientSub.isConnected() == false) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }



        time_start = System.currentTimeMillis();
        Date now = new Date();

        System.out.println(" Time Begin. " + format.format(now));
        int sw=1;


                message ="{\"statusCode\": 200,\"uuid\":\"12345\",\"body\": [\"default_vizix_subscription\"]}";
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                try {
                    clientSub.publish("/v1/flex/" + macId + "/response", mqttMessage);

                } catch (MqttPersistenceException e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                } catch (MqttException e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }



        Date out = new Date();


}
