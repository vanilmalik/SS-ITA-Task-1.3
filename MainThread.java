package ss;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class MainThread {
    private static Information information = new Information();
    private static BooleanManagerForStopLoop booleanManagerForStopLoop = new BooleanManagerForStopLoop();
    private static SequenceSearcher sequenceSearcher = new SequenceSearcher(information, booleanManagerForStopLoop);

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("Input file path. If you want to stop programme, press 'E' ");
                String fileName = bufferedReader.readLine();
                if (isStop(fileName)) {
                    bufferedReader.close();
                    break;
                }
                byte[] file = Files.readAllBytes(new File(fileName).toPath());
                sequenceSearcher.setNewFile(true);
                information.sendFile(file);
                receiveAndPrintOccurrence();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isStop(String fileName) {
        if (fileName.equals("E")) {
            sequenceSearcher.setNewFile(false);
            information.sendFile(new byte[]{});
            System.out.println("Exit from programme.");
            return true;
        }
        return false;
    }

    private static void receiveAndPrintOccurrence() {
        int[] occurrences = null;
        byte[] currentSequence = null;
        while (booleanManagerForStopLoop.isLoopInProgress()) {
            currentSequence = information.receiveSequence();
            displayArray(currentSequence);

            occurrences = information.receiveOccurrence();
        }

        if (occurrences != null)
            System.out.println("Length of sequence is " + currentSequence.length + "\nIndex of first occurrence is "
                    + occurrences[0] + ", " + "index of second occurrence is " + occurrences[1] + ".");
    }

    private static void displayArray(byte[] array) {
        System.out.println("The longest sequence for now is");
        for (byte b : array) {
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
