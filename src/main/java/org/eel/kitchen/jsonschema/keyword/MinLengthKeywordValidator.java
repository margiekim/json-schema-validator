/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.keyword;

import org.codehaus.jackson.JsonNode;
import org.eel.kitchen.jsonschema.context.ValidationContext;

/**
 * Keyword validator for the {@code minLength} keyword (draft section 5.17)
 */
public final class MinLengthKeywordValidator
    extends SimpleKeywordValidator
{
    /**
     * Value for {@code minLength}
     */
    private final int minLength;

    public MinLengthKeywordValidator(final ValidationContext context,
        final JsonNode instance)
    {
        super(context, instance);
        minLength = schema.get("minLength").getIntValue();
    }

    @Override
    public void validateInstance()
    {
        if (instance.getTextValue().length() < minLength)
            report.addMessage("string is shorter than minLength");
    }
}