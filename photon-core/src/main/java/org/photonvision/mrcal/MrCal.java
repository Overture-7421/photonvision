/*
 * Copyright (C) Photon Vision.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.photonvision.mrcal;

import java.io.IOException;
import java.util.List;
import org.photonvision.common.hardware.Platform;
import org.photonvision.common.util.TestUtils;
import org.photonvision.jni.PhotonJniCommon;

public class MrCal extends PhotonJniCommon {
    public static synchronized void forceLoad() throws IOException {
        // Force load opencv
        TestUtils.loadLibraries();

        // Library naming is dumb and has "lib" appended for Windows when it ought not to
        if (Platform.isWindows()) {
            // Order is correct to match dependencies of libraries
            forceLoad(
                    MrCal.class,
                    List.of(
                            "libamd",
                            "libcamd",
                            "libcolamd",
                            "libccolamd",
                            "openblas",
                            "libgcc_s_seh-1",
                            "libgfortran-5",
                            "liblapack",
                            "libcholmod",
                            "mrcal_jni"));
        } else {
            // Nothing else to do on linux
            forceLoad(MrCal.class, List.of("mrcal_jni"));
        }

        if (!MrCal.isWorking()) {
            throw new IOException("Can't load mrcal?");
        }
    }
}
