package org.bubblecloud.zigbee.tools;

import org.apache.commons.io.FileUtils;
import org.bubblecloud.zigbee.tools.zcl.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
        final String definitionFilePath;
        if (args.length != 0) {
            definitionFilePath = args[0];
        } else {
            definitionFilePath = "./src/main/resources/zcl.def";
        }

        final File definitionFile = new File(definitionFilePath);
        if (!definitionFile.exists()) {
            System.out.println("Definition file does not exist: " + definitionFilePath);
            return;
        }

        final String sourceRootPath;
        if (args.length != 0) {
            sourceRootPath = args[0];
        } else {
            sourceRootPath = "../zigbee-api/src/main/java/";
        }

        final File sourceRootFile = new File(sourceRootPath);
        if (!sourceRootFile.exists()) {
            System.out.println("Source root path does not exist: " + definitionFilePath);
            return;
        }
        if (!sourceRootFile.isDirectory()) {
            System.out.println("Source root path is not directory: " + definitionFilePath);
            return;
        }

        final String packageRoot;
        if (args.length != 0) {
            packageRoot = args[0];
        } else {
            packageRoot = "org.bubblecloud.zigbee.network.zcl.protocol";
        }

        generateCode(definitionFile, sourceRootFile, packageRoot);
    }


    public static void generateCode(final File definitionFile, final File sourceRootPath, final String packageRoot) {
        final Context context = new Context();
        try {
            context.lines = new ArrayList<String>(FileUtils.readLines(definitionFile, "UTF-8"));
        } catch (final IOException e) {
            System.out.println("Reading lines from definition file failed: " + definitionFile.getAbsolutePath());
            e.printStackTrace();
            return;
        }

        ZclProtocolDefinitionParser.parseProfiles(context);

        final String packagePath = sourceRootPath.getAbsolutePath() + File.separator + packageRoot.replace(".", File.separator);
        final File packageFile = new File(packagePath);
        if (!packageFile.exists()) {
            packageFile.mkdirs();
        }

        try {
            generateZclDataTypeEnumeration(context, packageRoot, packageFile);
        } catch (final IOException e) {
            System.out.println("Failed to generate data types enumeration.");
            e.printStackTrace();
            return;
        }

        try {
            generateZclProfileTypeEnumeration(context, packageRoot, packageFile);
        } catch (final IOException e) {
            System.out.println("Failed to generate profile enumeration.");
            e.printStackTrace();
            return;
        }

        try {
            generateZclClusterTypeEnumeration(context, packageRoot, packageFile);
        } catch (final IOException e) {
            System.out.println("Failed to generate profile enumeration.");
            e.printStackTrace();
            return;
        }

        try {
            generateZclCommandTypeEnumeration(context, packageRoot, packageFile);
        } catch (final IOException e) {
            System.out.println("Failed to generate profile enumeration.");
            e.printStackTrace();
            return;
        }

        try {
            generateZclFieldTypeEnumeration(context, packageRoot, packageFile);
        } catch (final IOException e) {
            System.out.println("Failed to generate profile enumeration.");
            e.printStackTrace();
            return;
        }
    }

    private static void generateZclDataTypeEnumeration(Context context, final String packageRoot, File packageFile) throws IOException {
        final String className = "ZclDataType";
        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<DataType> dataTypes = new LinkedList<DataType>(context.dataTypes.values());
        for (final DataType dataType : dataTypes) {
            out.print("    " + dataType.dataTypeType + "(\"" + dataType.dataTypeName + "\")");
            if (!dataTypes.getLast().equals(dataType)) {
                out.println(",");
            } else {
                out.println(";");
            }
        }

        out.println("    private final String label;");
        out.println("");
        out.println("    " + className + "(final String label) {");
        out.println("        this.label = label;");
        out.println("    }");
        out.println("");
        out.println("    public String getLabel() { return label; }");
        out.println("");
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclProfileTypeEnumeration(Context context, String packageRoot, File packageFile) throws IOException {
        final String className = "ZclProfileType";
        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            out.print("    " + profile.profileType + "(" + profile.profileId + ", \"" + profile.profileName + "\")");
            if (!profiles.getLast().equals(profile)) {
                out.println(",");
            } else {
                out.println(";");
            }
        }

        out.println("");
        out.println("    private final int id;");
        out.println("    private final String label;");
        out.println("");
        out.println("    " + className + "(final int id, final String label) {");
        out.println("        this.id = id;");
        out.println("        this.label = label;");
        out.println("    }");
        out.println("");
        out.println("    public int getId() { return id; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public String toString() { return label; }");
        out.println("");
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclClusterTypeEnumeration(Context context, String packageRoot, File packageFile) throws IOException {
        final String className = "ZclClusterType";
        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            final LinkedList<Cluster> clusters = new LinkedList<Cluster>(profile.clusters.values());
            for (final Cluster cluster : clusters) {
                out.print("    " + cluster.clusterType + "(" + cluster.clusterId + ", ZclProfileType." + profile.profileType + ", \"" + cluster.clusterName + "\")");
                if (!clusters.getLast().equals(cluster)) {
                    out.println(",");
                } else {
                    out.println(";");
                }
            }
        }

        out.println("");
        out.println("    private final int id;");
        out.println("    private final ZclProfileType profileType;");
        out.println("    private final String label;");
        out.println("");
        out.println("    " + className + "(final int id, final ZclProfileType profileType, final String label) {");
        out.println("        this.id = id;");
        out.println("        this.profileType = profileType;");
        out.println("        this.label = label;");
        out.println("    }");
        out.println("");
        out.println("    public int getId() { return id; }");
        out.println("    public ZclProfileType getProfileType() { return profileType; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public String toString() { return label; }");
        out.println("");
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclCommandTypeEnumeration(Context context, String packageRoot, File packageFile) throws IOException {
        final String className = "ZclCommandType";
        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<String> valueRows = new LinkedList<String>();
        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            final LinkedList<Cluster> clusters = new LinkedList<Cluster>(profile.clusters.values());
            for (final Cluster cluster : clusters) {
                {
                    final LinkedList<Command> commands = new LinkedList<Command>(cluster.received.values());
                    for (final Command command : commands) {
                        valueRows.add("    " + command.commandType + "(" + command.commandId + ", ZclClusterType." + cluster.clusterType + ", \"" + command.commandName + "\", true, false)");
                    }
                }
                {
                    final LinkedList<Command> commands = new LinkedList<Command>(cluster.generated.values());
                    for (final Command command : commands) {
                        valueRows.add("    " + command.commandType + "(" + command.commandId + ", ZclClusterType." + cluster.clusterType + ", \"" + command.commandName + "\", false, false)");
                    }
                }
            }
        }

        for (final String valueRow : valueRows) {
            out.print(valueRow);
            if (!valueRows.getLast().equals(valueRow)) {
                out.println(',');
            } else {
                out.println(';');
            }
        }

        out.println("");
        out.println("    private final int id;");
        out.println("    private final ZclClusterType clusterType;");
        out.println("    private final String label;");
        out.println("    private final boolean received;");
        out.println("    private final boolean generic;");
        out.println("");
        out.println("    " + className + "(final int id, final ZclClusterType clusterType, final String label, final boolean received, final boolean generic) {");
        out.println("        this.id = id;");
        out.println("        this.clusterType = clusterType;");
        out.println("        this.label = label;");
        out.println("        this.received = received;");
        out.println("        this.generic = generic;");
        out.println("    }");
        out.println("");
        out.println("    public int getId() { return id; }");
        out.println("    public ZclClusterType getClusterType() { return clusterType; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public boolean isReceived() { return received; }");
        out.println("    public boolean isGeneric() { return generic; }");
        out.println("    public String toString() { return label; }");
        out.println("");
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclFieldTypeEnumeration(Context context, String packageRoot, File packageFile) throws IOException {
        final String className = "ZclFieldType";
        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<String> valueRows = new LinkedList<String>();
        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            final LinkedList<Cluster> clusters = new LinkedList<Cluster>(profile.clusters.values());
            for (final Cluster cluster : clusters) {
                final ArrayList<Command> commands = new ArrayList<Command>();
                commands.addAll(cluster.received.values());
                commands.addAll(cluster.generated.values());
                for (final Command command : commands) {
                    final LinkedList<Field> fields = new LinkedList<Field>(command.fields.values());
                    for (final Field field : fields) {
                        valueRows.add("    " + field.fieldType + "(" + field.fieldId + ", ZclCommandType." + command.commandType + ", \"" + field.fieldName + "\",ZclDataType." + field.dataType + ")");
                    }
                }
            }
        }

        for (final String valueRow : valueRows) {
            out.print(valueRow);
            if (!valueRows.getLast().equals(valueRow)) {
                out.println(',');
            } else {
                out.println(';');
            }
        }

        out.println("");
        out.println("    private final int id;");
        out.println("    private final ZclCommandType commandType;");
        out.println("    private final String label;");
        out.println("    private final ZclDataType dataType;");
        out.println("");
        out.println("    " + className + "(final int id, final ZclCommandType commandType, final String label, final ZclDataType dataType) {");
        out.println("        this.id = id;");
        out.println("        this.commandType = commandType;");
        out.println("        this.label = label;");
        out.println("        this.dataType = dataType;");
        out.println("    }");
        out.println("");
        out.println("    public int getId() { return id; }");
        out.println("    public ZclCommandType getCommandType() { return commandType; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public ZclDataType getDataType() { return dataType; }");
        out.println("    public String toString() { return label; }");
        out.println("");
        out.println("}");

        out.flush();
        out.close();
    }

    private static PrintWriter getClassOut(File packageFile, String className) throws FileNotFoundException {
        final File classFile = new File(packageFile + File.separator + className + ".java");
        System.out.println("Generating: " + classFile.getAbsolutePath());
        final FileOutputStream fileOutputStream = new FileOutputStream(classFile, false);
        return new PrintWriter(fileOutputStream);
    }
}

