package org.bubblecloud.zigbee;

import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.DeviceListener;
import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.ColorControl;
import org.bubblecloud.zigbee.api.cluster.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.general.DoorLock;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ReportListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_wd.SquawkPayload;
import org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.ias_wd.StartWarningPayload;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_wd.SquawkPayloadImpl;
import org.bubblecloud.zigbee.api.cluster.impl.security_safety.ias_wd.StartWarningPayloadImpl;
import org.bubblecloud.zigbee.api.cluster.security_safety.IASWD;
import org.bubblecloud.zigbee.network.NodeListener;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.bubblecloud.zigbee.network.impl.ZigBeeException;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodeDescriptor;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodePowerDescriptor;
import org.bubblecloud.zigbee.network.discovery.LinkQualityIndicatorNetworkBrowser.NetworkNeighbourLinks;
import org.bubblecloud.zigbee.network.discovery.ZigBeeDiscoveryManager;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.model.IEEEAddress;
import org.bubblecloud.zigbee.network.SerialPort;
import org.bubblecloud.zigbee.network.zcl.ZclCommand;
import org.bubblecloud.zigbee.network.zdo.command.ActiveEndpointsRequest;
import org.bubblecloud.zigbee.network.zdo.command.ActiveEndpointsResponse;
import org.bubblecloud.zigbee.network.zdo.command.DeviceAnnounce;
import org.bubblecloud.zigbee.network.zdo.command.SimpleDescriptorRequest;
import org.bubblecloud.zigbee.simple.Command;
import org.bubblecloud.zigbee.simple.CommandListener;
import org.bubblecloud.zigbee.network.zcl.protocol.command.ias.zone.ZoneEnrollRequestCommand;
import org.bubblecloud.zigbee.network.zcl.protocol.command.ias.zone.ZoneEnrollResponseCommand;
import org.bubblecloud.zigbee.util.Cie;

/**
 * ZigBee command line console is an example usage of ZigBee API.
 * It requires a ZigBeePort implementation to function.
 *
 * For a ready-to-run demonstration on a Desktop PC equipped with CC2531 dongle:
 * - Check-out the 'zigbee4java-serialPort' module
 * - Execute class 'ZigBeeSerialConsole' with appropriate params
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 * @author <a href="mailto:chris@cd-jackson.com">Chris Jackson</a>
 */
public final class ZigBeeConsole {
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

	private SerialPort port;
	private int pan;
	private int channel;
	private boolean resetNetwork;
    private ZigBeeApi zigBeeApi;

    public ZigBeeConsole(SerialPort port, int pan, int channel, boolean resetNetwork) {
		this.port         = port;
		this.pan          = pan;
		this.channel      = channel;
		this.resetNetwork = resetNetwork;

		commands.put("quit", 		new QuitCommand());
		commands.put("help", 		new HelpCommand());
		commands.put("list", 		new ListCommand());
		commands.put("nodes", 		new NodesCommand());
		commands.put("desc", 		new DescribeCommand());
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
        commands.put("unlock", 		new DoorUnlockCommand());
        commands.put("enroll", new EnrollCommand());

        final Set<DiscoveryMode> discoveryModes = new HashSet<DiscoveryMode>();
        discoveryModes.add(DiscoveryMode.Addressing);
        discoveryModes.add(DiscoveryMode.Announce);
        zigBeeApi = new ZigBeeApi(port, pan, channel, resetNetwork, discoveryModes);
    }

