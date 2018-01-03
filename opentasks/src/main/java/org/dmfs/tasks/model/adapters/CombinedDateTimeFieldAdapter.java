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

package org.dmfs.tasks.model.adapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.dmfs.opentaskspal.readdata.cursor.CursorTaskDateTime;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;
import org.dmfs.tasks.readdata.ContentSetTaskDateTime;


/**
 * Knows how to load and store combined date-time values as {@link DateTime} from/to {@link Cursor} and {@link ContentSet}.
 * <p>
 * Combined date-time values are stored as three values:
 * <ul>
 * <li>a timestamp in milliseconds since the epoch</li>
 * <li>a time zone</li>
 * <li>an allday flag</li>
 * </ul>
 * <p>
 *
 * @author Gabor Keszthelyi
 */
public final class CombinedDateTimeFieldAdapter extends FieldAdapter<DateTime>
{
    private final String mTimestampField;
    private final String mTzField;
    private final String mAllDayField;


    /**
     * Constructor for a new {@link CombinedDateTimeFieldAdapter}.
     *
     * @param timestampField
     *         The name of the field that holds the time stamp in milliseconds.
     * @param tzField
     *         The name of the field that holds the time zone (as Olson ID).
     * @param alldayField
     *         The name of the field that indicated that this time is a date not a date-time.
     */
    public CombinedDateTimeFieldAdapter(@NonNull String timestampField, @NonNull String tzField, @NonNull String alldayField)
    {
        mTimestampField = timestampField;
        mTzField = tzField;
        mAllDayField = alldayField;
    }


    @Override
    public DateTime get(ContentSet values)
    {
        return new ContentSetTaskDateTime(values, mTimestampField, mTzField, mAllDayField).value(null);
    }


    @Override
    public DateTime get(Cursor cursor)
    {
        return new CursorTaskDateTime(cursor, mTimestampField, mTzField, mAllDayField).value(null);
    }


    @Override
    public DateTime getDefault(ContentSet values)
    {
        // TODO What are we supposed to use here?
        throw new UnsupportedOperationException("TODO");
    }


    @Override
    public void set(ContentSet values, DateTime value)
    {
        values.startBulkUpdate();
        try
        {
            if (value != null)
            {
                values.put(mTimestampField, value.getTimestamp());
                values.put(mTzField, value.isAllDay() ? null : value.getTimeZone().getID());
                values.put(mAllDayField, value.isAllDay() ? 1 : 0);
            }
            else
            {
                // write timestamp only, other fields may still use allday and timezone
                values.put(mTimestampField, (Long) null);
            }
        }
        finally
        {
            values.finishBulkUpdate();
        }
    }


    @Override
    public void set(ContentValues values, DateTime value)
    {
        if (value != null)
        {
            // just store all three parts separately
            values.put(mTimestampField, value.getTimestamp());
            values.put(mTzField, value.isAllDay() ? null : value.getTimeZone().getID());
            values.put(mAllDayField, value.isAllDay() ? 1 : 0);
        }
        else
        {
            // write timestamp only, other fields may still use allday and timezone
            values.put(mTimestampField, (Long) null);
        }
    }


    @Override
    public void registerListener(ContentSet values, OnContentChangeListener listener, boolean initalNotification)
    {
        values.addOnChangeListener(listener, mTimestampField, initalNotification);
        values.addOnChangeListener(listener, mTzField, initalNotification);
        values.addOnChangeListener(listener, mAllDayField, initalNotification);
    }


    @Override
    public void unregisterListener(ContentSet values, OnContentChangeListener listener)
    {
        values.removeOnChangeListener(listener, mTimestampField);
        values.removeOnChangeListener(listener, mTzField);
        values.removeOnChangeListener(listener, mAllDayField);
    }
}
