package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.ZigbeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Subscription;
import org.bubblecloud.zigbee.network.impl.ZigbeeNetworkManagerException;

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
    /**
     * The main thread.
     */
    private static Thread mainThread = null;

    /**
     * The flag reflecting that shutdown is in process.
     */
    private static boolean shutdown = false;

    /**
     * Map of registered commands and their implementations.
     */
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
        if (command.length() == 0) {
            return;
        }
        final String[] args = command.split(" ");
        try {
            if (commands.containsKey(args[0])) {
                process(zigbeeApi, args, args[0]);
                return;
            } else {
                for (final String key : commands.keySet()) {
                    if (key.charAt(0) == command.charAt(0)) {
                        process(zigbeeApi, args, key);
                        return;
                    }
                }
                write("Uknown command. Use 'help' command to list available commands.");
            }
        } catch (final Exception e) {
            write("Exception in command execution: ");
            e.printStackTrace();
        }
    }

    private static void process(ZigbeeApi zigbeeApi, String[] args, String key) {
        final ConsoleCommand consoleCommand = commands.get(key);
        if (!consoleCommand.process(zigbeeApi, args)) {
            write(consoleCommand.getSyntax());
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

    /**
     * Gets device from Zigbee API either with index or endpoint ID
     * @param zigbeeApi the zigbee API
     * @param deviceIdentifier the device identifier
     * @return
     */
    private static Device getDeviceByIndexOrEndpointId(ZigbeeApi zigbeeApi, String deviceIdentifier) {
        Device device;
        try {
            device = zigbeeApi.getDevices().get(Integer.parseInt(deviceIdentifier));
        } catch (final NumberFormatException e) {
            device = zigbeeApi.getDevice(deviceIdentifier);
        }
        return device;
    }

    static {
        commands.put("quit", new QuitCommand());
        commands.put("help", new HelpCommand());
        commands.put("list", new ListCommand());
        commands.put("desc", new DescribeCommand());
        commands.put("bind", new BindCommand());
        commands.put("unbind", new UnbindCommand());
        commands.put("on", new OnCommand());
        commands.put("off", new OffCommand());
    }

    private static interface ConsoleCommand {
        String getDescription();
        String getSyntax();
        boolean process(final ZigbeeApi zigbeeApi, final String[] args);
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
            final List<Device> devices = zigbeeApi.getDevices();
            for (int i = 0; i < devices.size(); i++) {
                final Device device = devices.get(i);
                System.out.println(i + ") " + device.getEndpointId() + " : " + device.getDeviceType());
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

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);

            if (device == null) {
                return false;
            }

            write("Network Address  : " + device.getNetworkAddress());
            write("Extended Address : " + device.getIEEEAddress());
            write("Endpoint Address : " + device.getEndPointAddress());
            write("Device Type      : " + ZigbeeApiConstants.getDeviceName(device.getDeviceTypeId()));
            write("Device Category  : " + ZigbeeApiConstants.getCategoryDeviceName(device.getDeviceTypeId()));
            write("Device Version   : " + device.getDeviceVersion());
            write("Input Clusters   : ");
            for (int i : device.getInputClusters()) {
                final Cluster cluster = device.getCluster(i);
                write("                 : " + i + ") " + ZigbeeApiConstants.getClusterName(i));
                if (cluster != null) {
                    for (final Subscription subscription : cluster.getActiveSubscriptions()) {
                        write("                 :  * Subscription: " + subscription.getReportListenersCount() + " listeners.");
                    }
                }
            }
            write("Output Clusters  : ");
            for (int i : device.getOutputClusters()) {
                final Cluster cluster = device.getCluster(i);
                write("                 : " + i + ") " + ZigbeeApiConstants.getClusterName(i));
                if (cluster != null) {
                    for (final Subscription subscription : cluster.getActiveSubscriptions()) {
                        write("                 :  * Subscription: " + subscription.getReportListenersCount() + " listeners.");
                    }
                }
            }

            return true;
        }
    }

    private static class BindCommand implements ConsoleCommand {
        public String getDescription() {
            return "Binds a device to another device.";
        }
        public String getSyntax() {
            return "bind [CLIENT] SERVER CLUSTERID";
        }
        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {
            if (args.length != 3 && args.length != 4) {
                return false;
            }

            if (args.length == 3) {
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[2]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    server.bindToLocal(clusterId);
                } catch (final ZigbeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            } else {
                Device client = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[2]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[3]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    client.bindTo(server, clusterId);
                } catch (final ZigbeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }

    private static class UnbindCommand implements ConsoleCommand {
        public String getDescription() {
            return "Unbinds a device from another device.";
        }
        public String getSyntax() {
            return "bind CLIENT SERVER CLUSTERID";
        }
        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {
            if (args.length != 3 && args.length != 4) {
                return false;
            }

            if (args.length == 3) {
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[2]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    server.unbindFromLocal(clusterId);
                } catch (final ZigbeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            } else {
                Device client = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[2]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[3]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    client.unbindFrom(server, clusterId);
                } catch (final ZigbeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }

    private static class OnCommand implements ConsoleCommand {
        public String getDescription() {
            return "Switches device on.";
        }

        public String getSyntax() {
            return "on DEVICEID";
        }

        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final OnOff onOff = device.getCluster(OnOff.class);
            try {
                onOff.on();
            } catch (ZigbeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    private static class OffCommand implements ConsoleCommand {
        public String getDescription() {
            return "Switches device off.";
        }

        public String getSyntax() {
            return "off DEVICEID";
        }

        public boolean process(final ZigbeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final OnOff onOff = device.getCluster(OnOff.class);
            try {
                onOff.off();
            } catch (ZigbeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }
}
