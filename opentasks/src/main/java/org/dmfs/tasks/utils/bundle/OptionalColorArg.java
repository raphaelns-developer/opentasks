/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.tasks.utils.bundle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.dmfs.android.bolts.color.Color;
import org.dmfs.android.bolts.color.elementary.ValueColor;
import org.dmfs.optional.Absent;
import org.dmfs.optional.NullSafe;
import org.dmfs.optional.Present;
import org.dmfs.optional.decorators.DelegatingOptional;


/**
 * An optional {@link Color} argument in a {@link Bundle}.
 *
 * @author Gabor Keszthelyi
 */
// TODO Remove solution from boxed-bolts library when that is available
public final class OptionalColorArg extends DelegatingOptional<Color>
{
    public OptionalColorArg(@NonNull String key, @Nullable Bundle bundle)
    {
        // TODO revisit
        super(new NullSafe<>(bundle).value(Bundle.EMPTY).getInt(key, -1) != -1 ? new Present<>(new ValueColor(bundle.getInt(key))) : Absent.absent());
    }
}
