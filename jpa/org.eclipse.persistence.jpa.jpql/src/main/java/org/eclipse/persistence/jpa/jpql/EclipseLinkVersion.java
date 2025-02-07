/*
 * Copyright (c) 2012, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Oracle - initial API and implementation
//     04/21/2022: Tomas Kraus
//       - Issue 1474: Update JPQL Grammar for Jakarta Persistence 2.2, 3.0 and 3.1
//     06/02/2023: Radek Felcman
//       - Issue 1885: Implement new JPQLGrammar for upcoming Jakarta Persistence 3.2
package org.eclipse.persistence.jpa.jpql;

/**
 * An enumeration listing the various releases of EclipseLink.
 *
 * @author Pascal Filion
 */
public enum EclipseLinkVersion {

    /**
     * A constant that points to the current release of EclipseLink, which is 4.1.
     */
    DEFAULT_VERSION(4.1),

    /**
     * The constant for the EclipseLink 1.x release.
     */
    VERSION_1_x(1.0),

    /**
     * The constant for the EclipseLink 2.0 release.
     */
    VERSION_2_0(2.0),

    /**
     * The constant for the EclipseLink 2.1 release.
     */
    VERSION_2_1(2.1),

    /**
     * The constant for the EclipseLink 2.2 release.
     */
    VERSION_2_2(2.2),

    /**
     * The constant for the EclipseLink 2.3 release.
     */
    VERSION_2_3(2.3),

    /**
     * The constant for the EclipseLink 2.4 release.
     */
    VERSION_2_4(2.4),

    /**
     * The constant for the EclipseLink 2.5 release.
     */
    VERSION_2_5(2.5),

    /**
     * The constant for the EclipseLink 2.6 release.
     */
    VERSION_2_6(2.6),

    /**
     * The constant for the EclipseLink 3.0 release.
     */
    VERSION_3_0(3.0),

    /**
     * The constant for the EclipseLink 4.0 release.
     */
    VERSION_4_0(4.0),

    /**
     * The constant for the EclipseLink 4.1 release.
     */
    VERSION_4_1(4.1);

    /**
     * The real version number.
     */
    private final double version;

    /**
     * Creates a new <code>EclipseLinkVersion</code>.
     *
     * @param version The actual version number
     */
    private EclipseLinkVersion(double version) {
        this.version = version;
    }

    /**
     * Retrieves the enumeration constant for the given value. If the value is not known, then
     * {@link #DEFAULT_VERSION} will be returned.
     *
     * @param value The value to retrieve its constant version
     * @return The constant version of the given value
     */
    public static EclipseLinkVersion value(String value) {

        for (EclipseLinkVersion version : versions()) {
            if (version.getVersion().equals(value)) {
                return version;
            }
        }

        return DEFAULT_VERSION;
    }

    /**
     * Returns the list of {@link EclipseLinkVersion} excluding {@link #DEFAULT_VERSION}.
     *
     * @return The list of unique constants
     */
    public static EclipseLinkVersion[] versions() {
        EclipseLinkVersion[] values = new EclipseLinkVersion[10];
        values[0] = VERSION_1_x;
        values[1] = VERSION_2_0;
        values[2] = VERSION_2_1;
        values[3] = VERSION_2_2;
        values[4] = VERSION_2_3;
        values[5] = VERSION_2_4;
        values[6] = VERSION_2_5;
        values[7] = VERSION_3_0;
        values[8] = VERSION_4_0;
        values[9] = VERSION_4_1;
        return values;
    }

    /**
     * Returns the real version this constant represents.
     *
     * @return The string value of the version
     */
    public String getVersion() {
        return String.valueOf(version);
    }

    /**
     * Determines whether this constant represents a version that is newer than the given version.
     *
     * @param version The constant to verify if it's representing a version that is older than this one
     * @return <code>true</code> if this constant represents a newer version and the given constant
     * represents a version that is older; <code>false</code> if the given constant represents a
     * newer and this constant represents an older version
     */
    public boolean isNewerThan(EclipseLinkVersion version) {
        return this.version > version.version;
    }

    /**
     * Determines whether this constant represents a version that is newer than the given version or
     * if it's the same version.
     *
     * @param version The constant to verify if it's representing a version that is older than this
     * one or if it's the same than this one
     * @return <code>true</code> if this constant represents a newer version and the given constant
     * represents a version that is older or if it's the same constant; <code>false</code> if the
     * given constant represents a newer and this constant represents an older version
     */
    public boolean isNewerThanOrEqual(EclipseLinkVersion version) {
        return this.version >= version.version;
    }

    /**
     * Determines whether this constant represents a version that is older than the given version.
     *
     * @param version The constant to verify if it's representing a version that is more recent than
     * this one
     * @return <code>true</code> if this constant represents an earlier version and the given
     * constant represents a version that is more recent; <code>false</code> if the given constant
     * represents an earlier version and this constant represents a more recent version
     */
    public boolean isOlderThan(EclipseLinkVersion version) {
        return this.version < version.version;
    }

    /**
     * Determines whether this constant represents a version that is older than the given version or
     * if it's the same version.
     *
     * @param version The constant to verify if it's representing a version that is more recent than
     * this one or if it's the same than this one
     * @return <code>true</code> if this constant represents an earlier version and the given
     * constant represents a version that is more recent or if it's the same constant; <code>false</code>
     * if the given constant represents an earlier version and this constant represents a more recent
     * version
     */
    public boolean isOlderThanOrEqual(EclipseLinkVersion version) {
        return this.version <= version.version;
    }

    /**
     * Converts the current constant to one of the known versions, this means if the constant is
     * {@link #DEFAULT_VERSION}, then it will be converted into the actual constant representing that
     * version.
     *
     * @return Either this same constant or the actual version constant
     */
    public EclipseLinkVersion toCurrentVersion() {

        if (this == DEFAULT_VERSION) {

            String currentVersion = getVersion();

            for (EclipseLinkVersion version : versions()) {
                if (currentVersion.equals(String.valueOf(version.version))) {
                    return version;
                }
            }
        }

        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(version);
    }
}
