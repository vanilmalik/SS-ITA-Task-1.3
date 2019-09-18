package softserve.task3_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class MainThread {
    public static void main(String[] args) {
        Information information = new Information();
        BooleanManagerForStopLoop booleanManagerForStopLoop = new BooleanManagerForStopLoop();
        SequenceSearcher sequenceSearcher = new SequenceSearcher(information, booleanManagerForStopLoop);

        String fileName = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.println("Input file path. If you want to stop programme, press 'E' ");

                fileName = bufferedReader.readLine();
                if (fileName.equals("E")) {
                    sequenceSearcher.setNewFile(false);
                    information.sendFile(new byte[]{});
                    System.out.println("Exit from programme.");
                    break; //stop loop after user input 'E'
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] file = { };
            try {
                if (fileName != null)
                    file = Files.readAllBytes(new File(fileName).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            sequenceSearcher.setNewFile(true);
            information.sendFile(file);

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

    }

    private static void displayArray(byte[] array) {
        System.out.println("The longest sequence for now is");
        for (byte b : array) {
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
