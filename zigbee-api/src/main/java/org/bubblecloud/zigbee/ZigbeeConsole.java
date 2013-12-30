package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Subscription;

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
            System.out.println("Syntax: java -jar zigbee4java.jar SERIALPORT CHANNEL PAN RESET");
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
            process(zigbeeApi, command);
        }

        zigbeeApi.shutdown();
    }

    private static void process(final ZigbeeApi zigbeeApi, final String command) {
        final String[] args = command.split(" ");
        try {
            if (commands.containsKey(args[0])) {
                final ConsoleCommand consoleCommand = commands.get(args[0]);
                if (!consoleCommand.process(zigbeeApi, args)) {
                    write(consoleCommand.getSyntax());
                }
            } else {
                write("Uknown command. Use 'help' command to list available commands.");
            }
        } catch (final Exception e) {
            write("Exception in command execution: ");
            e.printStackTrace();
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
        String getDescription();
        String getSyntax();
        boolean process(final ZigbeeApi zigbeeApi, final String[] args);
    }

    static {
        commands.put("quit", new QuitCommand());
        commands.put("help", new HelpCommand());
        commands.put("list", new ListCommand());
        commands.put("desc", new DescribeCommand());
    }

    private static class QuitCommand implements ConsoleCommand {
        public String getDescription() {
            return "Quits console.";
        }

        public String getSyntax() {
            return "quit";
        }

        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {
            shutdown = true;
            return true;
        }
    }

    private static class HelpCommand implements ConsoleCommand {
        public String getDescription() {
            return "View command help.";
        }

        public String getSyntax() {
            return "help [command]";
        }

        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {

            if (args.length == 2) {
                if (commands.containsKey(args[1])) {
                    final ConsoleCommand command = commands.get(args[1]);
                    System.out.println(command.getDescription());
                    System.out.println("");
                    System.out.println("Syntax: " + command.getSyntax());
                } else {
                    return false;
                }
            } else if (args.length == 1) {
                final List<String> commandList = new ArrayList<String>(commands.keySet());
                Collections.sort(commandList);
                write("Commands:");
                for (final String command : commands.keySet()) {
                    write(command + " - " + commands.get(command).getDescription());
                }
            } else {
                return false;
            }

            return true;
        }
    }

    private static class ListCommand implements ConsoleCommand {
        public String getDescription() {
            return "Lists devices.";
        }

        public String getSyntax() {
            return "list";
        }

        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {
            for (final Device device : zigbeeApi.getDevices()) {
                System.out.println(device.getEndPointId() + " : " + device.getDeviceType());
            }
            return true;
        }
    }

    private static class DescribeCommand implements ConsoleCommand {
        public String getDescription() {
            return "Describes a device.";
        }

        public String getSyntax() {
            return "desc DEVICEID";
        }

        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = zigbeeApi.getDevice(args[1]);
            if (device == null) {
                return false;
            }

            write(device.getEndPointId() + " : " + device.getDeviceType());
            write("Network Address  : " + device.getNode().getNetworkAddress());
            write("Extended Address : " + device.getNode().getIEEEAddress());
            write("Endpoint Address : " + device.getEndPointAddress());
            write("Device Type      : " + ZigbeeApiConstants.getDeviceName(device.getDeviceTypeId()));
            write("Device Category  : " + ZigbeeApiConstants.getCategoryDeviceName(device.getDeviceTypeId()));
            write("Device Version   : " + device.getDeviceVersion());
            write("Input Clusters   : ");
            for (int i : device.getInputClusters()) {
                final Cluster cluster = device.getCluster(i);
                if (cluster == null) {
                    write(i + ") No implementation.");
                    continue;
                }
                write(i + ") " + cluster.getName());
                for (final Subscription subscription : cluster.getActiveSubscriptions()) {
                    write(" - Subscription with " + subscription.getReportListenersCount() + " report listeners.");
                }
            }
            write("Output Clusters  : ");
            for (int i : device.getOutputClusters()) {
                final Cluster cluster = device.getCluster(i);
                if (cluster == null) {
                    write(i + ") No implementation.");
                    continue;
                }
                write(i + ") " + cluster.getName());
                for (final Subscription subscription : cluster.getActiveSubscriptions()) {
                    write(" - Subscription with " + subscription.getReportListenersCount() + " report listeners.");
                }
            }

            return true;
        }
    }
}
