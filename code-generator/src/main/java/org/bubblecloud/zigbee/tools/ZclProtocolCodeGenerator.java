package org.bubblecloud.zigbee.tools;

import org.apache.commons.io.FileUtils;
import org.bubblecloud.zigbee.tools.zcl.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code generator for generating ZigBee cluster library command protocol.
 *
 * @author Tommi S.E. Laukkanen
 * @author Chris Jackson
 */
public class ZclProtocolCodeGenerator {
	// The following are offsets to the root package
	static String packageZcl = ".zcl";
	static String packageZclField = packageZcl + ".field";
	static String packageZclCluster = packageZcl + ".clusters";
	static String packageZclProtocol = packageZcl + ".protocol";
	static String packageZclProtocolCommand = packageZclCluster;

    /**
     * The main method for running the code generator.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        final String definitionFilePath;
        if (args.length != 0) {
            definitionFilePath = args[0];
        } else {
            definitionFilePath = "./src/main/resources/zcl_definition.md";
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
//            sourceRootPath = "./src/main/blah/";
            sourceRootPath = "../zigbee-common/src/main/java/";
        }

        final File sourceRootFile = new File(sourceRootPath);
        if (!sourceRootFile.exists()) {
            System.out.println("Source root path does not exist: " + sourceRootFile);
            return;
        }
        if (!sourceRootFile.isDirectory()) {
            System.out.println("Source root path is not directory: " + sourceRootFile);
            return;
        }

        final String packageRoot;
        if (args.length != 0) {
            packageRoot = args[0];
        } else {
            packageRoot = "org.bubblecloud.zigbee.v3";
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

        final String packagePath = getPackagePath(sourceRootPath, packageRoot);
        final File packageFile = getPackageFile(packagePath);

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
            System.out.println("Failed to generate cluster enumeration.");
            e.printStackTrace();
            return;
        }
        
        try {
            generateZclCommandTypeEnumerationXXXXX(context, packageRoot, packageFile);
            generateZclCommandTypeEnumeration(context, packageRoot, packageFile);
        } catch (final IOException e) {
            System.out.println("Failed to generate command enumeration.");
            e.printStackTrace();
            return;
        }

        try {
            generateZclFieldTypeEnumeration(context, packageRoot, packageFile);
        } catch (final IOException e) {
            System.out.println("Failed to generate field enumeration.");
            e.printStackTrace();
            return;
        }

        try {
            generateZclCommandClasses(context, packageRoot, sourceRootPath);
        } catch (final IOException e) {
            System.out.println("Failed to generate profile message classes.");
            e.printStackTrace();
            return;
        }

        try {
            generateZclClusterClasses(context, packageRoot, sourceRootPath);
        } catch (final IOException e) {
            System.out.println("Failed to generate cluster classes.");
            e.printStackTrace();
            return;
        }
    }

    private static File getPackageFile(String packagePath) {
        final File packageFile = new File(packagePath);
        if (!packageFile.exists()) {
            packageFile.mkdirs();
        }
        return packageFile;
    }

    private static String getPackagePath(File sourceRootPath, String packageRoot) {
        return sourceRootPath.getAbsolutePath() + File.separator + packageRoot.replace(".", File.separator);
    }

    private static void generateZclDataTypeEnumeration(Context context, final String packageRootPrefix, File sourceRootPath) throws IOException {
        final String className = "ZclDataType";
        
        final String packageRoot = packageRootPrefix + packageZclProtocol;
        final String packagePath = getPackagePath(sourceRootPath, packageZclProtocol);
        final File packageFile = getPackageFile(packagePath);

        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");

        out.println();
        out.println("import java.util.Calendar;");
        out.println("import " + packageRootPrefix + packageZclField + ".*;");
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<DataType> dataTypes = new LinkedList<DataType>(context.dataTypes.values());
        for (final DataType dataType : dataTypes) {
            final String dataTypeClass;
            if (dataType.dataTypeClass.contains("<")) {
                dataTypeClass = dataType.dataTypeClass.substring(dataType.dataTypeClass.indexOf("<") + 1, dataType.dataTypeClass.indexOf(">"));
            } else {
                dataTypeClass = dataType.dataTypeClass;
            }
            out.print("    " + dataType.dataTypeType + "(\"" + dataType.dataTypeName + "\","
                             + dataTypeClass + ".class" +
                    ")");
            out.println(dataTypes.getLast().equals(dataType) ? ';' : ',');
        }

        out.println();
        out.println("    private final String label;");
        out.println("    private final Class<?> dataClass;");
        out.println();
        out.println("    " + className + "(final String label, final Class<?> dataClass) {");
        out.println("        this.label = label;");
        out.println("        this.dataClass = dataClass;");
        out.println("    }");
        out.println();
        out.println("    public String getLabel() { return label; }");
        out.println("    public Class<?> getDataClass() { return dataClass; }");
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclProfileTypeEnumeration(Context context, String packageRootPrefix, File sourceRootPath) throws IOException {
        final String className = "ZclProfileType";
        
        final String packageRoot = packageRootPrefix + packageZclProtocol;
        final String packagePath = getPackagePath(sourceRootPath, packageZclProtocol);
        final File packageFile = getPackageFile(packagePath);

        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            out.print("    " + profile.profileType + "(" + profile.profileId + ", \"" + profile.profileName + "\")");
            out.println(profiles.getLast().equals(profile) ? ';' : ',');
        }

        out.println();
        out.println("    private final int id;");
        out.println("    private final String label;");
        out.println();
        out.println("    " + className + "(final int id, final String label) {");
        out.println("        this.id = id;");
        out.println("        this.label = label;");
        out.println("    }");
        out.println();
        out.println("    public int getId() { return id; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public String toString() { return label; }");
        out.println();
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclClusterTypeEnumeration(Context context, String packageRootPrefix, File sourceRootPath) throws IOException {
        final String className = "ZclClusterType";
        
        final String packageRoot = packageRootPrefix + packageZclProtocol;
        final String packagePath = getPackagePath(sourceRootPath, packageZclProtocol);
        final File packageFile = getPackageFile(packagePath);
        
        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();
        out.println("import " + packageRootPrefix + packageZcl + ".ZclCluster;");
        out.println("import " + packageRootPrefix + packageZclCluster + ".*;");
        out.println();
        out.println("import java.util.HashMap;");
        out.println("import java.util.Map;");
        
        out.println();
        out.println("public enum " + className + " {");

        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            final LinkedList<Cluster> clusters = new LinkedList<Cluster>(profile.clusters.values());
            for (final Cluster cluster : clusters) {
                out.print("    " + cluster.clusterType + "(" + cluster.clusterId + ", ZclProfileType." + 
                		profile.profileType + ", Zcl" + cluster.nameUpperCamelCase +
                		"Cluster.class, \"" + cluster.clusterName + "\")");
                out.println(clusters.getLast().equals(cluster) ? ';' : ',');
            }
        }

        out.println();
        out.println("    private static final Map<Integer, ZclClusterType> idValueMap = new HashMap<Integer, ZclClusterType>();");
        out.println();
        out.println("    private final int id;");
        out.println("    private final ZclProfileType profileType;");
        out.println("    private final String label;");
        out.println("    private final Class<? extends ZclCluster> clusterClass;");
        out.println();
        out.println("    " + className + "(final int id, final ZclProfileType profileType, final Class<? extends ZclCluster>clusterClass, final String label) {");
        out.println("        this.id = id;");
        out.println("        this.profileType = profileType;");
        out.println("        this.clusterClass = clusterClass;");
        out.println("        this.label = label;");
        out.println("    }");
        out.println();
        out.println("    public int getId() { return id; }");
        out.println("    public ZclProfileType getProfileType() { return profileType; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public String toString() { return label; }");
        out.println("    public Class<? extends ZclCluster> getClusterClass() { return clusterClass; }");
        out.println();
        out.println("    public static ZclClusterType getValueById(final int id) {");
        out.println("        return idValueMap.get(id);");
        out.println("    }");
        out.println();
        out.println("    static {");
        out.println("        for (final ZclClusterType value : values()) {");
        out.println("            idValueMap.put(value.id, value);");
        out.println("        }");
        out.println("    }");
        out.println();
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclCommandTypeEnumerationXXXXX(Context context, String packageRootPrefix, File sourceRootPath) throws IOException {
    	
        final String className = "ZclCommandTypeXXX";
        
        final String packageRoot = packageRootPrefix + packageZclProtocol;
        final String packagePath = getPackagePath(sourceRootPath, packageZclProtocol);
        final File packageFile = getPackageFile(packagePath);

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
                        final boolean generic = cluster.clusterId == 65535;
                        valueRows.add("    " + command.commandType + "(" + command.commandId + ", ZclClusterType." + cluster.clusterType + ", \"" + command.commandLabel + "\", true, " + generic + ")");
                    }
                }
                {
                    final LinkedList<Command> commands = new LinkedList<Command>(cluster.generated.values());
                    for (final Command command : commands) {
                        final boolean generic = cluster.clusterId == 65535;
                        valueRows.add("    " + command.commandType + "(" + command.commandId + ", ZclClusterType." + cluster.clusterType + ", \"" + command.commandLabel + "\", false, " + generic + ")");
                    }
                }
            }
        }

        for (final String valueRow : valueRows) {
            out.print(valueRow);
            out.println(valueRows.getLast().equals(valueRow) ? ';' : ',');
        }

        out.println();
        out.println("    private final int id;");
        out.println("    private final ZclClusterType clusterType;");
        out.println("    private final String label;");
        out.println("    private final boolean received;");
        out.println("    private final boolean generic;");
        out.println();
        out.println("    " + className + "(final int id, final ZclClusterType clusterType, final String label, final boolean received, final boolean generic) {");
        out.println("        this.id = id;");
        out.println("        this.clusterType = clusterType;");
        out.println("        this.label = label;");
        out.println("        this.received = received;");
        out.println("        this.generic = generic;");
        out.println("    }");
        out.println();
        out.println("    public int getId() { return id; }");
        out.println("    public ZclClusterType getClusterType() { return clusterType; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public boolean isReceived() { return received; }");
        out.println("    public boolean isGeneric() { return generic; }");
        out.println("    public String toString() { return label; }");
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclFieldTypeEnumeration(Context context, String packageRootPrefix, File sourceRootPath) throws IOException {
        final String className = "ZclFieldType";
        
        final String packageRoot = packageRootPrefix + packageZclProtocol;
        final String packagePath = getPackagePath(sourceRootPath, packageZclProtocol);
        final File packageFile = getPackageFile(packagePath);

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
                        valueRows.add("    " + field.fieldType + "(" + field.fieldId + ", ZclCommandType." + command.commandType + ", \"" + field.fieldLabel + "\",ZclDataType." + field.dataType + ")");
                    }
                }
            }
        }

        for (final String valueRow : valueRows) {
            out.print(valueRow);
            out.println(valueRows.getLast().equals(valueRow) ? ';' : ',');
        }

        out.println();
        out.println("    private final int id;");
        out.println("    private final ZclCommandType commandType;");
        out.println("    private final String label;");
        out.println("    private final ZclDataType dataType;");
        out.println();
        out.println("    " + className + "(final int id, final ZclCommandType commandType, final String label, final ZclDataType dataType) {");
        out.println("        this.id = id;");
        out.println("        this.commandType = commandType;");
        out.println("        this.label = label;");
        out.println("        this.dataType = dataType;");
        out.println("    }");
        out.println();
        out.println("    public int getId() { return id; }");
        out.println("    public ZclCommandType getCommandType() { return commandType; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public ZclDataType getDataType() { return dataType; }");
        out.println();
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclCommandClasses(Context context, String packageRootPrefix, File sourceRootPath) throws IOException {

        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            final LinkedList<Cluster> clusters = new LinkedList<Cluster>(profile.clusters.values());
            for (final Cluster cluster : clusters) {
                final ArrayList<Command> commands = new ArrayList<Command>();
                commands.addAll(cluster.received.values());
                commands.addAll(cluster.generated.values());
                for (final Command command : commands) {
                    final String packageRoot = packageRootPrefix + packageZclProtocolCommand + "." + cluster.clusterType.replace("_", "").toLowerCase();
                    final String packagePath = getPackagePath(sourceRootPath, packageRoot);
                    final File packageFile = getPackageFile(packagePath);

                    final String className = command.nameUpperCamelCase;
                    final PrintWriter out = getClassOut(packageFile, className);

                    final LinkedList<Field> fields = new LinkedList<Field>(command.fields.values());
                    boolean fieldWithDataTypeList = false;
                    for (final Field field : fields) {
                        if (field.dataTypeClass.startsWith("List")) {
                            fieldWithDataTypeList = true;
                            break;
                        }
                    }

                    out.println("package " + packageRoot + ";");
                    out.println();
                    out.println("import " + packageRootPrefix + packageZcl + ".ZclCommandMessage;");
                    out.println("import " + packageRootPrefix + packageZcl + ".ZclCommand;");
                    out.println("import " + packageRootPrefix + packageZclProtocol + ".ZclCommandType;");
                    if (!fields.isEmpty()) {
                        out.println("import " + packageRootPrefix + packageZclProtocol + ".ZclFieldType;");
                        if (fieldWithDataTypeList) {
                            out.println("import " + packageRootPrefix + packageZclField + ".*;");
                        }
                    }

                    out.println();
                    if (fieldWithDataTypeList) {
                        out.println("import java.util.List;");
                    }

                    out.println();
                    out.println("/**");
                    out.println(" * " + command.commandLabel  + " value object class.");

                	out.println(" * ");
                    for(String line : command.commandDescription) {
                    	out.println(" * " + line);
                    }

                    out.println(" * ");
                    out.println(" * Cluster: " + cluster.clusterName);
                    for(String line : cluster.clusterDescription) {
                    	out.println(" * " + line);
                    }

                	out.println(" * ");
                    out.println(" * Code is autogenerated. Modifications may be overwritten!");

                    out.println(" */");
                    out.println("public class " + className + " extends ZclCommand {");

