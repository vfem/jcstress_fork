/*
 * Copyright (c) 2014, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jcstress.samples.api;


import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IIZ_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.FORBIDDEN;

//mvn clean verify -pl jcstress-samples -am
//java -jar jcstress-samples/target/jcstress.jar -t Test_Request_Report -v
@JCStressTest
@Outcome(id = "0, 0, false", expect = ACCEPTABLE, desc = "Trivial")
@Outcome(id = "0, 1, false", expect = ACCEPTABLE, desc = "Trivial")
@Outcome(id = "1, 0, false", expect = ACCEPTABLE, desc = "Trivial")
@Outcome(id = "1, 1, true", expect = ACCEPTABLE, desc = "Trivial")
@Outcome(id = "1, 1, false", expect = FORBIDDEN, desc = "Trivial")
public class Test_Request_Report {

    @Actor
    public void processReq(MyState s) {
        Integer localReq = 1;

        if (s.req == 0) {
            s.req = localReq;
        }

        Integer localRep = s.rep;

        if (localRep == 1 && localReq == 1) {
            s.processed = true;
        }

    }

    @Actor
    public void processRep(MyState s) {
        Integer localRes = 1;
//        s.rep = localRes;

        Integer localReq = s.req;

        s.rep = localRes;

        if (localRes == 1 && localReq == 1) {
            s.processed = true;
        }
    }

    @Arbiter
    public void arb(MyState s, IIZ_Result r) {
        r.r1 = s.req;
        r.r2 = s.rep;
        r.r3 = s.processed;

    }


    @State
    public static class MyState {
        public volatile Integer req = 0;
        public volatile Integer rep = 0;
        public volatile Boolean processed = false;
    }

//    @Result
//    public static class Res {
//        public volatile Integer req = 0;
//        public volatile Integer rep = 0;
//        public volatile Boolean processed = false;
//    }

}
