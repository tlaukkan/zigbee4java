zigbee4java
===========

This is fork of ZB4OSGI for usage without OSGI.

This library provides API to Zigbee network through CC2531 dongle.

Prerequisites
-------------

1. Java 7
2. Maven 3
3. Serial-comm jar with correct native library in maven repository.
4. CC2531 dongle with USB serial / coordinator firmware. Flashing new firmware requires CCDEBUGGER hardware component.

Download Sources
----------------

git clone https://github.com/tlaukkan/zigbee4java.git zigbee4java

Build
-----

cd zigbee4java
mvn clean install

Maven Dependency
----------------

```
<dependency>
    <groupId>org.bubblecloud.zigbee4java</groupId>
    <artifactId>zigbee-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Usage
-----

```
ZigbeeApi zigbeeApi = new ZigbeeApi("/dev/ttyACM0", 4951, 11, false);
zigbeeApi.startup();

Device lampDevice = zigbeeApi.getZigbeeApiContext().getDevice("00:17:88:01:00:BE:51:EC/11");

OnOff onOff = lampDevice.getCluster(OnOff.class);

int onOffAttributeIndex = 0;
Reporter reporter = onOff.getAttribute(onOffAttributeIndex).getReporter();
reporter.addReportListener(reportListener);

onOff.on();
```

Examples
--------

1. ZigbeeConsole.java

