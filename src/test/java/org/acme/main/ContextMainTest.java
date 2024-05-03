/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme.main;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;


@QuarkusTest
public class ContextMainTest {

    @Inject
    ContextMain contextMain;

    @Test
    public void testContextMain() throws Exception {
        assertDoesNotThrow(() -> contextMain.run("LSBmcm9tOgogICAgdXJpOiAicXVhcnR6Oi8vaXBhYXMvbXktdHJpZ2dlcj9jcm9uPSorKisqKyorKis/IgogICAgc3RlcHM6CiAgICAgIC0gc2V0UHJvcGVydHk6CiAgICAgICAgICBuYW1lOiAiaXBhYXMuc3RhcnRUaW1lIgogICAgICAgICAgZXhwcmVzc2lvbjoKICAgICAgICAgICAgZ3Jvb3Z5OiAiamF2YS50aW1lLkluc3RhbnQubm93KCkiCiAgICAgIC0gbG9nOgogICAgICAgICAgbWVzc2FnZTogJz4+Pj4+IEV4ZWN1dGFuZG8gaW50ZWdyYWNhbycKICAgICAgLSB0bzoKICAgICAgICAgIHVyaTogIm1vY2s6cmVzdWx0IgogICAgICAtIHRvOgogICAgICAgICAgdXJpOiAiZGlyZWN0Om15LXJvdXRlIgotIGZyb206CiAgICB1cmk6ICJkaXJlY3Q6bXktcm91dGUiCiAgICBzdGVwczoKICAgICAgLSBsb2c6CiAgICAgICAgICBtZXNzYWdlOiAiTG9nZ2luZyBmcm9tIG15LXJvdXRlLi4uIg=="));
    }
}