                    for (final Field field : fields) {
                        out.println("    /**");
                        out.println("     * " + field.fieldLabel + " command message field.");
                        out.println("     */");
                        out.println("    private " + field.dataTypeClass + " " + field.nameLowerCamelCase + ";");
                    }

                    out.println();
                    out.println("    /**");
                    out.println("     * Default constructor setting the command type field.");
                    out.println("     */");
                    out.println("    public " + className + "() {");
                    out.println("        setType(ZclCommandType." + command.commandType + ");");
                    out.println("    }");
                    out.println();
                    out.println("    /**");
                    out.println("     * Constructor copying field values from command message.");
                    out.println("     * @param message the command message");
                    out.println("     */");
                    out.println("    public " + className + "(final ZclCommandMessage message) {");
                    out.println("        super(message);");
                    for (final Field field : fields) {
                        out.println("        this." + field.nameLowerCamelCase + " = (" + field.dataTypeClass +  ") message.getFields().get(ZclFieldType." + field.fieldType + ");");
                    }
                    out.println("    }");
                    out.println();
                    out.println("    @Override");
                    out.println("    public ZclCommandMessage toCommandMessage() {");
                    out.println("        final ZclCommandMessage message = super.toCommandMessage();");
                    for (final Field field : fields) {
                        out.println("        message.getFields().put(ZclFieldType." + field.fieldType + "," + field.nameLowerCamelCase + ");");
                    }
                    out.println("        return message;");
                    out.println("    }");
                    for (final Field field : fields) {
                        out.println();
                        out.println("    /**");
                        out.println("     * Gets " + field.fieldLabel + ".");
                        out.println("     * @return the " + field.fieldLabel);
                        out.println("     */");
                        out.println("    public " + field.dataTypeClass + " get" + field.nameUpperCamelCase + "() {");
                        out.println("        return " + field.nameLowerCamelCase + ";");
                        out.println("    }");
                        out.println();
                        out.println("    /**");
                        out.println("     * Sets " + field.fieldLabel + ".");
                        out.println("     * @param " + field.nameLowerCamelCase +  " the " + field.fieldLabel);
                        out.println("     */");
                        out.println("    public void set" + field.nameUpperCamelCase + "(final " + field.dataTypeClass + " " + field.nameLowerCamelCase + ") {");
                        out.println("        this." + field.nameLowerCamelCase + " = " + field.nameLowerCamelCase + ";");
                        out.println("    }");
                    }

