package org.bubblecloud.zigbee.simple;

import org.apache.commons.lang.StringUtils;
import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.network.SerialPort;
import org.bubblecloud.zigbee.network.model.IEEEAddress;

import java.io.*;
import java.util.*;

/**
 * Simple ZigBee command line console is an example usage of simple ZigBee API.
 * This implementation is experimental.
 */
public final class SimpleZigBeeConsole {
    /**
     * The main thread.
     */
    private Thread mainThread = null;

    /**
     * The flag reflecting that shutdown is in process.
     */
    private boolean shutdown = false;

    /**
     * Map of registered commands and their implementations.
     */
    private Map<String, ConsoleCommand> commands = new TreeMap<String, ConsoleCommand>();

    /**
     * The ZigBee API.
     */
    private LocalZigBeeApi zigBeeApi;

    /**
     * Constructor which configures ZigBee API and constructs commands.
     *
     * @param serialPort the serial port
     * @param pan the pan
     * @param channel the channel
     * @param resetNetwork whether to reset network
     */
    public SimpleZigBeeConsole(final SerialPort serialPort, final int pan, int channel, final boolean resetNetwork) {

		commands.put("quit", 		new QuitCommand());
		commands.put("help", 		new HelpCommand());
		commands.put("list", 		new ListCommand());
		commands.put("desc", 		new DescribeCommand());
        commands.put("label", 		new LabelCommand());
		commands.put("bind", 		new BindCommand());
		commands.put("unbind", 		new UnbindCommand());
		commands.put("on", 			new OnCommand());
		commands.put("off", 		new OffCommand());
		commands.put("color",		new ColorCommand());
		commands.put("level", 		new LevelCommand());
		commands.put("listen", 	    new ListenCommand());
		commands.put("unlisten",    new UnlistenCommand());
		commands.put("subscribe", 	new SubscribeCommand());
		commands.put("unsubscribe", new UnsubscribeCommand());
		commands.put("read", new ReadCommand());
		commands.put("write", new WriteCommand());
		commands.put("join", new JoinCommand());
		commands.put("lqi", 		new LqiCommand());
        commands.put("warn",        new WarnCommand());
        commands.put("squawk", new SquawkCommand());
        commands.put("lock", new DoorLockCommand());
        commands.put("unlock", new DoorUnlockCommand());
        commands.put("enroll", new EnrollCommand());

        zigBeeApi = new LocalZigBeeApi(serialPort, pan, channel, resetNetwork);
    }

