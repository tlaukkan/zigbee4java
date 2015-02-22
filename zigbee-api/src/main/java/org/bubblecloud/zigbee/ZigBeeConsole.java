package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.DeviceListener;
import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ReportListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.general.ColorControl;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.util.Cie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * ZigBee command line console is an example application demonstrating usage of ZigBee API.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigBeeConsole {
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
            print("Syntax: java -jar zigbee4java.jar SERIALPORT CHANNEL PAN RESET");
            return;
        }

        System.out.println("ZigBee API starting up ...");
        final EnumSet<DiscoveryMode> discoveryModes = DiscoveryMode.ALL;
        //discoveryModes.remove(DiscoveryMode.LinkQuality);
        final ZigBeeApi zigbeeApi = new ZigBeeApi(serialPortName, pan, channel, resetNetwork, discoveryModes);
        if (!zigbeeApi.startup()) {
            print("ZigBee API starting up ... [FAIL]");
            return;
        } else {
            print("ZigBee API starting up ... [OK]");
        }

        zigbeeApi.addDeviceListener(new DeviceListener() {
            @Override
            public void deviceAdded(Device device) {
                print("Device added: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")");
            }

            @Override
            public void deviceUpdated(Device device) {
            }

            @Override
            public void deviceRemoved(Device device) {
                print("Device removed: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")");
            }
        });

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

        print("Browsing network ...");
        while (!shutdown && !zigbeeApi.isInitialBrowsingComplete()) {
            System.out.print('.');
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
        }
        print("Browsing network ... [OK]");
        print("Found " + zigbeeApi.getDevices().size() + " nodes.");

        print("ZigBee console ready.");

        String inputLine;
        while (!shutdown && (inputLine = readLine()) != null) {
            processInputLine(zigbeeApi, inputLine);
        }

        zigbeeApi.shutdown();
    }

    /**
     * Processes text input line.
     * @param zigbeeApi the ZigBee API
     * @param inputLine the input line
     */
    private static void processInputLine(final ZigBeeApi zigbeeApi, final String inputLine) {
        if (inputLine.length() == 0) {
            return;
        }
        final String[] args = inputLine.split(" ");
        try {
            if (commands.containsKey(args[0])) {
                executeCommand(zigbeeApi, args[0], args);
                return;
            } else {
                for (final String command : commands.keySet()) {
                    if (command.charAt(0) == inputLine.charAt(0)) {
                        executeCommand(zigbeeApi, command, args);
                        return;
                    }
                }
                print("Uknown command. Use 'help' command to list available commands.");
            }
        } catch (final Exception e) {
            print("Exception in command execution: ");
            e.printStackTrace();
        }
    }

    /**
     * Executes command.
     * @param zigbeeApi the ZigBee API
     * @param command the command
     * @param args the arguments including the command
     */
    private static void executeCommand(final ZigBeeApi zigbeeApi, final String command, final String[] args) {
        final ConsoleCommand consoleCommand = commands.get(command);
        if (!consoleCommand.process(zigbeeApi, args)) {
            print(consoleCommand.getSyntax());
        }
    }

    /**
     * Prints line to console.
     *
     * @param line the line
     */
    private static void print(final String line) {
        System.out.println("\r" + line);
        System.out.print("> ");
    }

    /**
     * Reads line from console.
     *
     * @return line readLine from console or null if exception occurred.
     */
    private static String readLine() {
        System.out.print("\r> ");
        try {
            final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            final String inputLine = bufferRead.readLine();
            return inputLine;
        } catch(final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets device from ZigBee API either with index or endpoint ID
     * @param zigbeeApi the zigbee API
     * @param deviceIdentifier the device identifier
     * @return
     */
    private static Device getDeviceByIndexOrEndpointId(ZigBeeApi zigbeeApi, String deviceIdentifier) {
        Device device;
        try {
            device = zigbeeApi.getDevices().get(Integer.parseInt(deviceIdentifier));
        } catch (final Exception e) {
            device = zigbeeApi.getDevice(deviceIdentifier);
        }
        return device;
    }

    /*
     * Static initialization of console commands.
     */
    static {
        commands.put("quit", new QuitCommand());
        commands.put("help", new HelpCommand());
        commands.put("list", new ListCommand());
        commands.put("desc", new DescribeCommand());
        commands.put("bind", new BindCommand());
        commands.put("unbind", new UnbindCommand());
        commands.put("on", new OnCommand());
        commands.put("off", new OffCommand());
        commands.put("color", new ColorCommand());
        commands.put("level", new LevelCommand());
        commands.put("subscribe", new SubscribeCommand());
        commands.put("unsubscribe", new UnsubscribeCommand());
        commands.put("read", new ReadCommand());
        commands.put("write", new WriteCommand());
    }

    /**
     * Interface for console commands.
     */
    private static interface ConsoleCommand {
        /**
         * Get command description.
         * @return the command description
         */
        String getDescription();

        /**
         * Get command syntax.
         * @return the command syntax
         */
        String getSyntax();

        /**
         *
         * @param zigbeeApi
         * @param args
         * @return
         */
        boolean process(final ZigBeeApi zigbeeApi, final String[] args);
    }

    /**
     * Quits console.
     */
    private static class QuitCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Quits console.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "quit";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            shutdown = true;
            return true;
        }
    }

    /**
     * Prints help on console.
     */
    private static class HelpCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "View command help.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "help [command]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {

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
                print("Commands:");
                for (final String command : commands.keySet()) {
                    print(command + " - " + commands.get(command).getDescription());
                }
            } else {
                return false;
            }

            return true;
        }
    }

    /**
     * Prints list of devices to console.
     */
    private static class ListCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Lists devices.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "list";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            final List<Device> devices = zigbeeApi.getDevices();
            for (int i = 0; i < devices.size(); i++) {
                final Device device = devices.get(i);
                System.out.println(i + ") " + device.getEndpointId() +
                		" [" + device.getNetworkAddress() + "]" +
                		" : " + device.getDeviceType());
            }
            return true;
        }
    }

    /**
     * Prints device information to console.
     */
    private static class DescribeCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Describes a device.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "desc DEVICEID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);

            if (device == null) {
                return false;
            }

            print("Network Address  : " + device.getNetworkAddress());
            print("Extended Address : " + device.getIEEEAddress());
            print("Endpoint Address : " + device.getEndPointAddress());
            print("Device Type      : " + device.getDeviceType());
            print("Device Category  : " + ZigBeeApiConstants.getCategoryDeviceName(device.getDeviceTypeId()));
            print("Device Version   : " + device.getDeviceVersion());
            print("Input Clusters   : ");
            for (int c : device.getInputClusters()) {
                final Cluster cluster = device.getCluster(c);
                print("                 : " + c + " " + ZigBeeApiConstants.getClusterName(c));
                if (cluster != null) {
                    for (int a = 0; a < cluster.getAttributes().length; a++) {
                        final Attribute attribute = cluster.getAttributes()[a];
                        print("                 :    " + a
                                + " "
                                + "r"
                                + (attribute.isWritable() ? "w" : "-")
                                + (attribute.isReportable() ? "s" : "-")
                                + " "
                                + attribute.getName()
                                + " "
                                + (attribute.getReporter() != null ? "(" +
                                Integer.toString(attribute.getReporter().getReportListenersCount()) + ")" : "")
                                + "  [" + attribute.getZigBeeType() + "]");
                    }
                }
            }
            print("Output Clusters  : ");
            for (int c : device.getOutputClusters()) {
                final Cluster cluster = device.getCluster(c);
                print("                 : " + c + " " + ZigBeeApiConstants.getClusterName(c));
            }

            return true;
        }
    }

    /**
     * Binds client device to server device with given cluster ID.
     */
    private static class BindCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Binds a device to another device.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "bind [CLIENT] SERVER CLUSTERID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
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
                } catch (final ZigBeeNetworkManagerException e) {
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
                } catch (final ZigBeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }

    /**
     * Unbinds device from another device with given cluster ID.
     */
    private static class UnbindCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Unbinds a device from another device.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "unbind CLIENT SERVER CLUSTERID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
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
                } catch (final ZigBeeNetworkManagerException e) {
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
                } catch (final ZigBeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }

    /**
     * Switches a device on.
     */
    private static class OnCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Switches device on.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "on DEVICEID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
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
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    /**
     * Changes a light color on device.
     */
    private static class ColorCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Changes light color.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "color DEVICEID RED GREEN BLUE";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 5) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final ColorControl colorControl = device.getCluster(ColorControl.class);
            if (colorControl == null) {
                print("Device does not support color control.");
                return false;
            }
            // @param colorX x * 65536 where colorX can be in rance 0 to 65279
            // @param colorY y * 65536 where colorY can be in rance 0 to 65279

            float red;
            try {
                red = Float.parseFloat(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            float green;
            try {
                green = Float.parseFloat(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }
            float blue;
            try {
                blue = Float.parseFloat(args[4]);
            } catch (final NumberFormatException e) {
                return false;
            }

            try {
                /*
                // RED
                int x = (int) (0.648427f * 65536);
                int y = (int) (0.330856f * 65536);
                colorControl.moveToColor(x, y, 10);*/
                Cie cie = Cie.rgb2cie(red, green ,blue);
                int x = (int) (cie.x * 65536);
                int y = (int) (cie.y * 65536);
                if (x > 65279) {
                    x = 65279;
                }
                if (y > 65279) {
                    y = 65279;
                }
                colorControl.moveToColor(x, y, 10);
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    /**
     * Changes a device level for example lamp brightness.
     */
    private static class LevelCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Changes device level for example lamp brightness.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "color DEVICEID LEVEL";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 3) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final ColorControl colorControl = device.getCluster(ColorControl.class);
            if (colorControl == null) {
                print("Device does not support color control.");
                return false;
            }

            float level;
            try {
                level = Float.parseFloat(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            try {
                int l = (int) (level * 254);
                if (l > 254) {
                    l = 254;
                }
                if (l < 0) {
                    l = 0;
                }

                final LevelControl levelControl = device.getCluster(LevelControl.class);
                levelControl.moveToLevel((short) l, 10);
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    /**
     * Switches a device off.
     */
    private static class OffCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Switches device off.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "off DEVICEID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
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
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    /**
     * Subscribes to reports of given attribute.
     */
    private static class SubscribeCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Subscribe to attribute reports.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "subscribe [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 4) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }


            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeIndex).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.");
                return true;
            }

            reporter.addReportListener(consoleReportListener);

            return true;
        }
    }

    /**
     * Unsubscribes from reports of given attribute.
     */
    private static class UnsubscribeCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Unsubscribe from attribute reports.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "unsubscribe [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 4) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeIndex).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.");
            }

            reporter.removeReportListener(consoleReportListener);

            return true;
        }
    }

    /**
     * Reads an attribute from a device.
     */
    private static class ReadCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Read an attribute.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "read [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 4) {
                return false;
            }

            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.");
                return false;
            }

            final Cluster cluster = device.getCluster(clusterId);
            if (cluster == null) {
                print("Cluster not found.");
                return false;
            }

            final Attribute attribute = cluster.getAttributes()[attributeIndex];
            if (attribute == null) {
                print("Attribute not found.");
                return false;
            }

            try {
                print(attribute.getName() + "=" + attribute.getValue());
            } catch (ZigBeeClusterException e) {
                print("Failed to read attribute.");
                e.printStackTrace();
            }

            return true;
        }
    }

    /**
     * Writes an attribute to a device.
     */
    private static class WriteCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Write an attribute.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "write [DEVICE] [CLUSTER] [ATTRIBUTE] [VALUE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 5) {
                return false;
            }

            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.");
                return false;
            }

            final Cluster cluster = device.getCluster(clusterId);
            if (cluster == null) {
                print("Cluster not found.");
                return false;
            }

            final Attribute attribute = cluster.getAttributes()[attributeIndex];
            if (attribute == null) {
                print("Attribute not found.");
                return false;
            }
            
            if(attribute.isWritable() == false) {
                print(attribute.getName() + " is not writable");
            	return true;
            }

            try {
            	Object val = null;
                //TODO Handle other value types.
            	switch(attribute.getZigBeeType()) {
                    case Bitmap16bit:
                        break;
                    case Bitmap24bit:
                        break;
                    case Bitmap32bit:
                        break;
                    case Bitmap8bit:
                        break;
                    case Boolean:
                        break;
                    case CharacterString:
                        val = new String(args[4]);
                        break;
                    case Data16bit:
                        break;
                    case Data24bit:
                        break;
                    case Data32bit:
                        break;
                    case Data8bit:
                        break;
                    case DoublePrecision:
                        break;
                    case Enumeration16bit:
                        break;
                    case Enumeration8bit:
                        break;
                    case IEEEAddress:
                        break;
                    case LongCharacterString:
                        break;
                    case LongOctectString:
                        break;
                    case OctectString:
                        break;
                    case SemiPrecision:
                        break;
                    case SignedInteger16bit:
                        break;
                    case SignedInteger24bit:
                        break;
                    case SignedInteger32bit:
                        break;
                    case SignedInteger8bit:
                        break;
                    case SinglePrecision:
                        break;
                    case UnsignedInteger16bit:
                        break;
                    case UnsignedInteger24bit:
                        break;
                    case UnsignedInteger32bit:
                        break;
                    case UnsignedInteger8bit:
                        break;
                    default:
                        break;
            	}
                attribute.setValue(val);
            } catch (ZigBeeClusterException e) {
                print("Failed to write attribute.");
                e.printStackTrace();
            }

            return true;
        }
    }

    /**
     * Anonymous class report listener implementation which prints the reports to console.
     */
    private static ReportListener consoleReportListener = new ReportListener() {
        @Override
        public void receivedReport(final String endPointId, final short clusterId,
                                   final Dictionary<Attribute, Object> reports) {
            final Enumeration<Attribute> attributes = reports.keys();
            while (attributes.hasMoreElements()) {
                final Attribute attribute = attributes.nextElement();
                final Object value = reports.get(attribute);
                print(endPointId + "->" + clusterId + "->" + attribute.getName() + "=" + value);
            }
        }
    };

}
