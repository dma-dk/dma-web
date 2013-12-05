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
package dk.dma.commons.web.rest.defaults;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import dk.dma.commons.util.JSONObject;

/**
 * If the system fails to find this message body writer. Make sure the annotated method using this writer has this
 * annotation <code>@Produces("application//json")</code>
 * 
 * @author Kasper Nielsen
 */
@Produces("application/json")
@Provider
public class JSONObjectMessageBodyWriter implements MessageBodyWriter<JSONObject> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == JSONObject.class;
    }

    @Override
    public long getSize(JSONObject myBean, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType) {
        return -1; // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
    }

    @Override
    public void writeTo(JSONObject myBean, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException {
        new PrintWriter(entityStream).append(myBean.toString()).flush();
    }
}
