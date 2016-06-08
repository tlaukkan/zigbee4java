[![Gitter](https://badges.gitter.im/tlaukkan/zigbee4java.svg)](https://gitter.im/tlaukkan/zigbee4java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/tlaukkan/zigbee4java.svg?branch=master)](https://travis-ci.org/tlaukkan/zigbee4java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/62db6a5c2e4f4c138c0a8eb076102678)](https://www.codacy.com/app/tommi-s-e-laukkanen/zigbee4java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tlaukkan/zigbee4java&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/tlaukkan/zigbee4java/badge.svg?branch=master)](https://coveralls.io/github/tlaukkan/zigbee4java?branch=master)
[![Download](https://api.bintray.com/packages/tlaukkan/bubblecloud/zigbee4java/images/download.svg) ](https://bintray.com/tlaukkan/bubblecloud/zigbee4java/_latestVersion)

ZigBee API for Java
===================

Zigbee API for Java provides simple Java interface to ZigBee network.

ZigBee API for Java is originally a fork of ZB4OSGI.

Questions can be sent to the following email address: 'zigbee4java@googlegroups.com'.

Or you can contact team members directly: 

* tommi.s.e.laukkanen@gmail.com

(Contributors, please add yourself to the list if you wish to provide support.)

Chat
----

[![Gitter](https://badges.gitter.im/tlaukkan/zigbee4java.svg)](https://gitter.im/tlaukkan/zigbee4java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Discussion Group
----------------

The URL to the discussion group: https://groups.google.com/d/forum/zigbee4java

FAQ
---

The URL to the FAQ: https://groups.google.com/d/forum/zigbee4java

Hardware
--------

This library provides API to ZigBee network through CC2531 dongle.

Example hardware that can be controlled with zigbee4java:

1. Philips Hue Bulb

Prerequisites
-------------

1. Java 7
2. Maven 3 and/or Gradle
3. CC2531 dongle with USB serial / coordinator firmware. Flashing new firmware requires CCDEBUGGER hardware component.
4. Second CC2531 dongle if you want to have cheap packet sniffer. There are probably better packet sniffers out there.

Buying Hardware
---------------

You need to buy the following items:

1. http://www.ti.com/tool/cc2531emk (NOTE: Buy two if you want to use another as packet sniffer.)
2. http://www.ti.com/tool/cc-debugger

Flashing Dongle
---------------

1. Download and install Z-STACK-HOME from http://www.ti.com/tool/z-stack
2. View CCDEBUGGER user guide: http://www.ti.com/lit/ug/swru197h/swru197h.pdf
2. Flash CC2531ZNP-Pro-Secure_Standard.hex with Smart RF Studio included in Z-STACK-HOME.

Maven Repository
----------------

Serial-comm and zigbee4java dependencies can be found from the following repository for convenience.

```
<repositories>
    <repository>
        <id>jcenter-bintray</id>
        <name>bintray</name>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
```

Using as a Dependency
---------------------

Maven:

```
<dependencies>
    <dependency>
      <groupId>org.bubblecloud.zigbee4java</groupId>
      <artifactId>zigbee-api</artifactId>
      <version>2.0.11</version>
      <type>pom</type>
    </dependency>

    <dependency>
        <groupId>org.bubblecloud.zigbee4java</groupId>
        <artifactId>zigbee-serial-javase</artifactId>
        <version>2.0.11</version>
    </dependency>
</dependencies>
```

Gradle:

```
dependencies
{
    compile 'org.bubblecloud.zigbee:zigbee-api:2.0.10'
    compile 'org.bubblecloud.zigbee:zigbee-serial-javase:2.0.10'
}
```


Building from Sources
---------------------

Maven:

```
git clone https://github.com/tlaukkan/zigbee4java.git zigbee4java
cd zigbee4java
mvn clean install
```

Gradle:

```
git clone https://github.com/tlaukkan/zigbee4java.git zigbee4java
cd zigbee4java
gradlew clean build
```


Usage
-----

Simple usage example:

```
final SerialPortImpl serialPort = new SerialPortImpl("/dev/ttyACM0", 38400);
final ZigBeeApi zigBeeApi = new ZigBeeApi(serialPort, 4951, 11, false, DiscoveryMode.ALL);
zigBeeApi.startup();

...

final Device lamp = zigBeeApi.getZigBeeApiContext().getDevice("00:17:88:01:00:BE:51:EC/11");

final Basic basic = lamp.getCluster(Basic);
final String manufactureName = basic.getManufacturerName();

final OnOff onOff = lamp.getCluster(OnOff.class);
onOff.on();

final int onOffAttributeIndex = 0;
final Reporter reporter = onOff.getAttribute(onOffAttributeIndex).getReporter();
reporter.addReportListener(reportListener);
```

Complete startup and shutdown example including network state loading:

```
final boolean resetNetwork = false;
final SerialPortImpl serialPort = new SerialPortImpl("COM5", 38400);
final ZigBeeApi zigBeeApi = new ZigBeeApi(serialPort, 4951, 11, false, DiscoveryMode.ALL);

final File networkStateFile = new File("network.json");
final boolean networkStateExists = networkStateFile.exists();
if (!resetNetwork && networkStateExists) {
    LOGGER.info("ZigBeeApi loading network state...");
    final String networkState = FileUtils.readFileToString(networkStateFile);
    zigBeeApi.deserializeNetworkState(networkState);
    LOGGER.info("ZigBeeApi loading network state done.");
}

LOGGER.info("ZigBeeApi startup...");
if (!zigBeeApi.startup()) {
    LOGGER.error("Error initializing ZigBeeApi.");
    return;
}
LOGGER.info("ZigBeeApi startup done.");

if (!networkStateExists) {
    LOGGER.info("ZigBeeApi initial browsing...");
    while (!zigBeeApi.isInitialBrowsingComplete()) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        LOGGER.info("Waiting for initial browsing to complete.");
    }
    LOGGER.info("ZigBeeApi initial browsing done.");
}

LOGGER.info("ZigBeeApi listing devices...");
final List<Device> devices = zigBeeApi.getDevices();
for (final Device device : devices) {
    LOGGER.info(device.getNetworkAddress() + ")" + device.getDeviceType());
}
LOGGER.info("ZigBeeApi listing devices done.");

LOGGER.info("ZigBeeApi shutdown...");
zigBeeApi.shutdown();
serialPort.close();
LOGGER.info("ZigBeeApi shutdown done.");

LOGGER.info("ZigBeeApi saving network state...");
FileUtils.writeStringToFile(networkStateFile, zigBeeApi.serializeNetworkState(), false);
LOGGER.info("ZigBeeApi saving network state done.");
```

This is an example how to interface directly with ZCL commands to accept IAS zone enroll request:

```
if (command instanceof ZoneEnrollRequestCommand) {
    final ZoneEnrollRequestCommand request = (ZoneEnrollRequestCommand) command;
    final int remoteAddress = request.getSourceAddress();
    final int remoteEndPoint = request.getSourceEnpoint();
    final byte transactionId = request.getTransactionId();

    final ZoneEnrollResponseCommand response = new ZoneEnrollResponseCommand();
    response.setDestinationAddress(remoteAddress);
    response.setDestinationEndpoint(remoteEndPoint);
    response.setTransactionId(transactionId);
    response.setEnrollResponseCode(0);
    response.setZoneId(0);

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                zigBeeApi.sendCommand(response);
            } catch (ZigBeeException e) {
                e.printStackTrace();
            }
        }
    }).start();
}
```

Examples
--------

1. ZigBeeConsole.java

Contributing
------------

1. Follow Google Java style: https://google.github.io/styleguide/javaguide.html
2. Add unit tests for your commit if applicable / possible.
3. Build before commit.
4. Rebase instead of merge when pushing.
5. Check that Travis CI build passes after pushing.
6. Fix any issues in your commit found in Codacy analysis.

Releasing
---------

Release can be done only from master branch by tagging the head revision with the following syntax:

```
{VERSION}-release-ready
```

For example:

```
2.0.10-release-ready
```

This will trigger a release build by the build agent.