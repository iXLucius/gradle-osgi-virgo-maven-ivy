/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lucius.framework.config;

import java.util.Set;

/**
 *
 * @author lqzhai
 */
public interface Configuration {

    /**
     * Check if the configuration is empty.
     *
     * @return {@code true} if the configuration contains no property,
     * {@code false} otherwise.
     */
    boolean isEmpty();

    /**
     * Check if the configuration contains the specified key.
     *
     * @param key the key whose presence in this configuration is to be tested
     *
     * @return {@code true} if the configuration contains a value for this key,
     * {@code false} otherwise
     */
    boolean containsKey(String key);

    /**
     * Add a property to the configuration. If it already exists then the value
     * stated here will be added to the configuration entry. For example, if the
     * property:
     *
     * <pre>resource.loader = file</pre>
     *
     * is already present in the configuration and you call
     *
     * <pre>addProperty("resource.loader", "classpath")</pre>
     *
     * Then you will end up with a List like the following:
     *
     * <pre>["file", "classpath"]</pre>
     *
     * @param key The key to add the property to.
     * @param value The value to add.
     */
    void addProperty(String key, Object value);

    /**
     * Set a property, this will replace any previously set values. Set values
     * is implicitly a call to clearProperty(key), addProperty(key, value).
     *
     * @param key The key of the property to change
     * @param value The new value
     */
    void setProperty(String key, Object value);

    /**
     * Gets a property from the configuration. This is the most basic get method
     * for retrieving values of properties. In a typical implementation of the
     * {@code Configuration} interface the other get methods (that return
     * specific data types) will internally make use of this method. On this
     * level variable substitution is not yet performed. The returned object is
     * an internal representation of the property value for the passed in key.
     * It is owned by the {@code Configuration} object. So a caller should not
     * modify this object. It cannot be guaranteed that this object will stay
     * constant over time (i.e. further update operations on the configuration
     * may change its internal state).
     *
     * @param key property to retrieve
     * @return the value to which this configuration maps the specified key, or
     * null if the configuration contains no mapping for this key.
     */
    Object getProperty(String key);

    /**
     * Get the list of the keys contained in the configuration. The returned
     * iterator can be used to obtain all defined keys. Note that the exact
     * behavior of the iterator's {@code remove()} method is specific to a
     * concrete implementation. It <em>may</em> remove the corresponding
     * property from the configuration, but this is not guaranteed. In any case
     * it is no replacement for calling {@link #clearProperty(String)} for this
     * property. So it is highly recommended to avoid using the iterator's
     * {@code remove()} method.
     *
     * @return An Iterator.
     */
    Set<String> getKeys();

    /**
     * Get a boolean associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated boolean.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Boolean.
     */
    boolean getBoolean(String key);

    /**
     * Get a {@link Boolean} associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated boolean if key is found and has valid format,
     * default value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Boolean.
     */
    Boolean getBoolean(String key, Boolean defaultValue);

    /**
     * Get a byte associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated byte.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Byte.
     */
    byte getByte(String key);

    /**
     * Get a {@link Byte} associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated byte if key is found and has valid format, default
     * value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Byte.
     */
    Byte getByte(String key, Byte defaultValue);

    /**
     * Get a double associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated double.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Double.
     */
    double getDouble(String key);

    /**
     * Get a {@link Double} associated with the given configuration key.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated double if key is found and has valid format,
     * default value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Double.
     */
    Double getDouble(String key, Double defaultValue);

    /**
     * Get a float associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated float.
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Float.
     */
    float getFloat(String key);

    /**
     * Get a {@link Float} associated with the given configuration key. If the
     * key doesn't map to an existing object, the default value is returned.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated float if key is found and has valid format,
     * default value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Float.
     */
    Float getFloat(String key, Float defaultValue);

    /**
     * Get a int associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated int.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Integer.
     */
    int getInt(String key);

    /**
     * Get an {@link Integer} associated with the given configuration key. If
     * the key doesn't map to an existing object, the default value is returned.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated int if key is found and has valid format, default
     * value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Integer.
     */
    Integer getInteger(String key, Integer defaultValue);

    /**
     * Get a long associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated long.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Long.
     */
    long getLong(String key);

    /**
     * Get a {@link Long} associated with the given configuration key. If the
     * key doesn't map to an existing object, the default value is returned.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated long if key is found and has valid format, default
     * value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Long.
     */
    Long getLong(String key, Long defaultValue);

    /**
     * Get a short associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated short.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Short.
     */
    short getShort(String key);

    /**
     * Get a {@link Short} associated with the given configuration key. If the
     * key doesn't map to an existing object, the default value is returned.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated short if key is found and has valid format,
     * default value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a Short.
     */
    Short getShort(String key, Short defaultValue);

    /**
     * Get a string associated with the given configuration key.
     *
     * @param key The configuration key.
     * @return The associated string.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a String.
     */
    String getString(String key);

    /**
     * Get a string associated with the given configuration key. If the key
     * doesn't map to an existing object, the default value is returned.
     *
     * @param key The configuration key.
     * @param defaultValue The default value.
     * @return The associated string if key is found and has valid format,
     * default value otherwise.
     *
     * @throws ConversionException is thrown if the key maps to an object that
     * is not a String.
     */
    String getString(String key, String defaultValue);
    
    Object getArray(String key);
}