                    out.println();
                    out.println("    @Override");
                    out.println("    public String toString() {");
                    out.println("        final StringBuilder builder = new StringBuilder();");
                    out.println("        builder.append(super.toString());");
                    for (final Field field : fields) {
                        out.println("        builder.append(\", \");");
                        out.println("        builder.append(\"" + field.nameLowerCamelCase + "\");");
                        out.println("        builder.append('=');");
                        out.println("        builder.append(" + field.nameLowerCamelCase + ");");
                    }
                    out.println("        return builder.toString();");
                    out.println("    }");

                    out.println();
                    out.println("}");

                    out.flush();
                    out.close();
                }
            }
        }
    }
    
    private static String getCommandTypeEnum(final Cluster cluster, final Command command, boolean received) {
        return command.commandType + "(ZclClusterType." + cluster.clusterType + ", " + 
        		command.commandId + ", " +
        		command.nameUpperCamelCase + ".class" + ", " +
        		"\"" + command.commandLabel + "\", " +
           		received +
        		")";
    }

    private static void generateZclCommandTypeEnumeration(Context context, String packageRootPrefix, File sourceRootPath) throws IOException {
        final String className = "ZclCommandType";

        final String packageRoot = packageRootPrefix + packageZclProtocol;
        final String packagePath = getPackagePath(sourceRootPath, packageZclProtocol);
        final File packageFile = getPackageFile(packagePath);

        final PrintWriter out = getClassOut(packageFile, className);

        out.println("package " + packageRoot + ";");
        out.println();

        out.println("import " + packageRootPrefix + packageZcl + ".ZclCommand;");
        out.println();
        
        List<String> commandEnum = new ArrayList<String>();
        
        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            final LinkedList<Cluster> clusters = new LinkedList<Cluster>(profile.clusters.values());
            for (final Cluster cluster : clusters) {
            	// Brute force to get the commands in order!
                for (int c = 0; c < 65535; c++) {
                	if(cluster.received.get(c) != null) {
	                    out.println("import " + getClusterCommandPackage(packageRootPrefix, cluster) + "." + cluster.received.get(c).nameUpperCamelCase + ";");

	                    commandEnum.add(getCommandTypeEnum(cluster, cluster.received.get(c), true));
                	}
                	if(cluster.generated.get(c) != null) {
	                    out.println("import " + getClusterCommandPackage(packageRootPrefix, cluster) + "." + cluster.generated.get(c).nameUpperCamelCase + ";");

	                    commandEnum.add(getCommandTypeEnum(cluster, cluster.generated.get(c), false));
                	}
                }                
            }
        }
        out.println();
 
        out.println();
        out.println("/**");
        out.println(" * Code generated command type.");
        out.println(" */");
        out.println("public enum " + className + " {");
        out.println("    /**");
        out.println("     * Register command types.");
        out.println("     */");
        
        for(String command : commandEnum) {
        	out.print("    " + command);
            out.println((commandEnum.indexOf(command) == commandEnum.size() - 1) ? ';' : ',');
        }
        out.println();
        
        out.println("    private final int commandId;");
        out.println("    private final ZclClusterType clusterType;");
        out.println("    private final Class<? extends ZclCommand> commandClass;");
        out.println("    private final String label;");
        out.println("    private final boolean received;");
        out.println();
        out.println("    " + className + "(final ZclClusterType clusterType, final int commandId, final Class<? extends ZclCommand> commandClass, final String label, final boolean received) {");
        out.println("        this.clusterType = clusterType;");        
        out.println("        this.commandId = commandId;");
        out.println("        this.commandClass = commandClass;");
        out.println("        this.label = label;");
        out.println("        this.received = received;");
        out.println("    }");
        out.println();

        out.println("    public ZclClusterType getClusterType() { return clusterType; }");
        out.println("    public int getId() { return commandId; }");
        out.println("    public String getLabel() { return label; }");
        out.println("    public boolean isGeneric() { return clusterType==ZclClusterType.GENERAL; }");        
        out.println("    public boolean isReceived() { return received; }");
        out.println("    public Class<? extends ZclCommand> getCommandClass() { return commandClass; }");
        out.println();
        out.println("    public static ZclCommandType getValueById(final ZclClusterType clusterType, final int commandId) {");
        out.println("        for (final ZclCommandType value : values()) {");
        out.println("            if(value.clusterType == clusterType && value.commandId == commandId) {");
        out.println("                return value;");
        out.println("            }");
        out.println("        }");
        out.println("        return null;");
        out.println("    }");
        out.println("}");

        out.flush();
        out.close();
    }

    private static void generateZclClusterClasses(Context context, String packageRootPrefix, File sourceRootPath) throws IOException {

        final LinkedList<Profile> profiles = new LinkedList<Profile>(context.profiles.values());
        for (final Profile profile : profiles) {
            final LinkedList<Cluster> clusters = new LinkedList<Cluster>(profile.clusters.values());
            for (final Cluster cluster : clusters) {
                final String packageRoot = packageRootPrefix;
                final String packagePath = getPackagePath(sourceRootPath, packageRoot);
                final File packageFile = getPackageFile(packagePath + (packageZclCluster).replace('.', '/'));

                final String className = "Zcl" + cluster.nameUpperCamelCase + "Cluster";
                final PrintWriter out = getClassOut(packageFile, className);

            	final ArrayList<Command> commands = new ArrayList<Command>();
                commands.addAll(cluster.received.values());
                commands.addAll(cluster.generated.values());

                out.println("package " + packageRoot + packageZclCluster + ";");
                out.println();

                Set<String> imports = new HashSet<String>();

                boolean addAttributeTypes = false;
                boolean readAttributes = false;
                for (final Attribute attribute : cluster.attributes.values()) {
                	if(attribute.attributeAccess.toLowerCase().contains("write")) {
                		addAttributeTypes = true;
                	}
                	if(attribute.attributeAccess.toLowerCase().contains("read")) {
                		readAttributes = true;
                	}
                }
                
                if(addAttributeTypes) {
                	imports.add("org.bubblecloud.zigbee.v3.zcl.protocol.ZclDataType");
                }
                
                imports.add(packageRoot + packageZcl + ".ZclCluster");
                imports.add(packageRoot + packageZclProtocol + ".ZclDataType");
//              imports.add(packageRoot + packageZcl + ".ZclCommand");
//                imports.add(packageRoot + packageZcl + ".ZclCommandMessage");
                   
//                imports.add(packageRoot + ".ZigBeeDestination");
                imports.add(packageRoot + ".ZigBeeDevice");
                imports.add(packageRoot + ".ZigBeeApi");
                imports.add(packageRoot + ".CommandResult");
                imports.add(packageRoot + ".ZigBeeDevice");
                imports.add(packageRoot + packageZcl + ".ZclAttribute");
                imports.add("java.util.Map");
                imports.add("java.util.HashMap");
                imports.add("java.util.concurrent.Future");
//                imports.add("org.bubblecloud.zigbee.v3.model.ZigBeeType");

                for (final Command command : commands) {
                	imports.add(getClusterCommandPackage(packageRoot, cluster) + "." + command.nameUpperCamelCase);
                }

                List<String>importList = new ArrayList<String>();
                importList.addAll(imports);
                Collections.sort(importList);
                for(final String importClass : importList) {
                    out.println("import " + importClass + ";");                	                		
                }
                
                out.println();
                out.println("/**");
                out.println(" * <b>" + cluster.clusterName + "</b> cluster implementation (<i>Cluster ID " + String.format("0x%04X", cluster.clusterId) + "</i>).");
           		if(cluster.clusterDescription.size() != 0) {
	                out.println(" * <p>");
	                for(String line : cluster.clusterDescription) {
	                	out.println(" * " + line);
	                }
	                out.println(" * </p>");
           		}
                out.println(" * This code is autogenerated. Modifications may be overwritten!");

                out.println(" */");
                out.println("public class " + className + " extends ZclCluster {");

                out.println("    // Cluster ID");
                out.println("    private static final int CLUSTER_ID = " + String.format("0x%04X;", cluster.clusterId));
                out.println();

                if(cluster.attributes.size() != 0) {
                    out.println("    // Attribute constants");
	                for (final Attribute attribute : cluster.attributes.values()) {
	                	out.println("    private final int " + attribute.enumName + " = " + String.format("0x%04X", attribute.attributeId) + ";");
	                }
	                out.println();
                }
                
                out.println("    // Attribute initialisation");
                out.println("    protected Map<Integer, ZclAttribute> initializeAttributes() {");
                out.println("        Map<Integer, ZclAttribute> attributeMap = new HashMap<Integer, ZclAttribute>(" + cluster.attributes.size() + ");");
                out.println();
                if(cluster.attributes.size() != 0) {
                    for (final Attribute attribute : cluster.attributes.values()) {
                        out.println("        attributeMap.put(" + attribute.attributeId + ", new ZclAttribute(" + attribute.attributeId + ", ZclDataType." + attribute.dataType + ", " +
                                "mandatory".equals(attribute.attributeImplementation.toLowerCase()) + ", " +
                                String.format("0x%X", ZclDataType.getDataTypeMapping().get(attribute.dataType).invalid) + ", " +
                                ZclDataType.getDataTypeMapping().get(attribute.dataType).length +
                                "));");
                    }
                }
                out.println();
                out.println("        return attributeMap;");
                out.println("    }");
                out.println();
                
                out.println("    /**");
                out.println("     * Default constructor.");
                out.println("     */");
                out.println("    public " + className + "(final ZigBeeApi zigbeeApi, final ZigBeeDevice zigbeeDevice) {");
                out.println("        super(zigbeeApi, zigbeeDevice, CLUSTER_ID);");
                out.println("    }");
                out.println();
                out.println();
                
                for (final Attribute attribute : cluster.attributes.values()) {
                	if(attribute.attributeAccess.toLowerCase().contains("write")) {
                    	outputAttributeJavaDoc(out, "Set", attribute);
                		out.println("    public Future<CommandResult> set" + attribute.nameUpperCamelCase + "(final Object value) {");
                		out.println("        return write(" + attribute.enumName + ", ZclDataType." + attribute.dataType + ", value);");
                		out.println("    }");
                		out.println();
                	}

                	if(attribute.attributeAccess.toLowerCase().contains("read")) {
                    	outputAttributeJavaDoc(out, "Get", attribute);
                		out.println("    public Future<CommandResult> get" + attribute.nameUpperCamelCase + "() {");
                		out.println("        return read(" + attribute.enumName + ");");
                		out.println("    }");
                		out.println();
                	}

                	if(attribute.attributeAccess.toLowerCase().contains("read") && attribute.attributeReporting.toLowerCase().equals("mandatory")) {
                    	outputAttributeJavaDoc(out, "Configure reporting for", attribute);
                		out.println("    public Future<CommandResult> config" + attribute.nameUpperCamelCase + "Reporting(final int minInterval, final int maxInterval, final Object reportableChange) {");
                		out.println("        return report(" + attribute.enumName + ", minInterval, maxInterval, reportableChange);");
                		out.println("    }");
                		out.println();
                	}
                }

                for (final Command command : commands) {
                	out.println();
                	out.println("    /**");
               		out.println("     * The " + command.commandLabel);
               		if(command.commandDescription.size() != 0) {
               			out.println("     * <p>");
	                    for(String line : command.commandDescription) {
	                    	out.println("     * " + line);
	                    }
	                	out.println("     * </p>");
               		}
                	out.println("     *");
            		out.println("     * @return the {@link Future<CommandResult>} command result future");
                	out.println("     */");
                	out.println("    public Future<CommandResult> " + command.nameLowerCamelCase + "() {");
                    out.println("        return send(new " + command.nameUpperCamelCase + "());");
                    out.println("    }");
                    out.println();
                }

                
                if(readAttributes) {
                	out.println();
                	out.println("    /**");
               		out.println("     * Add a binding for this cluster to the local node");
                	out.println("     *");
            		out.println("     * @return the {@link Future<CommandResult>} command result future");
                	out.println("     */");
                	out.println("    public Future<CommandResult> bind() {");
                    out.println("        return bind();");
                    out.println("    }");
                    out.println();            	
                }

                out.println("}");

                out.flush();
                out.close();
            }
        }
    }

    private static void outputAttributeJavaDoc(PrintWriter out, String type, Attribute attribute) {
    	out.println();
    	out.println("    /**");
   		out.println("     * " + type + " the <i>" + attribute.attributeLabel + "</i> attribute");
   		if(attribute.attributeDescription.size() != 0) {
	    	out.println("     * <p>");
	        for(String line : attribute.attributeDescription) {
	        	out.println("     * " + line);
	        }
   		}
    	out.println("     * </p>");
    	out.println("     * The attribute is of type {@link " + attribute.dataTypeClass + "}<br>");
    	out.println("     * The implementation of this attribute by a device is " + attribute.attributeImplementation.toUpperCase());
    	out.println("     *");
    	if("Set".equals(type)) {
    		out.println("     * @param " + attribute.nameLowerCamelCase + " the {@link " + attribute.dataTypeClass + "} attribute value to be set");
    	}
    	if("Configure reporting for".equals(type)) {
    		out.println("     * @param minInterval {@link int} minimum reporting period");
			out.println("     * @param maxInterval {@link int} maximum reporting period");
    		out.println("     * @param reportableChange {@link Object} delta required to trigger report");
    	}
		out.println("     * @return the {@link Future<CommandResult>} command result future");
    	out.println("     */");
    }

    private static PrintWriter getClassOut(File packageFile, String className) throws FileNotFoundException {
        final File classFile = new File(packageFile + File.separator + className + ".java");
        System.out.println("Generating: " + classFile.getAbsolutePath());
        final FileOutputStream fileOutputStream = new FileOutputStream(classFile, false);
        return new PrintWriter(fileOutputStream);
    }

    public static List<String> splitString(String msg, int lineSize) {
        List<String> res = new ArrayList<String>();

        Pattern p = Pattern.compile("\\b.{1," + (lineSize-1) + "}\\b\\W?");
        Matcher m = p.matcher(msg);

        while(m.find()) {
                System.out.println(m.group().trim());   // Debug
                res.add(m.group());
        }
        return res;
    }
    
    private static String getClusterCommandPackage(String packageRoot, Cluster cluster) {
    	return packageRoot + packageZclProtocolCommand + "." + cluster.clusterType.replace("_", "").toLowerCase();
    }
}

