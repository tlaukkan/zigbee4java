package org.bubblecloud.zigbee.v3;

import org.apache.commons.lang.StringUtils;
import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.util.IEEEAddress;
import org.bubblecloud.zigbee.v3.model.Status;
import org.bubblecloud.zigbee.v3.model.ZigBeeType;
import org.bubblecloud.zigbee.v3.model.ZToolAddress64;
import org.bubblecloud.zigbee.v3.zcl.field.Unsigned16BitInteger;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.ConfigureReportingResponseCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.ReadAttributesResponseCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.ReportAttributesCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.general.WriteAttributesResponseCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.groups.GetGroupMembershipResponseCommand;
import org.bubblecloud.zigbee.v3.zcl.protocol.command.groups.ViewGroupResponseCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;

/**
 * ZigBee command line console is an example usage of simple ZigBee API.
 * This implementation is experimental.
 */
public final class ZigBeeGateway {
    /**
     * The main thread.
     */
    private Thread mainThread = null;

    /**
     * The flag reflecting that shutdown is in process.
     */
    private boolean shutdown = false;

    /**
     * Whether to print attribute reports.
     */
    private boolean printAttributeReports = false;

    /**
     * Map of registered commands and their implementations.
     */
    private Map<String, ConsoleCommand> commands = new TreeMap<String, ConsoleCommand>();

    /**
     * The ZigBee API.
     */
    private ZigBeeApiDongleImpl zigBeeApi;

