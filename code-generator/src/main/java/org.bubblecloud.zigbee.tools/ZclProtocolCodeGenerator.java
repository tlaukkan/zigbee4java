package org.bubblecloud.zigbee.tools;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                context.profileName = getHeaderTitle(line);
                context.profileId = getHeaderId(line);
                System.out.println("Profile: " + context.profileName + " " + toHex(context.profileId));
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
                context.clusterName = getHeaderTitle(line);
                context.clusterId = getHeaderId(line);
                System.out.println("  " + toHex(context.clusterId) + ") " + context.clusterName);

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
                context.commandName = getHeaderTitle(line);
                context.commandId = getHeaderId(line);
                System.out.println("     " + toHex(context.commandId) + ") " + context.commandName);

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
                final String fieldName = columns[0].trim();
                final String dataType = columns[1].trim();
                System.out.println("      " + toHex(fieldIndex) + ") " +  fieldName + ": " + dataType);
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

        public int profileId;
        public String profileName;

        public int clusterId;
        public String clusterName;

        public int commandId;
        public String commandName;

        public boolean received;

    }

    private static String toHex(int profileId) {
        return "0x" + Integer.toHexString(profileId);
    }

    private static Integer fromHex(String headerIdString) {
        return Integer.valueOf(StringUtils.substringAfter(headerIdString, "0x"), 16);
    }

}

