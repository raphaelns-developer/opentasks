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

import org.dmfs.jems.predicate.Predicate;
import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.ValueSingle;
import org.dmfs.optional.Optional;

import java.util.NoSuchElementException;


/**
 * @author Gabor Keszthelyi
 */
// TODO Move to jems
public final class Conditional<T> implements Optional<T>
{
    private final Single<T> mTarget;
    private final Predicate<T> mPredicate;


    public Conditional(Predicate<T> predicate, Single<T> target)
    {
        mTarget = target;
        mPredicate = predicate;
    }


    public Conditional(Predicate<T> predicate, T target)
    {
        this(predicate, new ValueSingle<>(target));
    }


    @Override
    public boolean isPresent()
    {
        return mPredicate.satisfiedBy(mTarget.value());
    }


    @Override
    public T value(T defaultValue)
    {
        return isPresent() ? value() : defaultValue;
    }


    @Override
    public T value() throws NoSuchElementException
    {
        if (mPredicate.satisfiedBy(mTarget.value()))
        {
            return mTarget.value();
        }
        throw new NoSuchElementException(
                String.format("Value '%s' doesn't satisfy the predicate '%s'", mTarget.value(), mPredicate));
    }
}
