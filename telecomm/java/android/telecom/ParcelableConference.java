/*
 * Copyright 2014, The Android Open Source Project
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

package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.telecom.IVideoProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A parcelable representation of a conference connection.
 * @hide
 */
public final class ParcelableConference implements Parcelable {

    private PhoneAccountHandle mPhoneAccount;
    private int mState;
    private int mConnectionCapabilities;
    private int mConnectionProperties;
    private List<String> mConnectionIds;
    private long mConnectTimeMillis = Conference.CONNECT_TIME_NOT_SPECIFIED;
    private final IVideoProvider mVideoProvider;
    private final int mVideoState;
    private StatusHints mStatusHints;
    private Bundle mExtras;
    private long mConnectElapsedTimeMillis = Conference.CONNECT_TIME_NOT_SPECIFIED;
    private final Uri mAddress;
    private final int mAddressPresentation;
    private final String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;

    public ParcelableConference(
            PhoneAccountHandle phoneAccount,
            int state,
            int connectionCapabilities,
            int connectionProperties,
            List<String> connectionIds,
            IVideoProvider videoProvider,
            int videoState,
            long connectTimeMillis,
            long connectElapsedTimeMillis,
            StatusHints statusHints,
            Bundle extras,
            Uri address,
            int addressPresentation,
            String callerDisplayName,
            int callerDisplayNamePresentation) {
        mPhoneAccount = phoneAccount;
        mState = state;
        mConnectionCapabilities = connectionCapabilities;
        mConnectionProperties = connectionProperties;
        mConnectionIds = connectionIds;
        mVideoProvider = videoProvider;
        mVideoState = videoState;
        mConnectTimeMillis = connectTimeMillis;
        mStatusHints = statusHints;
        mExtras = extras;
        mConnectElapsedTimeMillis = connectElapsedTimeMillis;
        mAddress = address;
        mAddressPresentation = addressPresentation;
        mCallerDisplayName = callerDisplayName;
        mCallerDisplayNamePresentation = callerDisplayNamePresentation;
    }

    @Override
    public String toString() {
        return (new StringBuffer())
                .append("account: ")
                .append(mPhoneAccount)
                .append(", state: ")
                .append(Connection.stateToString(mState))
                .append(", capabilities: ")
                .append(Connection.capabilitiesToString(mConnectionCapabilities))
                .append(", properties: ")
                .append(Connection.propertiesToString(mConnectionProperties))
                .append(", connectTime: ")
                .append(mConnectTimeMillis)
                .append(", children: ")
                .append(mConnectionIds)
                .append(", VideoState: ")
                .append(mVideoState)
                .append(", VideoProvider: ")
                .append(mVideoProvider)
                .toString();
    }

    public PhoneAccountHandle getPhoneAccount() {
        return mPhoneAccount;
    }

    public int getState() {
        return mState;
    }

    public int getConnectionCapabilities() {
        return mConnectionCapabilities;
    }

    public int getConnectionProperties() {
        return mConnectionProperties;
    }

    public List<String> getConnectionIds() {
        return mConnectionIds;
    }

    public long getConnectTimeMillis() {
        return mConnectTimeMillis;
    }

    public long getConnectElapsedTimeMillis() {
        return mConnectElapsedTimeMillis;
    }

    public IVideoProvider getVideoProvider() {
        return mVideoProvider;
    }

    public int getVideoState() {
        return mVideoState;
    }

    public StatusHints getStatusHints() {
        return mStatusHints;
    }

    public Bundle getExtras() {
        return mExtras;
    }

    public Uri getHandle() {
        return mAddress;
    }

    public int getHandlePresentation() {
        return mAddressPresentation;
    }

    public String getCallerDisplayName() {
        return mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return mCallerDisplayNamePresentation;
    }

    public static final @android.annotation.NonNull Parcelable.Creator<ParcelableConference> CREATOR =
            new Parcelable.Creator<ParcelableConference> () {
        @Override
        public ParcelableConference createFromParcel(Parcel source) {
            ClassLoader classLoader = ParcelableConference.class.getClassLoader();
            PhoneAccountHandle phoneAccount = source.readParcelable(classLoader);
            int state = source.readInt();
            int capabilities = source.readInt();
            List<String> connectionIds = new ArrayList<>(2);
            source.readList(connectionIds, classLoader);
            long connectTimeMillis = source.readLong();
            IVideoProvider videoCallProvider =
                    IVideoProvider.Stub.asInterface(source.readStrongBinder());
            int videoState = source.readInt();
            StatusHints statusHints = source.readParcelable(classLoader);
            Bundle extras = source.readBundle(classLoader);
            int properties = source.readInt();
            long connectElapsedTimeMillis = source.readLong();
            Uri address = source.readParcelable(classLoader);
            int addressPresentation = source.readInt();
            String callerDisplayName = source.readString();
            int callerDisplayNamePresentation = source.readInt();

            return new ParcelableConference(phoneAccount, state, capabilities, properties,
                    connectionIds, videoCallProvider, videoState, connectTimeMillis,
                    connectElapsedTimeMillis, statusHints, extras, address, addressPresentation,
                    callerDisplayName, callerDisplayNamePresentation);
        }

        @Override
        public ParcelableConference[] newArray(int size) {
            return new ParcelableConference[size];
        }
    };

    /** {@inheritDoc} */
    @Override
    public int describeContents() {
        return 0;
    }

    /** Writes ParcelableConference object into a Parcel. */
    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeParcelable(mPhoneAccount, 0);
        destination.writeInt(mState);
        destination.writeInt(mConnectionCapabilities);
        destination.writeList(mConnectionIds);
        destination.writeLong(mConnectTimeMillis);
        destination.writeStrongBinder(
                mVideoProvider != null ? mVideoProvider.asBinder() : null);
        destination.writeInt(mVideoState);
        destination.writeParcelable(mStatusHints, 0);
        destination.writeBundle(mExtras);
        destination.writeInt(mConnectionProperties);
        destination.writeLong(mConnectElapsedTimeMillis);
        destination.writeParcelable(mAddress, 0);
        destination.writeInt(mAddressPresentation);
        destination.writeString(mCallerDisplayName);
        destination.writeInt(mCallerDisplayNamePresentation);
    }
}
