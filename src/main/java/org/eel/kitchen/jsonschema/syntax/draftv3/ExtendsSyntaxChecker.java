/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.syntax.draftv3;

import com.fasterxml.jackson.databind.JsonNode;
import org.eel.kitchen.jsonschema.report.Message;
import org.eel.kitchen.jsonschema.syntax.AbstractSyntaxChecker;
import org.eel.kitchen.jsonschema.syntax.SyntaxChecker;
import org.eel.kitchen.jsonschema.syntax.SyntaxValidator;
import org.eel.kitchen.jsonschema.util.NodeType;

import java.util.List;

/**
 * Syntax validator for the {@code extends} keyword
 */
public final class ExtendsSyntaxChecker
    extends AbstractSyntaxChecker
{
    private static final SyntaxChecker INSTANCE = new ExtendsSyntaxChecker();

    public static SyntaxChecker getInstance()
    {
        return INSTANCE;
    }

    private ExtendsSyntaxChecker()
    {
        super("extends", NodeType.ARRAY, NodeType.OBJECT);
    }

    @Override
    public void checkValue(final SyntaxValidator validator,
        final List<Message> messages, final JsonNode schema)
    {
        final JsonNode extendsNode = schema.get(keyword);

        if (!extendsNode.isArray()) {
            validator.validate(messages, extendsNode);
            return;
        }

        /*
         * If it is an array, check that its elements are all objects
         */

        final int size = extendsNode.size();

        JsonNode subSchema;
        NodeType type;

        for (int index = 0; index < size; index++) {
            subSchema = extendsNode.get(index);
            type = NodeType.getNodeType(subSchema);
            if (type != NodeType.OBJECT) {
                messages.add(newMsg()
                    .setMessage("incorrect type for array element")
                    .addInfo("index", index).addInfo("found", type)
                    .addInfo("expected", NodeType.OBJECT).build());
                continue;
            }
            validator.validate(messages, subSchema);
        }
    }
}