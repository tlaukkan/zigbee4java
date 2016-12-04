package org.bubblecloud.zigbee.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bubblecloud.zigbee.tools.zcl.*;

/**
 * Created by tlaukkan on 4/10/2016.
 */
public class ZclProtocolDefinitionParser {
    public static void parseProfiles(Context context) {
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            if (line.startsWith("# ") && line.contains("[")) {
                context.profile = new Profile();
                context.profile.profileName = getHeaderTitle(line);
                context.profile.profileType = CodeGeneratorUtil.labelToEnumerationValue(context.profile.profileName);
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
            if (line.startsWith("# ") && line.contains("[")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("# ")) {
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
            if (line.startsWith("# ")) {
                context.lines.add(0, line);
                return;
            }
            
            if (line.startsWith("## ")) {
                context.cluster = new Cluster();
                context.cluster.clusterName = getHeaderTitle(line);
                context.cluster.clusterDescription = new ArrayList<String>();
                context.cluster.clusterType = CodeGeneratorUtil.labelToEnumerationValue(context.cluster.clusterName);
                context.cluster.clusterId = getHeaderId(line);
                context.cluster.nameUpperCamelCase = CodeGeneratorUtil.labelToUpperCamelCase(context.cluster.clusterName);
                context.cluster.nameLowerCamelCase = CodeGeneratorUtil.upperCamelCaseToLowerCamelCase(context.cluster.clusterName);
                context.profile.clusters.put(context.cluster.clusterId, context.cluster);
                System.out.println("  " + CodeGeneratorUtil.toHex(context.cluster.clusterId) + ") " + context.cluster.clusterName);

                parseDirections(context);
            }
        }
    }

    private static void parseDirections(Context context) {
    	boolean addBreak = false;
    	while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("# ") || line.startsWith("## ")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("### ")) {
            	addBreak = false;
            	
            	context.received = line.toLowerCase().contains("received");
                context.generated = line.toLowerCase().contains("generated");
                context.attribute = line.toLowerCase().contains("attributes");
                if (context.received) {
                    System.out.println("   Received:");
                } else if(context.generated) {
                    System.out.println("   Generated:");
                } else if(context.attribute) {
                    System.out.println("   Attributes:");
                    
                    parseAttributes(context);
                    continue;
                }
                else {
                    System.out.println("   Unknown:");
                }

                parseCommands(context);
                
                continue;
            }
            
            if(context.cluster.clusterDescription.size() == 0 && line.trim().length() == 0) {
            	continue;
            }
            if(line.trim().length() == 0) {
            	addBreak = true;
            	continue;
            }
            if(addBreak) {
            	context.cluster.clusterDescription.add("<br>");
                addBreak = false;            	
            }
            context.cluster.clusterDescription.add(line.trim());
        }
    }

    private static void parseCommands(Context context) {
    	while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("# ") || line.startsWith("## ") || line.startsWith("### ")) {
                context.lines.add(0, line);
                return;
            }
            
            if (line.startsWith("#### ")) {
                context.command = new Command();
                context.command.commandLabel = getHeaderTitle(line);
                String splits[] = context.command.commandLabel.split(" ");

                if("RESPONSE".equals(splits[splits.length-2].toUpperCase()) && "COMMAND".equals(splits[splits.length-1].toUpperCase())) {
                	StringBuilder sb = new StringBuilder();
                	for(int c = 0; c < splits.length-1; c++) {
                		sb.append(" ");
                		sb.append(splits[c]);
                	}
                	
                	context.command.commandLabel = sb.toString();
                }
                
                context.command.commandDescription = new ArrayList<String>();
                context.command.commandType = CodeGeneratorUtil.labelToEnumerationValue(context.command.commandLabel);
                context.command.commandId = getHeaderId(line);
                context.command.nameUpperCamelCase = CodeGeneratorUtil.labelToUpperCamelCase(context.command.commandLabel);
                context.command.nameLowerCamelCase = CodeGeneratorUtil.upperCamelCaseToLowerCamelCase(context.command.nameUpperCamelCase);
                if (context.received) {
                    context.cluster.received.put(context.command.commandId, context.command);
                } else {
                    context.cluster.generated.put(context.command.commandId, context.command);
                }
                System.out.println("     " + CodeGeneratorUtil.toHex(context.command.commandId) + ") " + context.command.commandLabel);

                parseField(context);
            }
        }
    }

    private static void parseField(Context context) {
        int fieldIndex = 0;
        boolean addBreak = false;
        while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("#")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("|") && !line.startsWith("|Field") && !line.startsWith("|-")) {
                final String row = line.trim().substring(1, line.length() - 1);
                final String[] columns = row.split("\\|");
                final Field field = new Field();
                field.fieldId = fieldIndex;
                field.fieldLabel = columns[0].trim();
                field.fieldType = context.command.commandType + "_" + CodeGeneratorUtil.labelToEnumerationValue(field.fieldLabel);
                field.nameUpperCamelCase = CodeGeneratorUtil.labelToEnumerationValue(field.fieldLabel);
                field.nameUpperCamelCase = CodeGeneratorUtil.labelToUpperCamelCase(field.fieldLabel);
                field.nameLowerCamelCase = CodeGeneratorUtil.upperCamelCaseToLowerCamelCase(field.nameUpperCamelCase);
                field.dataType = CodeGeneratorUtil.labelToEnumerationValue(columns[1].trim());

                final DataType dataType = new DataType();
                dataType.dataTypeName = columns[1].trim();
                dataType.dataTypeType = field.dataType;

                dataType.dataTypeClass = ZclDataType.getDataTypeMapping().get(field.dataType).dataClass; 
                if(dataType.dataTypeClass == null) {
                    throw new IllegalArgumentException("Type not mapped: " + field.dataType);
                }
                
                field.dataTypeClass = dataType.dataTypeClass;

                context.dataTypes.put(field.dataType, dataType);
                context.command.fields.put(field.fieldId, field);
                System.out.println("      " + CodeGeneratorUtil.toHex(fieldIndex) + ") " +   field.fieldLabel + ": " +  dataType.dataTypeName);
                fieldIndex++;
            }
            
            if (line.startsWith("|")) {
            	addBreak = false;
            	continue;
            }
            
            if(context.command.commandDescription.size() == 0 && line.trim().length() == 0) {
            	continue;
            }
            if(line.trim().length() == 0) {
            	addBreak = true;
            	continue;
            }
            if(addBreak) {
                context.command.commandDescription.add("<br>");
                addBreak = false;            	
            }
            context.command.commandDescription.add(line.trim());
        }
    }

    private static String getHeaderTitle(String line) {
        line = line.substring(line.lastIndexOf("#") + 1);
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
    
    private static void parseAttributes(Context context) {
    	Attribute attribute = null;
    	boolean addBreak = false;
    	while (context.lines.size() > 0) {
            final String line = context.lines.remove(0);

            // Returning to previous level.
            if (line.startsWith("# ") || line.startsWith("## ") || line.startsWith("### ")) {
                context.lines.add(0, line);
                return;
            }

            if (line.startsWith("|") && !line.startsWith("|Id") && !line.startsWith("|-")) {
            	parseAttribute(context, line);
            }

            if (line.startsWith("#### ")) {
            	attribute = null;
            	for(Attribute attr : context.cluster.attributes.values()) {
            		if(attr.attributeLabel.equals(getHeaderTitle(line).substring(0, getHeaderTitle(line).indexOf(" ")))) {
            			attribute = attr;
            			break;
            		}
            	}
            	
            	continue;
            }
            
            if(attribute == null || (attribute.attributeDescription.size() == 0 && line.trim().length() == 0)) {
            	continue;
            }
            if(line.trim().length() == 0) {
            	addBreak = true;
            	continue;
            }
            if(addBreak) {
            	attribute.attributeDescription.add("<br>");
                addBreak = false;            	
            }
            attribute.attributeDescription.add(line.trim());
        }
    }

    private static void parseAttribute(Context context, String line) {
        final String row = line.trim().substring(1, line.length() - 1);
        final String[] columns = row.split("\\|");
        final Attribute attribute = new Attribute();
        attribute.attributeId = Integer.parseInt(columns[0].trim().substring(2), 16);
        attribute.attributeLabel = columns[1].trim();
        attribute.attributeDescription = new ArrayList<String>();
        attribute.attributeAccess = columns[3].trim();
        attribute.attributeImplementation = columns[4].trim();
        attribute.attributeReporting = columns[5].trim();
        attribute.nameUpperCamelCase = CodeGeneratorUtil.labelToEnumerationValue(attribute.attributeLabel);
        attribute.nameUpperCamelCase = CodeGeneratorUtil.labelToUpperCamelCase(attribute.attributeLabel);
        attribute.nameLowerCamelCase = CodeGeneratorUtil.upperCamelCaseToLowerCamelCase(attribute.nameUpperCamelCase);
        attribute.dataType = CodeGeneratorUtil.labelToEnumerationValue(columns[2].trim());
        attribute.enumName = "ATTR_" + attribute.attributeLabel.toUpperCase();
        final DataType dataType = new DataType();
        dataType.dataTypeName = columns[2].trim();
        dataType.dataTypeType = attribute.dataType;
        dataType.dataTypeClass = ZclDataType.getDataTypeMapping().get(attribute.dataType).dataClass; 
        if(dataType.dataTypeClass == null) {
            throw new IllegalArgumentException("Type not mapped: " + attribute.dataType);
        }
        attribute.dataTypeClass = dataType.dataTypeClass;

        context.dataTypes.put(attribute.dataType, dataType);
        context.cluster.attributes.put(attribute.attributeId, attribute);
    }

}