	/**
     * Starts this console application
     */
    public void start() {
        mainThread = Thread.currentThread();
        System.out.print("ZigBee API starting up...");

        if (!zigBeeApi.startup()) {
            print("ZigBee API starting up ... [FAIL]", System.out);
            return;
        } else {
            print("ZigBee API starting up ... [OK]", System.out);
        }

        zigBeeApi.getNetworkState().addNetworkListener(new ZigBeeNetworkStateListener() {
            @Override
            public void deviceAdded(ZigBeeDevice device) {
                print("Device added:\n" + getDeviceSummary(device), System.out);
            }

            @Override
            public void deviceUpdated(ZigBeeDevice device) {
                //print("Device updated:\n" + getDeviceSummary(device), System.out);
            }

            @Override
            public void deviceRemoved(ZigBeeDevice device) {
                print("Device removed\n" + getDeviceSummary(device), System.out);
            }
        });

        zigBeeApi.getNetwork().addCommandListener(new CommandListener() {
            @Override
            public void commandReceived(Command command) {
                //print("Received: " + command.toString(), System.out);
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
                }
            }
        }));

        print("ZigBee console ready.", System.out);

        String inputLine;
        while (!shutdown && (inputLine = readLine()) != null) {
            processInputLine(inputLine, System.out);
        }

        zigBeeApi.shutdown();
    }

    /**
     * Processes text input line.
     * @param inputLine the input line
     * @param out the output stream
     */
    public void processInputLine(final String inputLine, final PrintStream out) {
        if (inputLine.length() == 0) {
            return;
        }
        final String[] args = inputLine.split(" ");
        processArgs(args, out);
    }

    /**
     * Processes input arguments.
     * @param args the input arguments
     * @param out the output stream
     */
    public void processArgs(final String[] args, final PrintStream out) {
        try {
            if (commands.containsKey(args[0])) {
                executeCommand(zigBeeApi, args[0], args, out);
            } else {
                print("Uknown command. Use 'help' command to list available commands.", out);
            }
        } catch (final Exception e) {
            print("Exception in command execution: ", out);
            e.printStackTrace(out);
        }
    }

    public LocalZigBeeApi getZigBeeApi() {
        return zigBeeApi;
    }

    /**
     * Executes command.
     * @param zigbeeApi the ZigBee API
     * @param command the command
     * @param args the arguments including the command
     * @param out the output stream
     */
    private void executeCommand(final LocalZigBeeApi zigbeeApi, final String command, final String[] args, final PrintStream out) {
        final ConsoleCommand consoleCommand = commands.get(command);
        try {
            if (!consoleCommand.process(zigbeeApi, args, out)) {
                print(consoleCommand.getSyntax(), out);
            }
        } catch (Exception e) {
            out.println("Error executing on command: " + e.getMessage());
        }
    }

    /**
     * Prints line to console.
     *
     * @param line the line
     */
    private static void print(final String line, final PrintStream out) {
        out.println("\r" + line);
        if (out == System.out) {
            System.out.print("\r> ");
        }
    }

    /**
     * Reads line from console.
     *
     * @return line readLine from console or null if exception occurred.
     */
    private String readLine() {
        System.out.print("\r> ");
        try {
            final BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            return bufferRead.readLine();
        } catch(final IOException e) {
            return null;
        }
    }


    /**
     * Gets device summary.
     * @param device the device
     * @return the summary
     */
    private String getDeviceSummary(final ZigBeeDevice device) {
        return StringUtils.leftPad(Integer.toString(device.getNetworkAddress()), 10) + "/"
                + StringUtils.rightPad(Integer.toString(device.getEndpoint()), 3)
                + " " + StringUtils.rightPad(device.getLabel()!= null ? device.getLabel() : "<label>", 20)
                + " " + ZigBeeApiConstants.getDeviceName(device.getProfileId(),
                device.getDeviceType(), device.getDeviceId());
    }

    /**
     * Gets device by device identifier.
     * @param zigbeeApi the ZigBee API
     * @param deviceIdentifier the device identifier
     * @return the device
     */
    private ZigBeeDevice getDevice(LocalZigBeeApi zigbeeApi, final String deviceIdentifier) {
        for (final ZigBeeDevice zigBeeDevice : zigbeeApi.getNetworkState().getDevices()) {
            if (deviceIdentifier.equals(zigBeeDevice.getNetworkAddress() + "/" + zigBeeDevice.getEndpoint())) {
                return zigBeeDevice;
            }
            if (deviceIdentifier.equals(zigBeeDevice.getLabel())) {
                return zigBeeDevice;
            }
        }
        return null;
    }
    
    /**
     * Interface for console commands.
     */
    private interface ConsoleCommand {
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
         * Processes console command.
         * @param zigbeeApi the ZigBee API
         * @param args the command arguments
         * @param out the output PrintStream
         * @return true if command syntax was correct.
         */
        boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception;
    }

    /**
     * Quits console.
     */
    private class QuitCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            shutdown = true;
            return true;
        }
    }

    /**
     * Prints help on console.
     */
    private class HelpCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {

            if (args.length == 2) {
                if (commands.containsKey(args[1])) {
                    final ConsoleCommand command = commands.get(args[1]);
                    print(command.getDescription(), out);
                    print("", out);
                    print("Syntax: " + command.getSyntax(), out);
                } else {
                    return false;
                }
            } else if (args.length == 1) {
                final List<String> commandList = new ArrayList<String>(commands.keySet());
                Collections.sort(commandList);
                print("Commands:", out);
                for (final String command : commands.keySet()) {
                    print(command + " - " + commands.get(command).getDescription(), out);
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
    private class ListCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            final List<ZigBeeDevice> devices = zigbeeApi.getNetworkState().getDevices();
            for (int i = 0; i < devices.size(); i++) {
                final ZigBeeDevice device = devices.get(i);
                print(getDeviceSummary(device), out);
            }
            return true;
        }
    }

    /**
     * Prints device information to console.
     */
    private class DescribeCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);

            if (device == null) {
                return false;
            }

            print("IEEE Address     : " + IEEEAddress.toHex(device.getIeeeAddress()), out);
            print("Network Address  : " + device.getNetworkAddress(), out);
            print("Endpoint         : " + device.getEndpoint(), out);
            print("Device Profile   : " + ZigBeeApiConstants.getProfileName(device.getProfileId())+ String.format("  (0x%04X)", device.getProfileId()), out);
            print("Device Category  : " + ZigBeeApiConstants.getCategoryDeviceName(device.getProfileId(), device.getDeviceId()) + String.format("  (0x%04X)", device.getDeviceId()), out);
            print("Device Type      : " + ZigBeeApiConstants.getDeviceName(device.getProfileId(), device.getDeviceType(), device.getDeviceId()) + String.format("  (0x%04X)", device.getDeviceType()), out);
            print("Device Version   : " + device.getDeviceVersion(), out);
            print("Input Clusters   : ", out);
            printClusters(device, device.getInputClusterIds(), out);
            print("Output Clusters  : ", out);
            printClusters(device, device.getOutputClusterIds(), out);

            return true;
        }

        /**
         * Prints out clusters.
         * @param device the device
         * @param clusterIds the cluster IDs
         * @param out the output print stream
         */
        private void printClusters(final ZigBeeDevice device, final int[] clusterIds, PrintStream out) {
            for (int clusterId : clusterIds) {
                Cluster cluster = ZigBeeApiConstants.getCluster(device.getProfileId(), clusterId);
                print("                 : " + clusterId + " " + ZigBeeApiConstants.getClusterName(clusterId), out);
                if (cluster != null) {
                    for (int a = 0; a < cluster.getAttributes().length; a++) {
                        final Attribute attribute = cluster.getAttributes()[a];
                        print("                 :    " + attribute.getId()
                                + " "
                                + "r"
                                + (attribute.isWritable() ? "w" : "-")
                                + (attribute.isReportable() ? "s" : "-")
                                + " "
                                + attribute.getName()
                                + " "
                                + "  [" + attribute.getZigBeeType() + "]", out);
                    }
                }
            }
        }
    }

    /**
     * Binds client device to server device with given cluster ID.
     */
    private class BindCommand implements ConsoleCommand {
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
            return "bind [SOURCEDEVICE] [DESTINATIONDEVICE] [CLUSTERID]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 4) {
                return false;
            }
            final ZigBeeDevice source = getDevice(zigbeeApi, args[1]);
            if (source == null) {
                return false;
            }
            final ZigBeeDevice destination = getDevice(zigbeeApi, args[2]);
            if (destination == null) {
                return false;
            }
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final CommandResult response = zigBeeApi.bind(source, destination, clusterId).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Unbinds device from another device with given cluster ID.
     */
    private class UnbindCommand implements ConsoleCommand {
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
            return "unbind [SOURCEDEVICE] [DESTINATIONDEVICE] [CLUSTERID]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 4) {
                return false;
            }
            final ZigBeeDevice source = getDevice(zigbeeApi, args[1]);
            if (source == null) {
                return false;
            }
            final ZigBeeDevice destination = getDevice(zigbeeApi, args[2]);
            if (destination == null) {
                return false;
            }
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final CommandResult response = zigBeeApi.unbind(source, destination, clusterId).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Switches a device on.
     */
    private class OnCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            final CommandResult response = zigbeeApi.on(device).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }

        }
    }

    /**
     * Switches a device off.
     */
    private class OffCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            final CommandResult response = zigbeeApi.off(device).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Changes a light color on device.
     */
    private class ColorCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 5) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

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

            final CommandResult response = zigbeeApi.color(device, red, green, blue, 1).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Sets device label.
     */
    private class LabelCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Sets device label (user descriptor) to 0-16 US-ASCII character string.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "label DEVICEID DEVICELABEL";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            final String label = args[2];
            device.setLabel(label);
            zigbeeApi.getNetworkState().updateDevice(device);
            final CommandResult response = zigbeeApi.describe(device, label).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Changes a device level for example lamp brightness.
     */
    private class LevelCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Changes device level for example lamp brightness, where LEVEL is between 0 and 1.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "level DEVICEID LEVEL";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            float level;
            try {
                level = Float.parseFloat(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final CommandResult response = zigbeeApi.level(device, level, 1.0).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Locks door.
     */
    private class DoorLockCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Locks door.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "lock DEVICEID PINCODE";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            final String pinCode = args[2];

            final CommandResult response = zigbeeApi.lock(device, pinCode).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Locks door.
     */
    private class DoorUnlockCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Unlocks door.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "unlock DEVICEID PINCODE";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            final String pinCode = args[2];

            final CommandResult response = zigbeeApi.unlock(device, pinCode).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }
        }
    }

    /**
     * Starts listening to reports of given attribute.
     */
    private class ListenCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Listen to attribute reports.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "listen [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 4) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }


