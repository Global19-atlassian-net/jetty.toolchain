//
//  ========================================================================
//  Copyright (c) 1995-2012 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package javax.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This method level annotation can be used to make a Java method receive
 * incoming web socket messages. Each websocket endpoint may only have one
 * message handling method for each of the native websocket message formats:
 * text, binary and pong. Methods using this annotation are allowed to have
 * parameters of types described below, otherwise the container will generate an
 * error at deployment time. <br>
 * The allowed parameters are:
 * <ol>
 * <li>Exactly one of any of the following choices</li>
 * <ul>
 * if the method is handling text messages:
 * <li> {@link java.lang.String} to receive the whole message</li>
 * <li>Java primitive or class equivalent to receive the whole message converted
 * to that type</li>
 * <li>String and boolean pair to receive the message in parts</li>
 * <li> {@link java.io.Reader} to receive the whole message as a blocking stream</li>
 * <li>any object parameter for which the endpoint has a text decoder (
 * {@link Decoder.Text} or {@link Decoder.TextStream}).<br/>
 * if the method is handling binary messages:
 * <li>byte[] or {@link java.nio.ByteBuffer} to receive the whole message</li>
 * <li>byte[] and boolean pair, or {@link java.nio.ByteBuffer} and boolean pair
 * to receive the message in parts</li>
 * <li> {@link java.io.InputStream} to receive the whole message as a blocking
 * stream</li>
 * <li>any object parameter for which the endpoint has a binary decoder (
 * {@link Decoder.Binary} or {@link Decoder.BinaryStream}).</li>
 * if the method is handling pong messages:
 * <li> {@link PongMessage} for handling pong messages</li>
 * </ul>
 * <li>and Zero to n String or Java primitive parameters annotated with the
 * {@link javax.websocket.server.WebSocketPathParam} annotation for server
 * endpoints.</li>
 * <li>and an optional {@link Session} parameter</li>
 * </ol>
 * <p/>
 * The parameters may be listed in any order.<br>
 * <br>
 * The method may have a non-void return type, in which case the web socket
 * runtime must interpret this as a web socket message to return to the peer.
 * The allowed data types for this return type, other than void, are String,
 * ByteBuffer, byte[], any Java primitive or class equivalent, and anything for
 * which there is an encoder. If the method uses a Java primitive as a return
 * value, the implementation must construct the text message to send using the
 * standard Java string representation of the Java primitive. If the method uses
 * a class equivalent of a Java primitive as a return value, the implementation
 * must construct the text message from the Java primitive equivalent as
 * described above.
 * 
 * </br>Developers should note that if developer closes the session during the
 * invocation of a method with a return type, the method will complete but the
 * return value will not be delivered to the remote endpoint. The send failure
 * will be passed back into the endpoint's error handling method.<br>
 * <br>
 * <p/>
 * For example:
 * 
 * <code>
 * <pre>
 * &nbsp;@WebSocketMessage
 * public void processGreeting(String message, Session session) {
 * &nbsp;&nbsp;System.out.println("Greeting received:" + message);
 * }
 * </pre>
 * </code>
 * 
 * For example:
 * 
 * <code>
 * <pre>
 * &nbsp;@WebSocketMessage
 * public void processUpload(byte[] b, boolean last, Session session) {
 * &nbsp;&nbsp;// process partial data here, which check on last to see if these is more on the way
 * }
 * </pre>
 * </code>
 * 
 * Developers should not continue to reference message objects of type
 * {@link java.io.Reader}, {@link java.nio.ByteBuffer} or
 * {@link java.io.InputStream} after the annotated method has completed, since
 * they may be recycled by the implementation.
 * 
 * @since Draft 002
 * @see DRAFT 012
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebSocketMessage {
    /**
     * Specifies the maximum size of message in bytes that the method this
     * annotates will be able to process, or -1 to indicate that there is no
     * maximum. The default is -1.
     * 
     * @return the maximum size in bytes
     */
    public long maxMessageSize() default -1L;

}
