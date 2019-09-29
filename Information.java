package ss;

import java.util.logging.Logger;

public class Information {
    private Logger log = Logger.getLogger(Information.class.getName());

    private byte[] array;
    private boolean arraySet = false;

    private byte[] file;
    private boolean fileSet = false;

    private int[] occurrences; //[0] - first occurrence, [1] - second occurrence
    private boolean occurrencesSet;

    synchronized byte[] receiveSequence() {
        while (!arraySet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("Information", "receiveSequence", e);
            }
        }

        arraySet = false;
        notify();
        return array;
    }

    synchronized void sendSequence(byte[] array) {
        while (arraySet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("Information", "sendSequence", e);
            }
        }

        this.array = array;
        arraySet = true;
        notify();
    }

    synchronized byte[] receiveFile() {
        while (!fileSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("Information", "receiveFile", e);
            }
        }

        fileSet = false;
        notify();
        return file;
    }

    synchronized void sendFile(byte[] array) {
        while (fileSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("Information", "sendFile", e);
            }
        }

        this.file = array;
        fileSet = true;
        notify();
    }

    synchronized int[] receiveOccurrence() {
        while (!occurrencesSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("Information", "receiveOccurrence", e);
            }
        }

        occurrencesSet = false;
        notify();
        return occurrences;
    }

    synchronized void sendOccurrences(int[] occurrences) {
        while (occurrencesSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.throwing("Information", "sendOccurrences", e);
            }
        }

        this.occurrences = occurrences;
        occurrencesSet = true;
        notify();
    }

}
