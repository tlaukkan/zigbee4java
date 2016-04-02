/**
 * Copyright 2016 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bubblecloud.zigbee.network.zcl;

import java.util.ArrayList;
import java.util.List;

/**
 * Value object which holds single command serialisation format.
 *
 * @author Tommi S.E. Laukkanen
 */
public class ZclCommandFormat {
    /**
     * The command.
     */
    private ZclCommand command;
    /**
     * The fields.
     */
    private List<ZclCommandField> fields = new ArrayList<ZclCommandField>();

    /**
     * Constructor for setting the command.
     * @param command the command
     */
    public ZclCommandFormat(final ZclCommand command) {
        this.command = command;
    }

    /**
     * Gets the command.
     * @return the command
     */
    public ZclCommand getCommand() {
        return command;
    }

    /**
     * Gets the fields.
     * @return the fields
     */
    public List<ZclCommandField> getFields() {
        return fields;
    }
}
