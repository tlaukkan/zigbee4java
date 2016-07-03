package org.bubblecloud.zigbee.v3.zcl;

import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.v3.zcl.protocol.ZclClusterType;

/**
 * Created by tlaukkan on 7/3/2016.
 */
public class ZclAttributeTypeCodeGeneratorMain {

    public static void main(final String[] args) {

        System.out.println("package org.bubblecloud.zigbee.network.zcl;");
        System.out.println();
        System.out.println("package org.bubblecloud.zigbee.network.zcl;");

        System.out.println("import org.bubblecloud.zigbee.network.zcl.protocol.ZclClusterType;");
        System.out.println("import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;");
        System.out.println("");

        System.out.println("public enum ZigBeeAttributeType {");

        for (final ZclClusterType clusterType : ZclClusterType.values()) {
            final int profileId = clusterType.getProfileType().getId();
            final int clusterId = clusterType.getId();
            Cluster cluster = ZigBeeApiConstants.getCluster(profileId, clusterId);
            if (cluster != null) {
                for (int a = 0; a < cluster.getAttributes().length; a++) {
                    final Attribute attribute = cluster.getAttributes()[a];

                    System.out.print("   ");
                    System.out.print(clusterType.name());
                    System.out.print('_');
                    System.out.print(labelToEnumerationValue(splitCamelCase(attribute.getName())));
                    System.out.print('(');
                    System.out.print("ZclClusterType.");
                    System.out.print(clusterType.name());
                    System.out.print(',');
                    System.out.print(attribute.getId());
                    System.out.print(',');
                    System.out.print('"');
                    System.out.print(attribute.getName());
                    System.out.print('"');
                    System.out.print(',');
                    System.out.print(attribute.isWritable());
                    System.out.print(',');
                    System.out.print(attribute.isReportable());
                    System.out.print(',');
                    System.out.print("ZigBeeType.");
                    System.out.print(attribute.getZigBeeType().name());
                    System.out.print(',');
                    System.out.print(attribute.getType().getCanonicalName());
                    System.out.print(".class");
                    System.out.print(')');
                    System.out.print(',');
                    System.out.println();

                    /*System.out.println("                 :    " + attribute.getId()
                            + " "
                            + "r"
                            + (attribute.isWritable() ? "w" : "-")
                            + (attribute.isReportable() ? "s" : "-")
                            + " "
                            + attribute.getName()
                            + " "
                            + "  [" + attribute.getZigBeeType() + "]");*/
                }
            }
        }

        System.out.println("}");

    }

    public static String splitCamelCase(String value) {
        return value.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static String labelToEnumerationValue(String dataType) {
        return dataType.trim().toUpperCase().replace(" ", "_").replace("-", "_").replace("/", "_").replace("(", "_").replace(")", "_");
    }

}
