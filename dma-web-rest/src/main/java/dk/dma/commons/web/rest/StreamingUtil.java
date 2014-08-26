/* Copyright (c) 2011 Danish Maritime Authority.
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

import static java.util.Objects.requireNonNull;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import dk.dma.commons.util.io.OutputStreamSink;

/**
 * 
 * @author Kasper Nielsen
 */
public class StreamingUtil {

    /**
     * Creates a streaming output from the specified iterable and output stream sink.
     * 
     * @param i
     *            the iterable to stream
     * @param sink
     *            the sink to stream to
     * @return
     */
    public static <T> StreamingOutput createStreamingOutput(final Iterable<T> i, final OutputStreamSink<T> sink) {
        requireNonNull(i);
        requireNonNull(sink);
        return new StreamingOutput() {
            @Override
            public void write(OutputStream paramOutputStream) throws IOException {
                try {
                    try (BufferedOutputStream bos = new BufferedOutputStream(paramOutputStream);) {
                        sink.writeAll(i, bos);
                    }
                    paramOutputStream.close();
                } catch (RuntimeException | Error e) {
                    throw e;
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            }
        };
    }

    /**
     * Creates a zipped streaming output from the specified iterable and output stream sink.
     *
     * @param i
     *            the iterable to stream
     * @param sink
     *            the sink to process the iterables to produce output for the stream
     * @param zipEntryName
     *            the name of the single ZipEntry carried in the zipped output stream (like a filename in a zip file)
     * @return
     */
    public static <T> StreamingOutput createZippedStreamingOutput(final Iterable<T> i, final OutputStreamSink<T> sink, final String zipEntryName) {
        requireNonNull(i);
        requireNonNull(sink);
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException {
                try {
                    try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outputStream))) { // http://goo.gl/UFb41j
                        zos.putNextEntry(new ZipEntry(zipEntryName));
                        sink.writeAll(i, zos);
                        zos.closeEntry();
                    }
                    outputStream.close();
                } catch (RuntimeException | Error e) {
                    throw e;
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            }
        };
    }

    /**
     * Creates a streaming output from the specified iterable and output stream sink.
     * 
     * @param i
     *            the iterable to stream
     * @param sink
     *            the sink to stream to
     * @return
     */
    public static <T> StreamingOutput createStreamingOutput(final Iterable<T> i, final OutputStreamSink<T> sink,
            final Future<?> future) {
        requireNonNull(i);
        requireNonNull(sink);
        requireNonNull(future);
        return new StreamingOutput() {
            @Override
            public void write(OutputStream paramOutputStream) throws IOException {
                try {
                    try (BufferedOutputStream bos = new BufferedOutputStream(paramOutputStream);) {
                        sink.writeAll(i, bos);
                    }
                    paramOutputStream.close();
                } catch (RuntimeException | Error e) {
                    future.cancel(false);
                    throw e;
                } catch (Exception e) {
                    future.cancel(false);
                    throw new WebApplicationException(e);
                }
            }
        };
    }
}