    /**
     * Constructor which configures ZigBee API and constructs commands.
     *
     * @param dongle the dongle
     * @param resetNetwork whether network is to be reset
     */
    public ZigBeeGateway(final ZigBeeDongle dongle, final boolean resetNetwork) {

        commands.put("devicelist", new DeviceListCommand());
        commands.put("devicelabel", new DeviceLabelCommand());
        commands.put("deviceremove", new DeviceRemoveCommand());

        commands.put("groupadd", new GroupAddCommand());
        commands.put("groupremove", new GroupRemoveCommand());
        commands.put("grouplist", new GroupListCommand());

        commands.put("membershipadd", new MembershipAddCommand());
        commands.put("membershipremove", new MembershipRemoveCommand());
        commands.put("membershipview", new MembershipViewCommand());
        commands.put("membershiplist", new MembershipListCommand());

        commands.put("quit", new QuitCommand());
		commands.put("help", new HelpCommand());
		commands.put("desc", new DescribeCommand());
        commands.put("descriptor", new SetDescriptorCommand());
		commands.put("bind", new BindCommand());
		commands.put("unbind", new UnbindCommand());
		commands.put("on", new OnCommand());
		commands.put("off", new OffCommand());
		commands.put("color", new ColorCommand());
		commands.put("level", new LevelCommand());
		commands.put("listen", new ListenCommand());
		commands.put("unlisten", new UnlistenCommand());
		commands.put("subscribe", new SubscribeCommand());
		commands.put("unsubscribe", new UnsubscribeCommand());
		commands.put("read", new ReadCommand());
		commands.put("write", new WriteCommand());
		commands.put("join", new JoinCommand());
		commands.put("lqi", new LqiCommand());
        commands.put("warn", new WarnCommand());
        commands.put("squawk", new SquawkCommand());
        commands.put("lock", new DoorLockCommand());
        commands.put("unlock", new DoorUnlockCommand());
        commands.put("enroll", new EnrollCommand());

        zigBeeApi = new ZigBeeApiDongleImpl(dongle, resetNetwork);
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
                if (printAttributeReports && command instanceof ReportAttributesCommand) {
                    print("Received: " + command.toString(), System.out);
                }
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
                    e.printStackTrace();
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

    public ZigBeeApiDongleImpl getZigBeeApi() {
        return zigBeeApi;
    }

    /**
     * Executes command.
     * @param zigbeeApi the ZigBee API
     * @param command the command
     * @param args the arguments including the command
     * @param out the output stream
     */
    private void executeCommand(final ZigBeeApiDongleImpl zigbeeApi, final String command, final String[] args, final PrintStream out) {
        final ConsoleCommand consoleCommand = commands.get(command);
        try {
            if (!consoleCommand.process(zigbeeApi, args, out)) {
                print(consoleCommand.getSyntax(), out);
            }
        } catch (Exception e) {
            out.println("Error executing command: " + e);
            e.printStackTrace(out);
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
     * Gets destination by device identifier or group ID.
     * @param zigbeeApi the ZigBee API
     * @param destinationIdentifier the device identifier or group ID
     * @return the device
     */
    private ZigBeeDestination getDestination(final ZigBeeApiDongleImpl zigbeeApi, final String destinationIdentifier
            ,final PrintStream out) {
        final ZigBeeDevice device = getDevice(zigbeeApi, destinationIdentifier);

        if (device != null) {
            return device;
        }

        try {
            for (final ZigBeeGroup group : zigbeeApi.getGroups()) {
                if (destinationIdentifier.equals(group.getLabel())) {
                    return group;
                }
            }
            final int groupId = Integer.parseInt(destinationIdentifier);
            ZigBeeGroup group = zigbeeApi.getGroup(groupId);
            if (group == null) {
                zigBeeApi.addMembership(groupId, Integer.toString(groupId));
            }
            group = zigbeeApi.getGroup(groupId);
            return group;
        } catch (final NumberFormatException e) {
            return null;
        }
    }

    /**
     * Gets device by device identifier.
     * @param zigbeeApi the ZigBee API
     * @param deviceIdentifier the device identifier
     * @return the device
     */
    private ZigBeeDevice getDevice(ZigBeeApiDongleImpl zigbeeApi, final String deviceIdentifier) {
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
        boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception;
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {

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
    private class DeviceListCommand implements ConsoleCommand {
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
            return "devicelist";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
            final List<ZigBeeDevice> devices = zigbeeApi.getDevices();
            for (final ZigBeeDevice device : devices) {
                print(getDeviceSummary(device), out);
            }
            return true;
        }
    }

    /**
     * Lists groups in gateway network state.
     */
    private class GroupListCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Lists groups in gateway network state.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "grouplist";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
            final List<ZigBeeGroup> groups = zigbeeApi.getGroups();
            for (final ZigBeeGroup group : groups) {
                print(StringUtils.leftPad(Integer.toString(group.getGroupId()), 10) + "      " + group.getLabel(), out);
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
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
            return defaultResponseProcessing(response, out);
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
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
            return defaultResponseProcessing(response, out);
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
            return "on DEVICEID/DEVICELABEL/GROUPID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);

            if (destination == null) {
                return false;
            }

            final CommandResult response = zigbeeApi.on(destination).get();
            return defaultResponseProcessing(response, out);

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
            return "off DEVICEID/DEVICELABEL/GROUPID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);

            if (destination == null) {
                return false;
            }

            final CommandResult response = zigbeeApi.off(destination).get();
            return defaultResponseProcessing(response, out);
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 5) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);
            if (destination == null) {
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

            final CommandResult response = zigbeeApi.color(destination, red, green, blue, 1).get();
            return defaultResponseProcessing(response, out);
        }
    }

    /**
     * Sets device label.
     */
    private class DeviceLabelCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Sets device label.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "devicelabel DEVICEID DEVICELABEL";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            final String label = args[2];
            device.setLabel(label);
            zigbeeApi.setDeviceLabel(device.getNetworkAddress(), device.getEndpoint(), label);
            return true;
        }
    }

