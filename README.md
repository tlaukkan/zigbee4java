[![Build Status](https://travis-ci.org/tlaukkan/zigbee4java.svg?branch=master)](https://travis-ci.org/tlaukkan/zigbee4java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/62db6a5c2e4f4c138c0a8eb076102678)](https://www.codacy.com/app/tommi-s-e-laukkanen/zigbee4java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tlaukkan/zigbee4java&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/tlaukkan/zigbee4java/badge.svg?branch=master)](https://coveralls.io/github/tlaukkan/zigbee4java?branch=master)

ZigBee API for Java
===================

ZigBee API for Java is a fork of ZB4OSGI for usage without OSGI. Changes include removal of OSGI related dependencies and refactored API.

See the [API Java Doc here](http://tlaukkan.github.io/zigbee4java/)

ZB4OSGI: http://zb4osgi.aaloa.org/

Questions can be sent to the following email address: 'zigbee4java@googlegroups.com'.

You can also contact me directly: 'tommi.s.e.laukkanen@gmail.com'.

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
        <id>bubblecloud-cloudbees-release</id>
        <url>http://repository-bubblecloud.forge.cloudbees.com/release/</url>
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
        <version>2.0.2</version>
    </dependency>

    <dependency>
        <groupId>org.bubblecloud.zigbee4java</groupId>
        <artifactId>zigbee-serial-javase</artifactId>
        <version>2.0.2</version>
    </dependency>
</dependencies>
```

Gradle:

```
dependencies
{
    compile 'org.bubblecloud.zigbee:zigbee-api:2.0.2'
    compile 'org.bubblecloud.zigbee:zigbee-serial-javase:2.0.2'
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

```
ZigBeeSerialPortImpl serialPort = new ZigBeeSerialPortImpl("/dev/ttyACM0", 38400);
ZigBeeConsole console = new ZigBeeConsole(serialPort, 4951, 11, false);
zigbeeApi.startup();

Device lamp = zigbeeApi.getZigBeeApiContext().getDevice("00:17:88:01:00:BE:51:EC/11");

Basic basic = lamp.getCluster(Basic);
String manufactureName = basic.getManufacturerName();

OnOff onOff = lamp.getCluster(OnOff.class);
onOff.on();

int onOffAttributeIndex = 0;
Reporter reporter = onOff.getAttribute(onOffAttributeIndex).getReporter();
reporter.addReportListener(reportListener);
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
