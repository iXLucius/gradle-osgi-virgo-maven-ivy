/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lucius.framework.config.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author lqzhai
 */
@SuppressWarnings({"rawtypes", "unchecked","unused"})
public class PropertiesConfigHandler {
    

    protected static final String ENCODING = "UTF-8";
    protected static final int TOKEN_NAME = 'N';
    protected static final int TOKEN_EQ = '=';
    protected static final int TOKEN_ARR_OPEN = '[';
    protected static final int TOKEN_ARR_CLOS = ']';
    protected static final int TOKEN_VEC_OPEN = '(';
    protected static final int TOKEN_VEC_CLOS = ')';
    protected static final int TOKEN_COMMA = ',';
    protected static final int TOKEN_VAL_OPEN = '"'; // '{';
    protected static final int TOKEN_VAL_CLOS = '"'; // '}';
    // simple types (string & primitive wrappers)
    protected static final int TOKEN_SIMPLE_STRING = 'T';
    protected static final int TOKEN_SIMPLE_INTEGER = 'I';
    protected static final int TOKEN_SIMPLE_LONG = 'L';
    protected static final int TOKEN_SIMPLE_FLOAT = 'F';
    protected static final int TOKEN_SIMPLE_DOUBLE = 'D';
    protected static final int TOKEN_SIMPLE_BYTE = 'X';
    protected static final int TOKEN_SIMPLE_SHORT = 'S';
    protected static final int TOKEN_SIMPLE_CHARACTER = 'C';
    protected static final int TOKEN_SIMPLE_BOOLEAN = 'B';
    // primitives
    protected static final int TOKEN_PRIMITIVE_INT = 'i';
    protected static final int TOKEN_PRIMITIVE_LONG = 'l';
    protected static final int TOKEN_PRIMITIVE_FLOAT = 'f';
    protected static final int TOKEN_PRIMITIVE_DOUBLE = 'd';
    protected static final int TOKEN_PRIMITIVE_BYTE = 'x';
    protected static final int TOKEN_PRIMITIVE_SHORT = 's';
    protected static final int TOKEN_PRIMITIVE_CHAR = 'c';
    protected static final int TOKEN_PRIMITIVE_BOOLEAN = 'b';
    protected static final String CRLF = "\r\n";
    protected static final Map code2Type;
    protected static final Map type2Code;
    // set of valid characters for "symblic-name"
    private static final BitSet NAME_CHARS;
    private static final BitSet TOKEN_CHARS;

