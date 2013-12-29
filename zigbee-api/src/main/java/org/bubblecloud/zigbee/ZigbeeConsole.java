package org.bubblecloud.zigbee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Zigbee command line console is an example application demonstrating usage of Zigbee API.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigbeeConsole {

    private static boolean shutdown = false;

    private static Thread mainThread = null;

    private static Map<String, ConsoleCommand> commands = new HashMap<String, ConsoleCommand>();

    /**
     * Main method of this console application.
     *
     * @param args the console arguments
     */
    public static void main(final String[] args) {
        mainThread = Thread.currentThread();

        final String serialPortName;
        final int channel;
        final int pan;
        final boolean resetNetwork;
        try {
            serialPortName = args[0];
            channel = Integer.parseInt(args[1]);
            pan = Integer.parseInt(args[2]);
            resetNetwork = args[3].equals("true");
        } catch (final Throwable t) {
            System.out.println("Syntax: java -jar zigbee4java.jar <serial port name> <channel> <pan> <reset network>");
            return;
        }

        System.out.print("Zigbee API starting up...");
        final ZigbeeApi zigbeeApi = new ZigbeeApi(serialPortName, pan, channel, resetNetwork);
        if (!zigbeeApi.startup()) {
            write(". [FAIL]");
            return;
        } else {
            write(". [OK]");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdown = true;
                try {
                    System.in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mainThread.interrupt();
                    mainThread.join();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }));

        System.out.print("Browsing network ...");
        while (!shutdown && !zigbeeApi.isInitialBrowsingComplete()) {
            System.out.print('.');
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println(". [OK]");
        System.out.println("Found " + zigbeeApi.getDevices().size() + " nodes.");

        System.out.println("Zigbee console ready.");

        String command;
        while (!shutdown && (command = read()) != null) {
            if ("quit".equals(command) || "q".equals(command)) {
                break;
            }
            process(zigbeeApi, command);
        }

        zigbeeApi.shutdown();
    }

    private static void process(final ZigbeeApi zigbeeApi, final String command) {
        final String[] args = command.split(" ");
        if (commands.containsKey(args[0])) {
            commands.get(args[0]).process(zigbeeApi, args);
        }
    }

    /**
     * Writes line to console.
     *
     * @param line the line
     */
    private static void write(final String line) {
        System.out.println(line);
    }

    /**
     * Reads line from console.
     *
     * @return line read from console or null if exception occurred.
     */
    private static String read() {
        System.out.print("> ");
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferRead.readLine();
            return s;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static interface ConsoleCommand {
        void process(final ZigbeeApi zigbeeApi, final String[] args);
    }

    static {
        commands.put("quit", null);
        commands.put("help", new Help());
    }

    private static class Help implements ConsoleCommand {
        public void process(final ZigbeeApi zigbeeApi, final String[] args) {
            final List<String> commandList = new ArrayList<String>(commands.keySet());
            Collections.sort(commandList);
            write("Commands:");
            for (final String command : commands.keySet()) {
                write(command);
            }
        }
    }
}
