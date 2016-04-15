package org.bubblecloud.zigbee.tools;

import org.apache.commons.lang.StringUtils;
import org.bubblecloud.zigbee.tools.zcl.*;

/**
 * Created by tlaukkan on 4/10/2016.
 */
public class ZclProtocolDefinitionParser {

    public static void parseProfiles(Context context) {
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            if (line.startsWith("* ") && line.contains("[")) {
                context.profile = new Profile();
                context.profile.profileName = getHeaderTitle(line);
                context.profile.profileType = CodeGeneratorUtil.naturalNameToEnumerationValue(context.profile.profileName);
                context.profile.profileId = getHeaderId(line);
                context.profiles.put(context.profile.profileId, context.profile);
                System.out.println("Profile: " + context.profile.profileName + " " + CodeGeneratorUtil.toHex(context.profile.profileId));
                parseFunctionalDomains(context);
            }
        }
    }

    private static void parseFunctionalDomains(Context context) {
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

                parseClusters(context);
            }
        }
    }

    private static void parseClusters(Context context) {
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
                context.cluster.clusterType = CodeGeneratorUtil.naturalNameToEnumerationValue(context.cluster.clusterName);
                context.cluster.clusterId = getHeaderId(line);
                context.profile.clusters.put(context.cluster.clusterId, context.cluster);
                System.out.println("  " + CodeGeneratorUtil.toHex(context.cluster.clusterId) + ") " + context.cluster.clusterName);

                parseDirections(context);
            }
        }
    }

    private static void parseDirections(Context context) {
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

                parseCommands(context);
            }
        }
    }

    private static void parseCommands(Context context) {
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
                context.command.commandType = CodeGeneratorUtil.naturalNameToEnumerationValue(context.command.commandName);
                context.command.commandId = getHeaderId(line);
                if (context.received) {
                    context.cluster.received.put(context.command.commandId, context.command);
                } else {
                    context.cluster.generated.put(context.command.commandId, context.command);
                }
                System.out.println("     " + CodeGeneratorUtil.toHex(context.command.commandId) + ") " + context.command.commandName);

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
                final String row = line.trim().substring(1, line.length() - 1);
                final String[] columns = row.split("\\|");
                final Field field = new Field();
                field.fieldId = fieldIndex;
                field.fieldName = columns[0].trim();
                field.fieldType = context.command.commandType + "_" + CodeGeneratorUtil.naturalNameToEnumerationValue(field.fieldName);
                field.dataType = CodeGeneratorUtil.naturalNameToEnumerationValue(columns[1].trim());
                if ("0123456789".indexOf(field.dataType.charAt(0)) >= 0) {
                    field.dataType = "_" + field.dataType;
                }
                final DataType dataType = new DataType();
                dataType.dataTypeName = columns[1].trim();
                dataType.dataTypeType = field.dataType;
                context.dataTypes.put(field.dataType, dataType);
                context.command.fields.put(field.fieldId, field);
                System.out.println("      " + CodeGeneratorUtil.toHex(fieldIndex) + ") " +   field.fieldName + ": " +  dataType.dataTypeName);
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
        return CodeGeneratorUtil.fromHex(headerIdString);
    }
}
