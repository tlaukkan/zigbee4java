package org.bubblecloud.zigbee;

import org.apache.commons.io.FileUtils;
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
import org.bubblecloud.zigbee.network.NodeListener;
import org.bubblecloud.zigbee.network.ZigBeeNode;
import org.bubblecloud.zigbee.network.ZigBeeNodeDescriptor;
import org.bubblecloud.zigbee.network.ZigBeeNodePowerDescriptor;
import org.bubblecloud.zigbee.network.discovery.LinkQualityIndicatorNetworkBrowser.NetworkNeighbourLinks;
import org.bubblecloud.zigbee.network.discovery.ZigBeeDiscoveryManager;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.network.impl.ZigBeeNodeImpl;
import org.bubblecloud.zigbee.network.port.ZigBeePort;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.util.Cie;

import java.io.*;
import java.util.*;

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
    private Map<String, ConsoleCommand> commands = new HashMap<String, ConsoleCommand>();

	private ZigBeePort port;
	private int pan;
	private int channel;
	private boolean resetNetwork;
	
	public ZigBeeConsole(ZigBeePort port, int pan, int channel, boolean resetNetwork) {
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
		commands.put("read", 		new ReadCommand());
		commands.put("write", 		new WriteCommand());
		commands.put("join",        new JoinCommand());
		commands.put("lqi", 		new LqiCommand());
	}

	/**
     * Starts this console application
     */
    public void start() {
        mainThread = Thread.currentThread();
        System.out.print("ZigBee API starting up...");
        final EnumSet<DiscoveryMode> discoveryModes = DiscoveryMode.ALL;
        //discoveryModes.remove(DiscoveryMode.LinkQuality);
        final ZigBeeApi zigbeeApi = new ZigBeeApi(port, pan, channel, resetNetwork, discoveryModes);

        final File networkStateFile = new File("network.json");
        if (!resetNetwork && networkStateFile.exists()) {
            try {
                final String networkState = FileUtils.readFileToString(networkStateFile);
                zigbeeApi.deserializeNetworkState(networkState);
            } catch (final Exception e) {
                e.printStackTrace();
                // Fall through and just start the network without persistence
            }
        }

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
                print("Device updated: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")");
            }

            @Override
            public void deviceRemoved(Device device) {
                print("Device removed: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")");
            }
        });

        zigbeeApi.addNodeListener(new NodeListener() {
			@Override
			public void nodeAdded(ZigBeeNode node) {
                print("Node added: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")");
			}

			@Override
			public void nodeDiscovered(ZigBeeNode node) {
                print("Node discovered: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")");
			}

			@Override
			public void nodeUpdated(ZigBeeNode node) {
                print("Node updated: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")");
			}

			@Override
			public void nodeRemoved(ZigBeeNode node) {
                print("Node removed: " + node.getIeeeAddress() + " (#" + node.getNetworkAddress() + ")");
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

        while (!shutdown && !networkStateFile.exists() && !zigbeeApi.isInitialBrowsingComplete()) {
            print("Browsing network for the first time...");
            System.out.print('.');
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
            print("Browsing network for the first time... [OK]");
        }
        print("There are " + zigbeeApi.getDevices().size() + " known devices in the network.");

        print("ZigBee console ready.");

        String inputLine;
        while (!shutdown && (inputLine = readLine()) != null) {
            processInputLine(zigbeeApi, inputLine);
        }

        zigbeeApi.shutdown();

        try {
            FileUtils.writeStringToFile(networkStateFile, zigbeeApi.serializeNetworkState(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes text input line.
     * @param zigbeeApi the ZigBee API
     * @param inputLine the input line
     */
    private void processInputLine(final ZigBeeApi zigbeeApi, final String inputLine) {
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
    private void executeCommand(final ZigBeeApi zigbeeApi, final String command, final String[] args) {
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
            final String inputLine = bufferRead.readLine();
            return inputLine;
        } catch(final IOException e) {
            return null;
        }
    }

    /**
     * Gets device from ZigBee API either with index or endpoint ID
     * @param zigbeeApi the zigbee API
     * @param deviceIdentifier the device identifier
     * @return
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            final List<ZigBeeNode> nodes = zigbeeApi.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                final ZigBeeNode node = nodes.get(i);
                print("IEEE Address     : " + node.getIeeeAddress());
                print("Network Address  : #" + node.getNetworkAddress());

	        	ZigBeeNodeDescriptor nodeDescriptor = node.getNodeDescriptor();
	        	if(nodeDescriptor != null) {
		            print("Node Descriptor  : Logical Type       " + nodeDescriptor.getLogicalType());
	        		print("                 : Manufacturer Code  " + String.format("%04X", nodeDescriptor.getManufacturerCode()));
	        		print("                 : Max Buffer Size    " + nodeDescriptor.getMaximumBufferSize());
	        		print("                 : Max Transfer Size  " + nodeDescriptor.getMaximumTransferSize());
	        		print("                 : MAC Capabilities   " + nodeDescriptor.getMacCapabilities());
	        		print("                 : Server Mask        " + nodeDescriptor.getServerMask());
	        	}

	        	ZigBeeNodePowerDescriptor powerDescriptor = node.getPowerDescriptor();
	        	if(powerDescriptor != null) {
		            print("Power Descriptor : Power Mode         " + powerDescriptor.getPowerMode());
		            print("                 : Power Source       " + powerDescriptor.getPowerSource());
		            print("                 : Power Level        " + powerDescriptor.getPowerLevel());
		            print("                 : Power Available    " + powerDescriptor.getPowerSourcesAvailable());
	        	}
	            print("-");
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);

            if (device == null) {
                return false;
            }

            print("Network Address  : " + device.getNetworkAddress());
            print("Extended Address : " + device.getIeeeAddress());
            print("Endpoint Address : " + device.getEndPointAddress());
            print("Device Profile   : " + ZigBeeApiConstants.getProfileName(device.getProfileId())+ String.format("  (0x%04X)", device.getProfileId()));
            print("Device Category  : " + ZigBeeApiConstants.getCategoryDeviceName(device.getProfileId(), device.getDeviceTypeId()));
            print("Device Type      : " + device.getDeviceType() + String.format("  (0x%04X)", device.getDeviceTypeId()));
            print("Device Version   : " + device.getDeviceVersion());
            print("Implementation   : " + device.getClass().getName());
            print("Input Clusters   : ");
            for (int c : device.getInputClusters()) {
                final Cluster cluster = device.getCluster(c);
                print("                 : " + c + " " + ZigBeeApiConstants.getClusterName(c));
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 3) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final LevelControl levelControl = device.getCluster(LevelControl.class);
            if (levelControl == null) {
                print("Device does not support level control.");
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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }


            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.");
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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.");
            }

            reporter.removeReportListener(consoleReportListener, false);

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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }


            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.");
                return true;
            }

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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeId).getReporter();

            if (reporter == null) {
                print("Attribute does not provide reports.");
            }

            reporter.removeReportListener(consoleReportListener, true);

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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
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

            final Attribute attribute = cluster.getAttribute(attributeId);
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
            final int attributeId;
            try {
                attributeId = Integer.parseInt(args[3]);
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

            final Attribute attribute = cluster.getAttribute(attributeId);
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            ZigBeeDiscoveryManager discoveryMan = zigbeeApi.getZigBeeDiscoveryManager();
            NetworkNeighbourLinks neighbors = discoveryMan.getLinkQualityInfo();
            final List<ZigBeeNode> nodes = zigbeeApi.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
            	final ZigBeeNode src = nodes.get(i);

            	for (int j = 0; j < nodes.size(); j++) {
                	final ZigBeeNode dst = nodes.get(j);
                	int lqiLast = neighbors.getLast(src.getNetworkAddress(), dst.getNetworkAddress());
                	if(lqiLast != -1) {
                		System.out.println("Node #" + src.getNetworkAddress() + " receives node #" + dst.getNetworkAddress() +
                				" with LQI " + lqiLast + " (" +
                				neighbors.getMin(src.getNetworkAddress(), dst.getNetworkAddress()) + "/" +
                				neighbors.getAvg(src.getNetworkAddress(), dst.getNetworkAddress()) + "/" +
                				neighbors.getMax(src.getNetworkAddress(), dst.getNetworkAddress()) + ")"
                				);    		
                	}
                }
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
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }
            
            boolean join = false;
            if(args[1].toLowerCase().startsWith("e")) {
            	join = true;
            }

            if (!zigbeeApi.permitJoin(join)) {
                if (join) {
                    print("ZigBee API permit join enable ... [FAIL]");
                } else {
                    print("ZigBee API permit join disable ... [FAIL]");
                }
            } else {
                if (join) {
                    print("ZigBee API permit join enable ... [OK]");
                } else {
                    print("ZigBee API permit join disable ... [OK]");
                }
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