    /**
     * Adds group to gateway network state. Does not affect actual ZigBee network.
     */
    private class GroupAddCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Adds group to gateway network state.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "groupadd GROUPID GROUPLABEL";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final int groupId;
            try {
                groupId = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final String label = args[2];
            zigbeeApi.addMembership(groupId, label);

            return true;
        }
    }

    /**
     * Removes device from network state but does not affect actual ZigBeet network.
     * Device will be eventually rediscovered if it is still in the network.
     */
    private class DeviceRemoveCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Removes device from gateway network state.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "deviceremove DEVICE";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            zigbeeApi.removeDevice(device.getNetworkAddress());
            return true;
        }
    }

    /**
     * Removes group from network state but does not affect actual ZigBeet network.
     */
    private class GroupRemoveCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Removes group from gateway network state.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "groupremove GROUP";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);
            if (destination == null) {
                return false;
            }
            if (!(destination instanceof ZigBeeGroup)) {
                return false;
            }

            final ZigBeeGroup group = (ZigBeeGroup) destination;
            zigbeeApi.removeMembership(group.getGroupId());
            return true;
        }
    }

    /**
     * Sets device user descriptor.
     */
    private class SetDescriptorCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Sets device user descriptor to 0-16 US-ASCII character string.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "descriptor DEVICEID DEVICELABEL";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            final String label = args[2];

            final CommandResult response = zigbeeApi.describe(device, label).get();
            return defaultResponseProcessing(response, out);
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);
            if (destination == null) {
                return false;
            }

            float level;
            try {
                level = Float.parseFloat(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final CommandResult response = zigbeeApi.level(destination, level, 1.0).get();
            return defaultResponseProcessing(response, out);
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);
            if (destination == null) {
                return false;
            }

            final String pinCode = args[2];

            final CommandResult response = zigbeeApi.lock(destination, pinCode).get();
            return defaultResponseProcessing(response, out);
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);
            if (destination == null) {
                return false;
            }

            final String pinCode = args[2];

            final CommandResult response = zigbeeApi.unlock(destination, pinCode).get();
            return defaultResponseProcessing(response, out);
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
            return "listen";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 1) {
                return false;
            }

            printAttributeReports = true;

            out.println("Printing received attribute reports.");

            return true;
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
            return "unlisten";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 1) {
                return false;
            }

            printAttributeReports = false;

            out.println("Ignoring received attribute reports.");

            return true;
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
            return "subscribe [DEVICE] [CLUSTER] [ATTRIBUTE] [MIN-INTERVAL] [MAX-INTERVAL] [REPORTABLE-CHANGE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 6 && args.length != 7) {
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

            final Cluster cluster = ZigBeeApiConstants.getCluster(ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION, clusterId);
            final Attribute attribute = cluster.getAttribute(attributeId);
            Object reportableChange = null;
            if (args.length > 6) {
                reportableChange = parseValue(args[6], attribute.getZigBeeType());
            }

            final CommandResult result = zigbeeApi.report(device, clusterId, attributeId, minInterval, maxInterval, reportableChange).get();
            if (result.isSuccess()) {
                final ConfigureReportingResponseCommand response = result.getResponse();
                final int statusCode = response.getRecords().get(0).getStatus();
                if (statusCode == 0) {
                    out.println("Attribute value configure reporting success.");
                } else {
                    final Status status = Status.getStatus((byte) statusCode);
                    out.println("Attribute value configure reporting error: " + status);
                }
                return true;
            } else {
                out.println("Error executing command: " + result.getMessage());
                return true;
            }

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
            return "unsubscribe [DEVICE] [CLUSTER] [ATTRIBUTE] [REPORTABLE-CHANGE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
            if (args.length != 4 && args.length != 5) {
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

            final Cluster cluster = ZigBeeApiConstants.getCluster(ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION, clusterId);
            final Attribute attribute = cluster.getAttribute(attributeId);
            Object reportableChange = null;
            if (args.length > 4) {
                reportableChange = parseValue(args[4], attribute.getZigBeeType());
            }

            final CommandResult result = zigbeeApi.report(device, clusterId, attributeId, 0, 0xffff, reportableChange).get();
            if (result.isSuccess()) {
                final ConfigureReportingResponseCommand response = result.getResponse();
                final int statusCode = response.getRecords().get(0).getStatus();
                if (statusCode == 0) {
                    out.println("Attribute value configure reporting success.");
                } else {
                    final Status status = Status.getStatus((byte) statusCode);
                    out.println("Attribute value configure reporting error: " + status);
                }
                return true;
            } else {
                out.println("Error executing command: " + result.getMessage());
                return true;
            }


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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
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

            final CommandResult result = zigbeeApi.read(device, clusterId, attributeId).get();

            if (result.isSuccess()) {
                final ReadAttributesResponseCommand response = result.getResponse();

                final int statusCode = response.getRecords().get(0).getStatus();
                if (statusCode == 0) {
                    out.println("Attribute value: " + response.getRecords().get(0).getAttributeValue());
                } else {
                    final Status status = Status.getStatus((byte) statusCode);
                    out.println("Attribute value read error: " + status);
                }

                return true;
            } else {
                out.println("Error executing command: " + result.getMessage());
                return true;
            }

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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
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


            final Cluster cluster = ZigBeeApiConstants.getCluster(ZigBeeApiConstants.PROFILE_ID_HOME_AUTOMATION, clusterId);
            final Attribute attribute = cluster.getAttribute(attributeId);

            final Object value = parseValue(args[4], attribute.getZigBeeType());

            final CommandResult result = zigbeeApi.write(device, clusterId, attributeId, value).get();
            if (result.isSuccess()) {
                final WriteAttributesResponseCommand response = result.getResponse();
                final int statusCode = response.getRecords().get(0).getStatus();
                if (statusCode == 0) {
                    out.println("Attribute value write success.");
                } else {
                    final Status status = Status.getStatus((byte) statusCode);
                    out.println("Attribute value write error: " + status);
                }
                return true;
            } else {
                out.println("Error executing command: " + result.getMessage());
                return true;
            }
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
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
        public boolean process(ZigBeeApiDongleImpl zigbeeApi, String[] args, PrintStream out) throws Exception {
            if (args.length != 5) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);
            if (destination == null) {
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

            final CommandResult response = zigbeeApi.warn(destination, mode, strobe, duration).get();
            return defaultResponseProcessing(response, out);

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
        public boolean process(ZigBeeApiDongleImpl zigbeeApi, String[] args, PrintStream out) throws Exception {
            if (args.length != 5) {
                return false;
            }

            final ZigBeeDestination destination = getDestination(zigbeeApi, args[1], out);
            if (destination == null) {
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

            final CommandResult response = zigbeeApi.squawk(destination, mode, strobe, level).get();
            return defaultResponseProcessing(response, out);

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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) throws Exception {
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
            return "Enrolls IAS Zone device to this CIE device by setting own address as CIE address to the " +
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
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

//            int clusterId = org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASZone.ID;
//            int attributeId = Attributes.IAS_CIE_ADDRESS.getId();
//
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

    /**
     * Adds group membership to device.
     */
    private class MembershipAddCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Adds group membership to device.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "membershipadd [DEVICE] [GROUPID] [GROUPNAME]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out)
                throws Exception {
            if (args.length != 4) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final int groupId;
            try {
                groupId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final String groupName = args[3];

            final CommandResult result = zigbeeApi.addMembership(device, groupId, groupName).get();

            return defaultResponseProcessing(result, out);

        }
    }

    /**
     * Removes device group membership from device.
     */
    private class MembershipRemoveCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Removes group membership from device.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "membershipremove [DEVICE] [GROUPID]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out)
                throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final int groupId;
            try {
                groupId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final CommandResult result = zigbeeApi.removeMembership(device, groupId).get();

            return defaultResponseProcessing(result, out);
        }
    }

    /**
     * Views group name from device group membership.
     */
    private class MembershipViewCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Views group name from device group membership.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "membershipview [DEVICE] [GROUPID]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out)
                throws Exception {
            if (args.length != 3) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final int groupId;
            try {
                groupId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final CommandResult result = zigbeeApi.viewMembership(device, groupId).get();

            if (result.isSuccess()) {
                final ViewGroupResponseCommand response = result.getResponse();
                final int statusCode = response.getStatus();
                if (statusCode == 0) {
                    out.println("Group name: " + response.getGroupName());
                } else {
                    final Status status = Status.getStatus((byte) statusCode);
                    out.println("Error reading group name: " + status);
                }
                return true;
            } else {
                out.println("Error executing command: " + result.getMessage());
                return true;
            }
        }
    }

    /**
     * Lists group memberships from device.
     */
    private class MembershipListCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Lists group memberships from device.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "membershiplist [DEVICE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApiDongleImpl zigbeeApi, final String[] args, PrintStream out)
                throws Exception {
            if (args.length != 2) {
                return false;
            }

            final ZigBeeDevice device = getDevice(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final CommandResult result = zigbeeApi.getGroupMemberships(device).get();

            if (result.isSuccess()) {
                final GetGroupMembershipResponseCommand response = result.getResponse();
                out.print("Member of groups:");
                for (final Unsigned16BitInteger value :  response.getGroupList()) {
                    out.print(' ');
                    out.print(value.getValue());
                }
                out.println();
                return true;
            } else {
                out.println("Error executing command: " + result.getMessage());
                return true;
            }
        }
    }

    /**
     * Default processing for command result.
     * @param result the command result
     * @param out the output
     * @return TRUE if result is success
     */
    private boolean defaultResponseProcessing(CommandResult result, PrintStream out) {
        if (result.isSuccess()) {
            out.println("Success response received.");
            return true;
        } else {
            out.println("Error executing command: " + result.getMessage());
            return true;
        }
    }

    /**
     * Parses string value to Object.
     * @param stringValue the string value
     * @param zigBeeType the ZigBee type
     * @return the value Object
     */
    private Object parseValue(String stringValue, ZigBeeType zigBeeType) {
        Object value = null;
        switch (zigBeeType) {
            case Bitmap16bit:
                value = Integer.parseInt(stringValue);
                break;
            case Bitmap24bit:
                value = Integer.parseInt(stringValue);
                break;
            case Bitmap32bit:
                value = Integer.parseInt(stringValue);
                break;
            case Bitmap8bit:
                value = Integer.parseInt(stringValue);
                break;
            case Boolean:
                value = Boolean.parseBoolean(stringValue);
                break;
            case CharacterString:
                value = stringValue;
                break;
            case Data16bit:
                value = Integer.parseInt(stringValue);
                break;
            case Data24bit:
                value = Integer.parseInt(stringValue);
                break;
            case Data32bit:
                value = Integer.parseInt(stringValue);
                break;
            case Data8bit:
                value = Integer.parseInt(stringValue);
                break;
            case DoublePrecision:
                value = Double.parseDouble(stringValue);
                break;
            case Enumeration16bit:
                value = Integer.parseInt(stringValue);
                break;
            case Enumeration8bit:
                value = Integer.parseInt(stringValue);
                break;
            case IEEEAddress:
                value = new ZToolAddress64(Long.parseLong(stringValue));
                break;
            case LongCharacterString:
                value = stringValue;
                break;
            case LongOctectString:
                value = stringValue;
                break;
            case OctectString:
                value = stringValue;
                break;
            case SemiPrecision:
                throw new UnsupportedOperationException("SemiPrecision parsing not implemented");
            case SignedInteger16bit:
                value = Integer.parseInt(stringValue);
                break;
            case SignedInteger24bit:
                value = Integer.parseInt(stringValue);
                break;
            case SignedInteger32bit:
                value = Integer.parseInt(stringValue);
                break;
            case SignedInteger8bit:
                value = Integer.parseInt(stringValue);
                break;
            case SinglePrecision:
                throw new UnsupportedOperationException("SinglePrecision parsing not implemented");
            case UnsignedInteger16bit:
                value = Integer.parseInt(stringValue);
                break;
            case UnsignedInteger24bit:
                value = Integer.parseInt(stringValue);
                break;
            case UnsignedInteger32bit:
                value = Integer.parseInt(stringValue);
                break;
            case UnsignedInteger8bit:
                value = Integer.parseInt(stringValue);
                break;
            default:
                break;
        }
        return value;
    }
}
