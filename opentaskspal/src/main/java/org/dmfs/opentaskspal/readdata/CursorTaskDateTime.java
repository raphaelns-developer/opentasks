/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.opentaskspal.readdata;

import android.database.Cursor;
import android.support.annotation.NonNull;

import org.dmfs.jems.optional.decorators.Mapped;
import org.dmfs.optional.NullSafe;
import org.dmfs.optional.decorators.DelegatingOptional;
import org.dmfs.rfc5545.DateTime;

import java.util.TimeZone;


/**
 * {@link DateTime} value for a task field, taken from a {@link Cursor}.
 *
 * @author Gabor Keszthelyi
 */
public final class CursorTaskDateTime extends DelegatingOptional<DateTime>
{
    public CursorTaskDateTime(@NonNull Cursor cursor,
                              @NonNull String timestampField,
                              @NonNull String tzField,
                              @NonNull String alldayField)
    {
        super(new ComposedTaskDateTime(
                // TODO Checking for 0 is not good, use cursor.isNull() instead
                new Conditional<>((Long ts) -> ts > 0, cursor.getLong(cursor.getColumnIndexOrThrow(timestampField))),
                new Mapped<>(TimeZone::getTimeZone, new NullSafe<>(cursor.getString(cursor.getColumnIndexOrThrow(tzField)))),
                () -> new Mapped<>("1"::equals, new NullSafe<>(cursor.getString(cursor.getColumnIndexOrThrow(alldayField))))
                        .value(false)
        ));
    }
}