    static {
        type2Code = new HashMap();

        // simple (exclusive String whose type code is not written)
        type2Code.put(Integer.class, new Integer(TOKEN_SIMPLE_INTEGER));
        type2Code.put(Long.class, new Integer(TOKEN_SIMPLE_LONG));
        type2Code.put(Float.class, new Integer(TOKEN_SIMPLE_FLOAT));
        type2Code.put(Double.class, new Integer(TOKEN_SIMPLE_DOUBLE));
        type2Code.put(Byte.class, new Integer(TOKEN_SIMPLE_BYTE));
        type2Code.put(Short.class, new Integer(TOKEN_SIMPLE_SHORT));
        type2Code.put(Character.class, new Integer(TOKEN_SIMPLE_CHARACTER));
        type2Code.put(Boolean.class, new Integer(TOKEN_SIMPLE_BOOLEAN));

        // primitives
        type2Code.put(Integer.TYPE, new Integer(TOKEN_PRIMITIVE_INT));
        type2Code.put(Long.TYPE, new Integer(TOKEN_PRIMITIVE_LONG));
        type2Code.put(Float.TYPE, new Integer(TOKEN_PRIMITIVE_FLOAT));
        type2Code.put(Double.TYPE, new Integer(TOKEN_PRIMITIVE_DOUBLE));
        type2Code.put(Byte.TYPE, new Integer(TOKEN_PRIMITIVE_BYTE));
        type2Code.put(Short.TYPE, new Integer(TOKEN_PRIMITIVE_SHORT));
        type2Code.put(Character.TYPE, new Integer(TOKEN_PRIMITIVE_CHAR));
        type2Code.put(Boolean.TYPE, new Integer(TOKEN_PRIMITIVE_BOOLEAN));

        // reverse map to map type codes to classes, string class mapping
        // to be added manually, as the string type code is not written and
        // hence not included in the type2Code map
        code2Type = new HashMap();
        for (Iterator ti = type2Code.entrySet().iterator(); ti.hasNext();) {
            Map.Entry entry = (Map.Entry) ti.next();
            code2Type.put(entry.getValue(), entry.getKey());
        }
        code2Type.put(new Integer(TOKEN_SIMPLE_STRING), String.class);

        NAME_CHARS = new BitSet();
        for (int i = '0'; i <= '9'; i++) {
            NAME_CHARS.set(i);
        }
        for (int i = 'a'; i <= 'z'; i++) {
            NAME_CHARS.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            NAME_CHARS.set(i);
        }
        NAME_CHARS.set('_');
        NAME_CHARS.set('-');
        NAME_CHARS.set('.');
        NAME_CHARS.set('\\');

        TOKEN_CHARS = new BitSet();
        TOKEN_CHARS.set(TOKEN_EQ);
        TOKEN_CHARS.set(TOKEN_ARR_OPEN);
        TOKEN_CHARS.set(TOKEN_ARR_CLOS);
        TOKEN_CHARS.set(TOKEN_VEC_OPEN);
        TOKEN_CHARS.set(TOKEN_VEC_CLOS);
        TOKEN_CHARS.set(TOKEN_COMMA);
        TOKEN_CHARS.set(TOKEN_VAL_OPEN);
        TOKEN_CHARS.set(TOKEN_VAL_CLOS);
        TOKEN_CHARS.set(TOKEN_SIMPLE_STRING);
        TOKEN_CHARS.set(TOKEN_SIMPLE_INTEGER);
        TOKEN_CHARS.set(TOKEN_SIMPLE_LONG);
        TOKEN_CHARS.set(TOKEN_SIMPLE_FLOAT);
        TOKEN_CHARS.set(TOKEN_SIMPLE_DOUBLE);
        TOKEN_CHARS.set(TOKEN_SIMPLE_BYTE);
        TOKEN_CHARS.set(TOKEN_SIMPLE_SHORT);
        TOKEN_CHARS.set(TOKEN_SIMPLE_CHARACTER);
        TOKEN_CHARS.set(TOKEN_SIMPLE_BOOLEAN);

        // primitives
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_INT);
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_LONG);
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_FLOAT);
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_DOUBLE);
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_BYTE);
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_SHORT);
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_CHAR);
        TOKEN_CHARS.set(TOKEN_PRIMITIVE_BOOLEAN);
    }

    /**
     * Writes the configuration data from the
     * <code>Dictionary</code> to the given
     * <code>OutputStream</code>.
     * <p>
     * This method writes at the current location in the stream and does not
     * close the outputstream.
     *
     * @param out The <code>OutputStream</code> to write the configurtion data
     * to.
     * @param properties The <code>Dictionary</code> to write.
     * @throws IOException If an error occurrs writing to the output stream.
     */
    public static void write(OutputStream out, Dictionary properties) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, ENCODING));

        for (Enumeration ce = properties.keys(); ce.hasMoreElements();) {
            String key = (String) ce.nextElement();

            // cfg = prop "=" value "." .
            bw.write(key);
            bw.write(TOKEN_EQ);
            bw.write(properties.get(key).toString());//writeValue(bw, properties.get(key));
            bw.write(CRLF);
        }

        bw.flush();
    }    
        /**
     * Reads configuration data from the given
     * <code>InputStream</code> and returns a new
     * <code>Dictionary</code> object containing the data.
     * <p>
     * This method reads from the current location in the stream upto the end of
     * the stream but does not close the stream at the end.
     *
     * @param ins The <code>InputStream</code> from which to read the
     * configuration data.
     * @return A <code>Dictionary</code> object containing the configuration
     * data. This object may be empty if the stream contains no configuration
     * data.
     * @throws IOException If an error occurrs reading from the stream. This
     * exception is also thrown if a syntax error is encountered.
     */
    public static Dictionary read(InputStream ins) throws IOException {
        Properties p = new Properties();
        p.load(ins);
        return p;
    }
    
    // ---------- Configuration Output Implementation --------------------------
    private static void writeValue(Writer out, Object value) throws IOException {
        Class clazz = value.getClass();
        if (clazz.isArray()) {
            writeArray(out, value);
        } else if (value instanceof Collection) {
            writeCollection(out, (Collection) value);
        } else {
            writeType(out, clazz);
            writeSimple(out, value);
        }
    }

    private static void writeArray(Writer out, Object arrayValue) throws IOException {
        int size = Array.getLength(arrayValue);
        writeType(out, arrayValue.getClass().getComponentType());
        out.write(TOKEN_ARR_OPEN);
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                out.write(TOKEN_COMMA);
            }
            writeSimple(out, Array.get(arrayValue, i));
        }
        out.write(TOKEN_ARR_CLOS);
    }

    private static void writeCollection(Writer out, Collection collection) throws IOException {
        if (collection.isEmpty()) {
            out.write(TOKEN_VEC_OPEN);
            out.write(TOKEN_VEC_CLOS);
        } else {
            Iterator ci = collection.iterator();
            Object firstElement = ci.next();

            writeType(out, firstElement.getClass());
            out.write(TOKEN_VEC_OPEN);
            writeSimple(out, firstElement);

            while (ci.hasNext()) {
                out.write(TOKEN_COMMA);
                writeSimple(out, ci.next());
            }
            out.write(TOKEN_VEC_CLOS);
        }
    }

    private static void writeType(Writer out, Class valueType) throws IOException {
        Integer code = (Integer) type2Code.get(valueType);
        if (code != null) {
            out.write((char) code.intValue());
        }
    }

    private static void writeSimple(Writer out, Object value) throws IOException {
        if (value instanceof Double) {
            double dVal = ((Double) value).doubleValue();
            value = new Long(Double.doubleToRawLongBits(dVal));
        } else if (value instanceof Float) {
            float fVal = ((Float) value).floatValue();
            value = new Integer(Float.floatToRawIntBits(fVal));
        }

        out.write(TOKEN_VAL_OPEN);
        writeQuoted(out, String.valueOf(value));
        out.write(TOKEN_VAL_CLOS);
    }

    private static void writeQuoted(Writer out, String simple) throws IOException {
        if (simple == null || simple.length() == 0) {
            return;
        }

        char c = 0;
        int len = simple.length();
        for (int i = 0; i < len; i++) {
            c = simple.charAt(i);
            switch (c) {
                case '\\':
                case TOKEN_VAL_CLOS:
                case ' ':
                case TOKEN_EQ:
                    out.write('\\');
                    out.write(c);
                    break;

                // well known escapes
                case '\b':
                    out.write("\\b");
                    break;
                case '\t':
                    out.write("\\t");
                    break;
                case '\n':
                    out.write("\\n");
                    break;
                case '\f':
                    out.write("\\f");
                    break;
                case '\r':
                    out.write("\\r");
                    break;

                // other escaping
                default:
                    if (c < ' ') {
                        String t = "000" + Integer.toHexString(c);
                        out.write("\\u" + t.substring(t.length() - 4));
                    } else {
                        out.write(c);
                    }
            }
        }
    }    
}
