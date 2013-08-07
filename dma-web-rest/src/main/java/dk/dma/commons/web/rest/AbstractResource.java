/*
 * Copyright (c) 2008 Kasper Nielsen.
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
package dk.dma.commons.web.rest;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;

/**
 * 
 * @author Kasper Nielsen
 */
public class AbstractResource {
    public static final String CONFIG = "dma-classes";

    @Context
    ServletConfig servletConfig;

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        Map<Class<?>, Object> m = (Map<Class<?>, Object>) servletConfig.getServletContext().getAttribute(CONFIG);
        T t = (T) m.get(type);
        if (t == null) {
            throw new UnsupportedOperationException();
        }
        return t;
    }

    public static void install(Object o) {

    }

    public static <T> void install(T o, Class<T> type) {

    }
}
