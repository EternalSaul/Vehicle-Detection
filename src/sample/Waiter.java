package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class Waiter {
    public static Task doWaitFor(Process process, TextArea console) {
        return new Task() {
            @Override
            public Void call() {
                InputStream in = null;
                InputStream err = null;
                int exitValue = -1; // returned to caller when p is finished
                try {
                    in = process.getInputStream();
                    err = process.getErrorStream();
                    boolean finished = false; // Set to true when p is finished
                    while (!finished) {
                        try {
                            while (in.available() > 0) {
                                // Print the output of our system call
                                Character c = new Character((char) in.read());
                                if (console!=null)
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        console.setText(Optional.of(console.getText()).orElse("")+c);
                                        console.setScrollTop(Double.MAX_VALUE);
                                    }
                                });
//                        System.out.print(c);
                            }
                            while (err.available() > 0) {
                                // Print the output of our system call
                                Character c = new Character((char) err.read());
                                if (console!=null)
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        console.setText(Optional.of(console.getText()).orElse("")+c);
                                        console.setScrollTop(Double.MAX_VALUE);
                                    }
                                });
//                        System.out.print(c);
                            }
                            // Ask the process for its exitValue. If the process
                            // is not finished, an IllegalThreadStateException
                            // is thrown. If it is finished, we fall through and
                            // the variable finished is set to true.
                            exitValue = process.exitValue();
                            finished = true;
                        } catch (IllegalThreadStateException e) {
                            // Process is not finished yet;
                            // Sleep a little to save on CPU cycles
//                            Thread.currentThread().sleep(10);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (err != null) {
                        try {
                            err.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
        };
    }

}