	/**
     * Starts this console application
     */
    public void start() {
        mainThread = Thread.currentThread();
        System.out.print("ZigBee API starting up...");

        final File networkStateFile = new File("network.json");
        if (!resetNetwork && networkStateFile.exists()) {
            try {
                final String networkState = FileUtils.readFileToString(networkStateFile);
                zigBeeApi.deserializeNetworkState(networkState);
            } catch (final Exception e) {
                e.printStackTrace();
                // Fall through and just start the network without persistence
            }
        }

        if (!zigBeeApi.startup()) {
            print("ZigBee API starting up ... [FAIL]", System.out);
            return;
        } else {
            print("ZigBee API starting up ... [OK]", System.out);
        }

        zigBeeApi.addDeviceListener(new DeviceListener() {
            @Override
            public void deviceAdded(Device device) {
                print("Device added: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")", System.out);
            }

            @Override
            public void deviceUpdated(Device device) {
                print("Device updated: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")", System.out);
            }

            @Override
            public void deviceRemoved(Device device) {
                print("Device removed: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")", System.out);
            }
        });

        zigBeeApi.addNodeListener(new NodeListener() {
            @Override
            public void nodeAdded(ZigBeeNode node) {
                print("Node added: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")", System.out);
            }

            @Override
            public void nodeDiscovered(ZigBeeNode node) {
                print("Node discovered: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")", System.out);
            }

            @Override
            public void nodeUpdated(ZigBeeNode node) {
                print("Node updated: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")", System.out);
            }

            @Override
            public void nodeRemoved(ZigBeeNode node) {
                print("Node removed: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")", System.out);
            }
        });

        zigBeeApi.addCommandListener(new CommandListener() {
            @Override
            public void commandReceived(Command command) {

                //This is an example how to interface directly with ZCL commands.
                //print("Received: " + command.toString(), System.out);
//
//                if (command instanceof DeviceAnnounce) {
//                    final DeviceAnnounce deviceAnnounce = (DeviceAnnounce) command;
//                    final ActiveEndpointsRequest request = new ActiveEndpointsRequest();
//                    request.setDestinationAddress(deviceAnnounce.getNetworkAddress());
//                    request.setNetworkAddressOfInterest(deviceAnnounce.getNetworkAddress());
//                    try {
//                        zigBeeApi.sendCommand(request);
//                    } catch (ZigBeeException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (command instanceof ActiveEndpointsResponse) {
//                    final ActiveEndpointsResponse activeEndpointsResponse = (ActiveEndpointsResponse) command;
//                    for (final int endpoint : activeEndpointsResponse.getActiveEndpoints()) {
//                        final SimpleDescriptorRequest request = new SimpleDescriptorRequest();
//                        request.setDestinationAddress(activeEndpointsResponse.getNetworkAddress());
//                        request.setEndpoint(endpoint);
//                        try {
//                            zigBeeApi.sendCommand(request);
//                        } catch (ZigBeeException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                if (command instanceof ZoneEnrollRequestCommand) {
//                    final ZoneEnrollRequestCommand request = (ZoneEnrollRequestCommand) command;
//                    final int remoteAddress = request.getSourceAddress();
//                    final int remoteEndPoint = request.getSourceEnpoint();
//                    final byte transactionId = request.getTransactionId();
//
//                    final ZoneEnrollResponseCommand response = new ZoneEnrollResponseCommand();
//                    response.setDestinationAddress(remoteAddress);
//                    response.setDestinationEndpoint(remoteEndPoint);
//                    response.setTransactionId(transactionId);
//                    response.setEnrollResponseCode(0);
//                    response.setZoneId(0);
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                zigBeeApi.sendCommand(response);
//                            } catch (ZigBeeException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//                }

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

        if (!networkStateFile.exists()) {
            print("Browsing network for the first time...", System.out);
        }
        while (!shutdown && !networkStateFile.exists() && !zigBeeApi.isInitialBrowsingComplete()) {
            System.out.print('.');
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
        }
        if (!networkStateFile.exists()) {
            print("Browsing network for the first time... [OK]", System.out);
        }
        print("There are " + zigBeeApi.getDevices().size() + " known devices in the network.", System.out);

        print("ZigBee console ready.", System.out);

        String inputLine;
        while (!shutdown && (inputLine = readLine()) != null) {
            processInputLine(inputLine, System.out);
        }

        zigBeeApi.shutdown();

        try {
            FileUtils.writeStringToFile(networkStateFile, zigBeeApi.serializeNetworkState(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ZigBeeApi getZigBeeApi() {
        return zigBeeApi;
    }

    /**
     * Executes command.
     * @param zigbeeApi the ZigBee API
     * @param command the command
     * @param args the arguments including the command
     * @param out the output stream
     */
    private void executeCommand(final ZigBeeApi zigbeeApi, final String command, final String[] args, final PrintStream out) {
        final ConsoleCommand consoleCommand = commands.get(command);
        if (!consoleCommand.process(zigbeeApi, args, out)) {
            print(consoleCommand.getSyntax(), out);
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
     * Gets device from ZigBee API either with index or endpoint ID
     * @param zigbeeApi the zigbee API
     * @param deviceIdentifier the device identifier
     * @return the device or null if no device existed with given device identifier
     */
    private Device getDeviceByIndexOrEndpointId(ZigBeeApi zigbeeApi, String deviceIdentifier) {
        Device device;
        try {
            device = zigbeeApi.getDevices().get(Integer.parseInt(deviceIdentifier));
        } catch (final Exception e) {
            device = zigbeeApi.getDevice(deviceIdentifier);
        }
        return device;
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
         * @return true if command execution succeeded
         */
        boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out);
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {

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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            final List<Device> devices = zigbeeApi.getDevices();
            for (int i = 0; i < devices.size(); i++) {
                final Device device = devices.get(i);
                print(i + ") " + device.getEndpointId() +
                        " [" + device.getNetworkAddress() + "]" +
                        " : " + device.getDeviceType(), out);
            }
            return true;
        }
    }

    /**
     * Prints list of devices to console.
     */
    private class NodesCommand implements ConsoleCommand {
        /**
         * {@inheritDoc}
         */
        public String getDescription() {
            return "Lists physical node information.";
        }
        /**
         * {@inheritDoc}
         */
        public String getSyntax() {
            return "nodes";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            final List<ZigBeeNode> nodes = zigbeeApi.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                final ZigBeeNode node = nodes.get(i);
                print("IEEE Address     : " + node.getIeeeAddress(), out);
                print("Network Address  : #" + node.getNetworkAddress(), out);

	        	ZigBeeNodeDescriptor nodeDescriptor = node.getNodeDescriptor();
	        	if(nodeDescriptor != null) {
		            print("Node Descriptor  : Logical Type       " + nodeDescriptor.getLogicalType(), out);
	        		print("                 : Manufacturer Code  " + String.format("%04X", nodeDescriptor.getManufacturerCode()), out);
	        		print("                 : Max Buffer Size    " + nodeDescriptor.getMaximumBufferSize(), out);
	        		print("                 : Max Transfer Size  " + nodeDescriptor.getMaximumTransferSize(), out);
	        		print("                 : MAC Capabilities   " + nodeDescriptor.getMacCapabilities(), out);
	        		print("                 : Server Mask        " + nodeDescriptor.getServerMask(), out);
	        	}

	        	ZigBeeNodePowerDescriptor powerDescriptor = node.getPowerDescriptor();
	        	if(powerDescriptor != null) {
		            print("Power Descriptor : Power Mode         " + powerDescriptor.getPowerMode(), out);
		            print("                 : Power Source       " + powerDescriptor.getPowerSource(), out);
		            print("                 : Power Level        " + powerDescriptor.getPowerLevel(), out);
		            print("                 : Power Available    " + powerDescriptor.getPowerSourcesAvailable(), out);
	        	}
	            print("-", out);
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);

            if (device == null) {
                return false;
            }

            print("Network Address  : " + device.getNetworkAddress(), out);
            print("Extended Address : " + device.getIeeeAddress(), out);
            print("Endpoint Address : " + device.getEndPointAddress(), out);
            print("Device Profile   : " + ZigBeeApiConstants.getProfileName(device.getProfileId())+ String.format("  (0x%04X)", device.getProfileId()), out);
            print("Device Category  : " + ZigBeeApiConstants.getCategoryDeviceName(device.getProfileId(), device.getDeviceTypeId()), out);
            print("Device Type      : " + device.getDeviceType() + String.format("  (0x%04X)", device.getDeviceTypeId()), out);
            print("Device Version   : " + device.getDeviceVersion(), out);
            print("Implementation   : " + device.getClass().getName(), out);
            print("Input Clusters   : ", out);
            showClusters(device, device.getInputClusters(), out);
            print("Output Clusters  : ", out);
            showClusters(device, device.getOutputClusters(), out);

            return true;
        }

        private void showClusters(final Device device, final int[] clusters, PrintStream out) {
            for (int c : clusters) {
                final Cluster cluster = device.getCluster(c);
                print("                 : " + c + " " + ZigBeeApiConstants.getClusterName(c), out);
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
                                + (attribute.getReporter() != null ? "(" +
                                Integer.toString(attribute.getReporter().getReportListenersCount()) + ")" : "")
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
            return "bind [CLIENT] SERVER CLUSTERID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
                    server.bindTo(client, clusterId);
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
            return "unbind [CLIENT] SERVER CLUSTERID";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
                    server.unbindFrom(client, clusterId);
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 5) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final ColorControl colorControl = device.getCluster(ColorControl.class);
            if (colorControl == null) {
                print("Device does not support color control.", out);
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 3) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final LevelControl levelControl = device.getCluster(LevelControl.class);
            if (levelControl == null) {
                print("Device does not support level control.", out);
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

                levelControl.moveToLevel((short) l, 10);
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 3) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final DoorLock doorLock = device.getCluster(DoorLock.class);
            try {
				doorLock.lock(args[2]);
			} catch (ZigBeeDeviceException e) {
				e.printStackTrace();
			}

            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 3) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final DoorLock doorLock = device.getCluster(DoorLock.class);
            try {
                doorLock.unlock(args[2]);
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }


            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.", out);
                return true;
            }

            reporter.addReportListener(consoleReportListener, false);

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
            return "unlisten [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.", out);
            } else {
                reporter.removeReportListener(consoleReportListener, false);
            }

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
            return "subscribe [DEVICE] [CLUSTER] [ATTRIBUTE] [MIN-INTERVAL] [MAX-INTERVAL]";
        }
        /**
         * {@inheritDoc}
         */
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 6) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
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

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();
            
            if (reporter == null) {
                print("Attribute does not provide reports.", out);
                return true;
            }

            reporter.setMinimumReportingInterval(minInterval);
            reporter.setMaximumReportingInterval(maxInterval);

            reporter.addReportListener(consoleReportListener, true);

            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.", out);
            } else {
                reporter.removeReportListener(consoleReportListener, true);
            }

            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final Cluster cluster = device.getCluster(clusterId);
            if (cluster == null) {
                print("Cluster not found.", out);
                return false;
            }

            final Attribute attribute = cluster.getAttribute(attributeId);
            if (attribute == null) {
                print("Attribute not found.", out);
                return false;
            }

            try {
                print(attribute.getName() + "=" + attribute.getValue(), out);
            } catch (ZigBeeClusterException e) {
                print("Failed to read attribute.", out);
                e.printStackTrace();
            }

            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
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

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                print("Device not found.", out);
                return false;
            }

            final Cluster cluster = device.getCluster(clusterId);
            if (cluster == null) {
                print("Cluster not found.", out);
                return false;
            }

            final Attribute attribute = cluster.getAttribute(attributeId);
            if (attribute == null) {
                print("Attribute not found.", out);
                return false;
            }
            
            if(!attribute.isWritable()) {
                print(attribute.getName() + " is not writable", out);
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
                        val = Boolean.parseBoolean(args[4]);
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
                        val = Integer.parseInt(args[4]);
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
                print("Attribute value written.", out);
            } catch (ZigBeeClusterException e) {
                print("Failed to write attribute.", out);
                e.printStackTrace();
            }

            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            ZigBeeDiscoveryManager discoveryMan = zigbeeApi.getZigBeeDiscoveryManager();
            NetworkNeighbourLinks neighbors = discoveryMan.getLinkQualityInfo();
            final List<ZigBeeNode> nodes = zigbeeApi.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
            	final ZigBeeNode src = nodes.get(i);

            	for (int j = 0; j < nodes.size(); j++) {
                	final ZigBeeNode dst = nodes.get(j);
                	int lqiLast = neighbors.getLast(src.getNetworkAddress(), dst.getNetworkAddress());
                	if(lqiLast != -1) {
                        print("Node #" + src.getNetworkAddress() + " receives node #" + dst.getNetworkAddress() +
                				" with LQI " + lqiLast + " (" +
                				neighbors.getMin(src.getNetworkAddress(), dst.getNetworkAddress()) + "/" +
                				neighbors.getAvg(src.getNetworkAddress(), dst.getNetworkAddress()) + "/" +
                				neighbors.getMax(src.getNetworkAddress(), dst.getNetworkAddress()) + ")", out
                        );
                	}
                }
            }
            