//            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();
//
//            if (reporter == null) {
//                print("Attribute does not provide reports.", out);
//                return true;
//            }
//
//            reporter.addReportListener(consoleReportListener, false);

            throw new UnsupportedOperationException();
        }
    }

    /**
     * Unlisten from reports of given attribute.
     */
    private class UnlistenCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Unlisten from attribute reports.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "unlisten [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 4) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

//            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();
//
//            if (reporter == null) {
//                print("Attribute does not provide reports.", out);
//            } else {
//                reporter.removeReportListener(consoleReportListener, false);
//            }
//
//            return true;
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Subscribes to reports of given attribute.
     */
    private class SubscribeCommand implements ConsoleCommand {
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
            return "subscribe [DEVICE] [CLUSTER] [ATTRIBUTE] [MIN-INTERVAL] [MAX-INTERVAL]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 6) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int minInterval;
            try {
                minInterval = Integer.parseInt(args[4]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int maxInterval;
            try {
                maxInterval = Integer.parseInt(args[5]);
            } catch (final NumberFormatException e) {
                return false;
            }

//            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();
//
//            if (reporter == null) {
//                print("Attribute does not provide reports.", out);
//                return true;
//            }
//
//            reporter.setMinimumReportingInterval(minInterval);
//            reporter.setMaximumReportingInterval(maxInterval);
//
//            reporter.addReportListener(consoleReportListener, true);
//
//            return true;
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Unsubscribes from reports of given attribute.
     */
    private class UnsubscribeCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 4) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            throw new UnsupportedOperationException();
            /*
            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();
            if (reporter == null) {
                print("Attribute does not provide reports.", out);
            } else {
                reporter.removeReportListener(consoleReportListener, true);
            }
            */

        }
    }

    /**
     * Reads an attribute from a device.
     */
    private class ReadCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 4) {
                return false;
            }

            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

