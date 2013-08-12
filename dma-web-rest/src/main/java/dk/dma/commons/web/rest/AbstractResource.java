/* Copyright (c) 2011 Danish Maritime Authority
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package dk.dma.commons.web.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;

/**
 * The base class of REST resources.
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

    public static Map<Class<?>, Object> create(Object... o) {
        Map<Class<?>, Object> m = new HashMap<>();
        for (Object oo : o) {
            if (oo != null) {
                m.put(oo.getClass(), oo);
            }
        }
        return m;
    }

    public static <T> void install(T o, Class<T> type) {

    }
}
