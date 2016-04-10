package org.bubblecloud.zigbee.tools;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Code generator for generating ZigBee cluster library command protocol.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclProtocolCodeGenerator {

    /**
     * The main method for running the code generator.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        final String filePath;
        if (args.length != 0) {
            filePath = args[0];
        } else {
            filePath = "./src/main/resources/zcl.def";
        }
        final File file = new File("./src/main/resources/zcl.def");
        if (!file.exists()) {
            System.out.println("Definition file does not exist: " + filePath);
            return;
        }
        generateCode(file);
    }

    public static void generateCode(final File definitionFile) {
        final Context context = new Context();
        try {
            context.lines = new ArrayList<String>(FileUtils.readLines(definitionFile, "UTF-8"));
        } catch (final IOException e) {
            System.out.println("Reading lines from definition file failed: " + definitionFile.getAbsolutePath());
            e.printStackTrace();
            return;
        }

        parseProfile(context);
    }

    private static void parseProfile(Context context) {
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            if (line.startsWith("* ") && line.contains("[")) {
                context.profile = new Profile();
                context.profile.profileName = getHeaderTitle(line);
                context.profile.profileId = getHeaderId(line);
                context.profiles.put(context.profile.profileId, context.profile);
                System.out.println("Profile: " + context.profile.profileName + " " + toHex(context.profile.profileId));
                parseFunctionalDomain(context);
            }
        }
    }

    private static void parseFunctionalDomain(Context context) {
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("* ") && line.contains("[")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("* ")) {
                final String functionalDomainName = getHeaderTitle(line);
                System.out.println(" Functional domain: " + functionalDomainName);

                parseCluster(context);
            }
        }
    }

    private static void parseCluster(Context context) {
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("* ")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("** ")) {
                context.cluster = new Cluster();
                context.cluster.clusterName = getHeaderTitle(line);
                context.cluster.clusterId = getHeaderId(line);
                context.profile.clusters.put(context.cluster.clusterId, context.cluster);
                System.out.println("  " + toHex(context.cluster.clusterId) + ") " + context.cluster.clusterName);

                parseDirection(context);
            }
        }
    }

    private static void parseDirection(Context context) {
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("* ") || line.startsWith("** ")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("*** ")) {
                context.received = line.toLowerCase().contains("received");
                if (context.received) {
                    System.out.println("   Received:");
                } else {
                    System.out.println("   Generated:");
                }

                parseCommand(context);
            }
        }
    }

    private static void parseCommand(Context context) {
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("* ") || line.startsWith("** ") || line.startsWith("*** ")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("**** ")) {
                context.command = new Command();
                context.command.commandName = getHeaderTitle(line);
                context.command.commandId = getHeaderId(line);
                if (context.received) {
                    context.cluster.received.put(context.command.commandId, context.command);
                } else {
                    context.cluster.generated.put(context.command.commandId, context.command);
                }
                System.out.println("     " + toHex(context.command.commandId) + ") " + context.command.commandName);

                parseField(context);
            }
        }
    }

    private static void parseField(Context context) {
        int fieldIndex = 0;
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("*")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("|") && !line.startsWith("|Field") && !line.startsWith("|-")) {
                final String row = line.trim().substring(1, line.length() - 2);
                final String[] columns = row.split("\\|");
                final Field field = new Field();
                field.fieldId = fieldIndex;
                field.fieldName = columns[0].trim();
                field.dataType = columns[1].trim();
                context.command.fields.put(field.fieldId, field);
                System.out.println("      " + toHex(fieldIndex) + ") " +   field.fieldName + ": " +  field.dataType);
                fieldIndex++;
            }
        }
    }


    private static String getHeaderTitle(String line) {
        line = line.substring(line.lastIndexOf("*") + 1);
        if (line.contains("[")) {
            return StringUtils.substringBefore(line, "[").trim();
        } else {
            return line.trim();
        }
    }

    private static int getHeaderId(String line) {
        final String headerIdString = StringUtils.substringBetween(line, "[", "]").trim();
        return fromHex(headerIdString);
    }

    private static class Context {

        public List<String> lines;

        public Profile profile;
        public Cluster cluster;
        public Command command;

        public boolean received;

        public TreeMap<Integer, Profile> profiles = new TreeMap<Integer, Profile>();

    }

    public static class Profile {
        public int profileId;
        public String profileName;
        public TreeMap<Integer, Cluster> clusters = new TreeMap<Integer, Cluster>();
    }

    public static class Cluster {
        public int clusterId;
        public String clusterName;

        public TreeMap<Integer, Command> received = new TreeMap<Integer, Command>();
        public TreeMap<Integer, Command> generated = new TreeMap<Integer, Command>();
    }

    public static class Command {
        public int commandId;
        public String commandName;

        public TreeMap<Integer, Field> fields = new TreeMap<Integer, Field>();
    }

    public static class Field {
        public int fieldId;
        public String fieldName;
        public String dataType;
    }

    private static String toHex(int profileId) {
        return "0x" + Integer.toHexString(profileId);
    }

    private static Integer fromHex(String headerIdString) {
        return Integer.valueOf(StringUtils.substringAfter(headerIdString, "0x"), 16);
    }

}

