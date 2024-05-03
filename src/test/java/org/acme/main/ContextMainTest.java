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
        assertDoesNotThrow(() -> contextMain.run("LSBmcm9tOgogICAgdXJpOiAiZGlyZWN0Om15LXJvdXRlIgogICAgc3RlcHM6CiAgICAgIC0gbG9nOgogICAgICAgICAgbWVzc2FnZTogIkxvZ2dpbmcgZnJvbSBteS1yb3V0ZS4uLiI="));
    }
}
