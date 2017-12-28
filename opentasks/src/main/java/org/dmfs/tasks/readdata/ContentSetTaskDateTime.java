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

package org.dmfs.tasks.readdata;

import android.support.annotation.NonNull;

import org.dmfs.jems.optional.decorators.Mapped;
import org.dmfs.opentaskspal.readdata.ComposedTaskDateTime;
import org.dmfs.optional.NullSafe;
import org.dmfs.optional.decorators.DelegatingOptional;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.tasks.model.ContentSet;

import java.util.TimeZone;


/**
 * {@link DateTime} of a task field in a {@link ContentSet}.
 *
 * @author Gabor Keszthelyi
 */
public final class ContentSetTaskDateTime extends DelegatingOptional<DateTime>
{

    public ContentSetTaskDateTime(
            @NonNull ContentSet contentSet,
            @NonNull String timestampField,
            @NonNull String tzField,
            @NonNull String alldayField)
    {
        super(new ComposedTaskDateTime(
                new NullSafe<>(contentSet.getAsLong(timestampField)),
                new Mapped<>(TimeZone::getTimeZone, new NullSafe<>(contentSet.getAsString(tzField))),
                () -> new Mapped<>("1"::equals, new NullSafe<>(contentSet.getAsString(alldayField))).value(false)));
    }
}
