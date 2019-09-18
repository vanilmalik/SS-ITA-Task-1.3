package softserve.task3_1;

import java.util.Arrays;
import java.util.logging.Logger;

public class SequenceSearcher implements Runnable {
    private Logger log = Logger.getLogger(SequenceSearcher.class.getName());

    private Information information;
    private BooleanManagerForStopLoop booleans;

    private boolean newFile;

    public synchronized void setNewFile(boolean newFile) {
        this.newFile = newFile;
    }

    public SequenceSearcher(Information information, BooleanManagerForStopLoop booleans) {
        this.information = information;
        this.booleans = booleans;
        this.newFile = true;

        new Thread(this).start(); //start thread after creating
    }

    @Override
    public void run() {
        log.info("Side thread is running.");
        while (newFile) {
            byte[] str = information.receiveFile();
            byte[] lrs = {};
            int n = str.length;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    //Checks for the largest common factors in every substring
                    byte[] x = lcp(Arrays.copyOfRange(str, i, n), Arrays.copyOfRange(str, j, n));

                    //If the current prefix is greater than previous one
                    //then it takes the current one as longest repeating sequence
                    if (x.length > lrs.length) {
                        lrs = x;
                        booleans.setLoopInProgess(true);
                        information.sendSequence(lrs);
                        information.sendOccurrences(new int[]{i, j});
                    }
                }
            }

            booleans.setLoopInProgess(false);
        }
    }

    private static byte[] lcp(byte[] subarrayS, byte[] subarrayT){
        int n = Math.min(subarrayS.length, subarrayT.length);
        for(int i = 0; i < n; i++){
            if(subarrayS[i] != subarrayT[i] ){
                return Arrays.copyOfRange(subarrayS, 0, i);
            }
        }
        return Arrays.copyOfRange(subarrayS, 0, n);
    }
}