//            final Cluster cluster = device.getCluster(clusterId);
//            if (cluster == null) {
//                print("Cluster not found.", out);
//                return false;
//            }
//
//            final Attribute attribute = cluster.getAttribute(attributeId);
//            if (attribute == null) {
//                print("Attribute not found.", out);
//                return false;
//            }
//
//            try {
//                print(attribute.getName() + "=" + attribute.getValue(), out);
//            } catch (ZigBeeClusterException e) {
//                print("Failed to read attribute.", out);
//                e.printStackTrace();
//            }
//
//            return true;
            throw new UnsupportedOperationException();

        }
    }

    /**
     * Writes an attribute to a device.
     */
    private class WriteCommand implements ConsoleCommand {
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
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 5) {
                return false;
            }

            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

//            final Cluster cluster = device.getCluster(clusterId);
//            if (cluster == null) {
//                print("Cluster not found.", out);
//                return false;
//            }
//
//            final Attribute attribute = cluster.getAttribute(attributeId);
//            if (attribute == null) {
//                print("Attribute not found.", out);
//                return false;
//            }
//
//            if(!attribute.isWritable()) {
//                print(attribute.getName() + " is not writable", out);
//            	return true;
//            }
//
//            try {
//            	Object val = null;
//                //TODO Handle other value types.
//            	switch(attribute.getZigBeeType()) {
//                    case Bitmap16bit:
//                        break;
//                    case Bitmap24bit:
//                        break;
//                    case Bitmap32bit:
//                        break;
//                    case Bitmap8bit:
//                        break;
//                    case Boolean:
//                        val = Boolean.parseBoolean(args[4]);
//                        break;
//                    case CharacterString:
//                        val = new String(args[4]);
//                        break;
//                    case Data16bit:
//                        break;
//                    case Data24bit:
//                        break;
//                    case Data32bit:
//                        break;
//                    case Data8bit:
//                        break;
//                    case DoublePrecision:
//                        break;
//                    case Enumeration16bit:
//                        break;
//                    case Enumeration8bit:
//                        break;
//                    case IEEEAddress:
//                        break;
//                    case LongCharacterString:
//                        break;
//                    case LongOctectString:
//                        break;
//                    case OctectString:
//                        break;
//                    case SemiPrecision:
//                        break;
//                    case SignedInteger16bit:
//                        break;
//                    case SignedInteger24bit:
//                        break;
//                    case SignedInteger32bit:
//                        break;
//                    case SignedInteger8bit:
//                        break;
//                    case SinglePrecision:
//                        break;
//                    case UnsignedInteger16bit:
//                        val = Integer.parseInt(args[4]);
//                        break;
//                    case UnsignedInteger24bit:
//                        break;
//                    case UnsignedInteger32bit:
//                        break;
//                    case UnsignedInteger8bit:
//                        break;
//                    default:
//                        break;
//            	}
//                attribute.setValue(val);
//                print("Attribute value written.", out);
//            } catch (ZigBeeClusterException e) {
//                print("Failed to write attribute.", out);
//                e.printStackTrace();
//            }
//
//            return true;
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Writes an attribute to a device.
     */
    private class LqiCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "List LQI neighbours list.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "lqi";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