            return true;
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
        public boolean process(ZigBeeApi zigbeeApi, String[] args, PrintStream out) {
            if (args.length != 5) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
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

            final IASWD iasWD = device.getCluster(IASWD.class);
            StartWarningPayload payload = new StartWarningPayloadImpl((short)mode, (short)strobe, duration);
            try {
                iasWD.startWarning(payload);
            } catch (ZigBeeDeviceException e) {
                print("Failed to start warning.", out);
                e.printStackTrace();
            }
            return true;
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
        public boolean process(ZigBeeApi zigbeeApi, String[] args, PrintStream out) {
            if (args.length != 5) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
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

            final IASWD iasWD = device.getCluster(IASWD.class);
            SquawkPayload payload = new SquawkPayloadImpl((short)mode, (short)strobe, (short)level);
            try {
                iasWD.squawk(payload);
            } catch (ZigBeeDeviceException e) {
                print("Failed to start warning.", out);
                e.printStackTrace();
            }
            return true;
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 2) {
                return false;
            }
            
            boolean join = false;
            if(args[1].toLowerCase().startsWith("e")) {
            	join = true;
            }

            if (!zigbeeApi.permitJoin(join)) {
                if (join) {
                    print("ZigBee API permit join enable ... [FAIL]", out);
                } else {
                    print("ZigBee API permit join disable ... [FAIL]", out);
                }
            } else {
                if (join) {
                    print("ZigBee API permit join enable ... [OK]", out);
                } else {
                    print("ZigBee API permit join disable ... [OK]", out);
                }
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args, PrintStream out) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }

            int clusterId = org.bubblecloud.zigbee.api.cluster.impl.api.security_safety.IASZone.ID;
            int attributeId = Attributes.IAS_CIE_ADDRESS.getId();

            final Cluster cluster = device.getCluster(clusterId);
            if (cluster == null) {
                print("Cluster not found.", out);
                return false;
            }

            final Attribute attribute = cluster.getAttribute(attributeId);
            if (attribute == null) {
                print("Attribute not found.", out);
                return false;
            }

            if(!attribute.isWritable()) {
                print(attribute.getName() + " is not writable", out);
                return true;
            }

            try {
                attribute.setValue(zigbeeApi.getZigBeeNetworkManager().getIeeeAddress());
                print("CIE address set to: " + IEEEAddress.toColonNotation(zigbeeApi.getZigBeeNetworkManager().getIeeeAddress()), out);
                print("CIE address verification read: " + IEEEAddress.toColonNotation((Long)attribute.getValue()), out);
            }  catch (Exception e) {
                print("Failed to set CIE address.", out);
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
                print(endPointId + "->" + clusterId + "->" + attribute.getName() + "=" + value, System.out);
            }
        }
    };

}
