/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package indy

import org.junit.Test

import static groovy.test.GroovyAssert.assertScript

final class IndyUsageTest {
    @Test
    void testIndyIsUsedNested() {
        assertScript '''
            def foo() {
                 throw new Exception('blah')
            }
            try {
                foo()
            } catch (e) {
                assert e.stackTrace.find { it.className == 'org.codehaus.groovy.vmplugin.v8.IndyInterface' }
            }
        '''
    }

    @Test
    void testMethodWithSingletonParamType() {
        assertScript '''
            class Singleton {
                private Singleton() {}
                public static final Singleton INSTANCE = new Singleton()
            } 
            def foo(Singleton p) {
                return p
            }
            assert Singleton.INSTANCE === foo(Singleton.INSTANCE)
        '''
    }
}
