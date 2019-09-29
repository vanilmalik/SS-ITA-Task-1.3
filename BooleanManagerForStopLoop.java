package ss;

import java.util.logging.Logger;

public class BooleanManagerForStopLoop {
    private Logger log = Logger.getLogger(Information.class.getName());

    private boolean loopInProgress;
    private boolean loopInProgressSet = false;

    synchronized boolean isLoopInProgress() {
        while (!loopInProgressSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("BooleanManagerForStopLoop", "isLoopInProgress", e);
            }
        }

        loopInProgressSet = false;
        notify();
        return loopInProgress;
    }

    synchronized void setLoopInProgess(boolean setfile) {
        while (loopInProgressSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("BooleanManagerForStopLoop", "setLoopInProgess", e);
            }
        }

        this.loopInProgress = setfile;
        loopInProgressSet = true;
        notify();
    }
}