//            ZigBeeDiscoveryManager discoveryMan = zigbeeApi.getZigBeeDiscoveryManager();
//            NetworkNeighbourLinks neighbors = discoveryMan.getLinkQualityInfo();
//            final List<ZigBeeNode> nodes = zigbeeApi.getNodes();
//            for (int i = 0; i < nodes.size(); i++) {
//            	final ZigBeeNode src = nodes.get(i);
//
//            	for (int j = 0; j < nodes.size(); j++) {
//                	final ZigBeeNode dst = nodes.get(j);
//                	int lqiLast = neighbors.getLast(src.getNetworkAddress(), dst.getNetworkAddress());
//                	if(lqiLast != -1) {
//                        print("Node #" + src.getNetworkAddress() + " receives node #" + dst.getNetworkAddress() +
//                				" with LQI " + lqiLast + " (" +
//                				neighbors.getMin(src.getNetworkAddress(), dst.getNetworkAddress()) + "/" +
//                				neighbors.getAvg(src.getNetworkAddress(), dst.getNetworkAddress()) + "/" +
//                				neighbors.getMax(src.getNetworkAddress(), dst.getNetworkAddress()) + ")", out
//                        );
//                	}
//                }
////            }
//            return true;

            throw new UnsupportedOperationException();
        }
    }

    private class WarnCommand implements ConsoleCommand {

        @Override
        public String getDescription() {
            return "Warn IAS warning device.";
        }

        @Override
        public String getSyntax() {
            return "warn [DEVICE] [MODE] [STROBE] [DURATION]";
        }

        @Override
        public boolean process(LocalZigBeeApi zigbeeApi, String[] args, PrintStream out) throws Exception {
            if (args.length != 5) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final int mode;
            try {
                mode = Integer.parseInt(args[2]);
                if (mode < 0 || mode > 15) {
                    print("Warning mode should be in range [0, 15].", out);
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            final int strobe;
            try {
                strobe = Integer.parseInt(args[3]);
                if (strobe < 0 || strobe > 3) {
                    print("Strobe should be in range [0, 3].", out);
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            final int duration;
            try {
                duration = Integer.parseInt(args[4]);
                if (duration < 0) {
                    print("Duration should be an unsigned 16-bit integer.", out);
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            final CommandResult response = zigbeeApi.warn(device, mode, strobe, duration).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }

        }

    }

    private class SquawkCommand implements ConsoleCommand {

        @Override
        public String getDescription() {
            return "Make the specfied IAS warning device squawk.";
        }

        @Override
        public String getSyntax() {
            return "squawk [DEVICE] [MODE] [STROBE] [LEVEL]";
        }

        @Override
        public boolean process(LocalZigBeeApi zigbeeApi, String[] args, PrintStream out) throws Exception {
            if (args.length != 5) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final int mode;
            try {
                mode = Integer.parseInt(args[2]);
                if (mode < 0 || mode > 15) {
                    print("Squawk mode should be in range [0, 15].", out);
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            final int strobe;
            try {
                strobe = Integer.parseInt(args[3]);
                if (strobe != 0 && strobe != 1) {
                    print("Strobe should be either 0 or 1.", out);
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            final int level;
            try {
                level = Integer.parseInt(args[4]);
                if (level < 0 || level > 3) {
                    print("Squawk level should be in range [0, 3].", out);
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            final CommandResult response = zigbeeApi.squawk(device, mode, strobe, level).get();
            if (response.isSuccess()) {
                out.println("Success response received.");
                return true;
            } else {
                out.println("Error executing command: " + response.getMessage());
                return true;
            }

        }

    }

    private class JoinCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Enable or diable network join.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "join [enable|disable]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 2) {
                return false;
            }
            
            final boolean join;
            if(args[1].toLowerCase().equals("enable")) {
            	join = true;
            } else if(args[1].toLowerCase().equals("disable")) {
                join = false;
            } else {
                return false;
            }

            zigbeeApi.permitJoin(join);
            if(args[1].toLowerCase().equals("enable")) {
                out.println("Permit join enable broadcast success.");
            } else {
                out.println("Permit join disable broadcast success.");
            }
            return true;
        }
    }

    /**
     * Enrolls IAS Zone device to this CIE device by setting own address as CIE address to the
     * IAS Zone device.
     */
    private class EnrollCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Enrolls IAS Zone device to this CIE device by setting own address as CIE address to the\n" +
                    " IAS Zone device.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "enroll DEVICEID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final LocalZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            int clusterId = org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASZone.ID;
            int attributeId = Attributes.IAS_CIE_ADDRESS.getId();

//            final Cluster cluster = device.getCluster(clusterId);
//            if (cluster == null) {
//                print("Cluster not found.", out);
//                return false;
//            }
//
//            final Attribute attribute = cluster.getAttribute(attributeId);
//            if (attribute == null) {
//                print("Attribute not found.", out);
//                return false;
//            }
//
//            if(!attribute.isWritable()) {
//                print(attribute.getName() + " is not writable", out);
//                return true;
//            }
//
//            try {
//                attribute.setValue(zigbeeApi.getZigBeeNetworkManager().getIeeeAddress());
//                print("CIE address set to: " + IEEEAddress.toColonNotation(zigbeeApi.getZigBeeNetworkManager().getIeeeAddress()), out);
//                print("CIE address verification read: " + IEEEAddress.toColonNotation((Long)attribute.getValue()), out);
//            }  catch (Exception e) {
//                print("Failed to set CIE address.", out);
//                e.printStackTrace();
//            }
//
//            return true;
            throw new UnsupportedOperationException();
        }
    }

}
