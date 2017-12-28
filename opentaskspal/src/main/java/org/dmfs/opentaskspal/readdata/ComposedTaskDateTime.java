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

import org.dmfs.jems.optional.decorators.Mapped;
import org.dmfs.jems.single.Single;
import org.dmfs.optional.Optional;
import org.dmfs.optional.decorators.DelegatingOptional;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.tasks.contract.TaskContract;

import java.util.TimeZone;


/**
 * {@link DateTime} value for a task composed from the 3 related fields' (timestamp, timezone, all-day) values
 * interpreted according to {@link TaskContract}.
 *
 * @author Gabor Keszthelyi
 */
// TODO Consider creating SimpleTaskDateTime, SimpleCursorTaskDateTime, etc, for the completed, created
public final class ComposedTaskDateTime extends DelegatingOptional<DateTime>
{

    public ComposedTaskDateTime(Optional<Long> timestamp, Optional<TimeZone> timeZone, Single<Boolean> isAllDay)
    {
        super(new Mapped<>(
                ts -> isAllDay.value() ?
                        new DateTime(ts).toAllDay()
                        :
                        // new DateTime(null, ts) creates a floating date-time
                        new DateTime(timeZone.value(null), ts),
                timestamp));
    }

}
