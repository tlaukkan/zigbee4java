[![Gitter](https://badges.gitter.im/tlaukkan/zigbee4java.svg)](https://gitter.im/tlaukkan/zigbee4java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/tlaukkan/zigbee4java.svg?branch=master)](https://travis-ci.org/tlaukkan/zigbee4java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/62db6a5c2e4f4c138c0a8eb076102678)](https://www.codacy.com/app/tommi-s-e-laukkanen/zigbee4java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tlaukkan/zigbee4java&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/tlaukkan/zigbee4java/badge.svg?branch=master)](https://coveralls.io/github/tlaukkan/zigbee4java?branch=master)
[![Download](https://api.bintray.com/packages/tlaukkan/bubblecloud/zigbee4java/images/download.svg) ](https://bintray.com/tlaukkan/bubblecloud/zigbee4java/_latestVersion)

ZigBee API for Java
===================

ZigBee API for Java provides simple Java interface to ZigBee network. ZigBee API for Java version 3 can be used in 
embedded or gateway mode. In gateway mode a gateway process is run to provide simultaneous administrator shell access
and JSON RPC interface to ZigBee network. ZigBee Gateway Client library can be used to connect to gateway.
See 'Usage'-chapter for examples. 

ZigBee API for Java is originally a fork of ZB4OSGI.

See also https://github.com/zsmartsystems/com.zsmartsystems.zigbee

Questions can be sent to the following email address: 'zigbee4java@googlegroups.com'.

Or you can contact team members directly: 

* tommi.s.e.laukkanen@gmail.com
* chris@cd-jackson.com

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

NOTE: To support other dongles it is necessary to implement ZigBeeDongle interface.

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

Building from Sources
---------------------

Maven:

```
git clone https://github.com/tlaukkan/zigbee4java.git zigbee4java
cd zigbee4java
mvn clean install
```

Gradle:

NOTE: Gradle build is currently broken in 3.0.

```
git clone https://github.com/tlaukkan/zigbee4java.git zigbee4java
cd zigbee4java
gradlew clean build
```

Embedded Usage
--------------

Maven build dependencies:

```
<dependencies>
    <dependency>
      <groupId>org.bubblecloud.zigbee4java</groupId>
      <artifactId>zigbee-dongle-cc2531</artifactId>
      <version>3.x.x</version>
      <type>pom</type>
    </dependency>
    <dependency>
      <groupId>org.bubblecloud.zigbee4java</groupId>
      <artifactId>zigbee-serial-javase</artifactId>
      <version>3.x.x</version>
      <type>pom</type>
    </dependency>
</dependencies>
```

Gradle build dependencies:

```
dependencies
{
    compile 'org.bubblecloud.zigbee:zigbee-dongle-cc2531:3.x.x'
    compile 'org.bubblecloud.zigbee:zigbee-serial-javase:3.x.x'
}
```

Example:

```
final byte[] networkKey = null; // Default network key
final SerialPort port = new SerialPortImpl("COM5");
final ZigBeeDongle dongle = new ZigBeeDongleTiCc2531Impl(port, 4951, 11, networkKey, false);
final ZigBeeApiDongleImpl api = new ZigBeeApiDongleImpl(dongle, false);

api.startup();

final ZigBeeDevice device = api.getZigBeeDevices().get(3);

api.on(device);
Thread.sleep(1000);
api.color(device, 1.0, 0.0, 0.0, 1.0);
Thread.sleep(1000);
api.color(device, 0.0, 1.0, 0.0, 1.0);
Thread.sleep(1000);
api.color(device, 0.0, 0.0, 1.0, 1.0);
Thread.sleep(1000);
api.off(device);

api.shutdown();
port.close();
```

Gateway Usage
-------------

You can run ZigBee gateway as a separate process from command line and connect to it with ZigBee Gateway Client.
ZigBee Gateway Client provides ZigBee API and console commands remotely.

Run server by building zigbee4java with maven and executing gateway shell script under zigbee-gateway-server.

Maven build dependencies:

```
<dependencies>
    <dependency>
      <groupId>org.bubblecloud.zigbee4java</groupId>
      <artifactId>zigbee-gateway-client</artifactId>
      <version>3.x.x</version>
      <type>pom</type>
    </dependency>
</dependencies>
```

Gradle build dependencies:

```
dependencies
{
    compile 'org.bubblecloud.zigbee:zigbee-gateway-client:3.x.x'
}
```

Example:

```
final ZigBeeGatewayClient api = new ZigBeeGatewayClient("http://127.0.0.1:5000/", "secret");

api.startup();

api.addCommandListener(new CommandListener() {
    @Override
    public void commandReceived(final Command command) {
        System.out.println(command);
    }
});

final ZigBeeDevice device = api.getZigBeeDevices().get(3);

api.on(device);
Thread.sleep(1000);
api.color(device, 1.0, 0.0, 0.0, 1.0);
Thread.sleep(1000);
api.color(device, 0.0, 1.0, 0.0, 1.0);
Thread.sleep(1000);
api.color(device, 0.0, 0.0, 1.0, 1.0);
Thread.sleep(1000);
api.off(device);

api.shutdown();
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

Kotlin example:

```
package org.bubblecloud.zikky

import org.bubblecloud.zigbee.v3.ZigBeeGatewayClient
import org.bubblecloud.zigbee.v3.zcl.protocol.command.ias.zone.ZoneStatusChangeNotificationCommand
import kotlin.concurrent.fixedRateTimer

fun main(args : Array<String>) {
    val api = ZigBeeGatewayClient("http://127.0.0.1:5000/", "secret")

    println("Zikky startup...")
    api.startup()
    println("Zikky startup.")

    onExit {
        println("Zikky shutdown...")
        api.shutdown()
        println("Zikky shutdown.")
    }

    val devices = api.devices.filter { it.label != null }.associateBy({it.label}, {it})
    val groups = api.groups.associateBy({it.label}, {it})

    val zone = devices["zone"]!!
    val lamps = groups["lamps"]!!

    var movement = false
    var lighting = false
    var changeTime = currentTime()

    api.addCommandListener {
        if (it is ZoneStatusChangeNotificationCommand) {
            val command: ZoneStatusChangeNotificationCommand = it

            if (command.sourceAddress == zone.networkAddress && command.sourceEnpoint == zone.endpoint) {
                val alarm1 = command.zoneStatus and (1 shl 0) > 0
                val alarm2 = command.zoneStatus and (1 shl 1) > 0
                movement = alarm1 || alarm2
                changeTime = currentTime()
                if (movement) {
                    println("Zone movement detected.")
                } else {
                    println("Zone movement not detected.")
                }
            }
        }
    }

    fixedRateTimer("Lighting timer", true, 0, 1000, {
        if (movement != lighting) {
            if (movement) {
                api.on(lamps)
                lighting = true
                changeTime = currentTime()
                println("Occupied, switched lamps on.")
            } else {
                if (currentTime() - changeTime > 15 * 60000) {
                    api.off(lamps)
                    lighting = false
                    changeTime = currentTime()
                    println("Unoccupied, switched lamps off.")
                }
            }
        }
    })

}

fun currentTime(): Long {
    return System.currentTimeMillis()
}

fun onExit(shutdownHook: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(Thread(shutdownHook))
}
```

More API Examples
-----------------

1. ZigBeeGateway.java

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
3.0.0-release-ready
```

This will trigger a release build by the build agent.
