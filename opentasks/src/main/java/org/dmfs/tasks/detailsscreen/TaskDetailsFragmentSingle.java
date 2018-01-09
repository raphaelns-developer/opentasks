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

package org.dmfs.tasks.detailsscreen;

import android.net.Uri;
import android.support.v4.app.Fragment;

import org.dmfs.android.bolts.color.Color;
import org.dmfs.jems.single.Single;
import org.dmfs.optional.Optional;
import org.dmfs.tasks.EmptyTaskFragment;
import org.dmfs.tasks.ViewTaskFragment;

import gk.android.investigator.Investigator;

import static org.dmfs.tasks.LogUtil.desc;
import static org.dmfs.tasks.LogUtil.descOptColor;


/**
 * {@link Single} for a {@link Fragment} that can show the task details for a given optional task content uri.
 * (Empty fragment is used if it is absent.)
 *
 * @author Gabor Keszthelyi
 */
public final class TaskDetailsFragmentSingle implements Single<Fragment>
{
    private final Optional<Uri> mTaskUri;
    private final Optional<Color> mColorHint;


    /**
     * @param taskUri
     *         the content uri of the task
     * @param colorHint
     *         the color to be used by the created Fragment for the toolbars until the task details load
     */
    public TaskDetailsFragmentSingle(Optional<Uri> taskUri, Optional<Color> colorHint)
    {
        mTaskUri = taskUri;
        mColorHint = colorHint;
    }


    @Override
    public Fragment value()
    {
        Investigator.log(this, "taskUri", desc(mTaskUri), "color", descOptColor(mColorHint));
        return mTaskUri.isPresent() ?
                ViewTaskFragment.newInstance(mTaskUri.value(), mColorHint)
                :
                EmptyTaskFragment.newInstance(mColorHint);
    }
}
