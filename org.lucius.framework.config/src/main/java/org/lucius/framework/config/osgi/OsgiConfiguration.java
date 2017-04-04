package org.lucius.framework.config.osgi;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.lucius.framework.config.Configuration;

/**
 *
 * @author lqzhai
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class OsgiConfiguration implements Configuration{
    
    protected Dictionary prop = new Properties();
    
    OsgiConfiguration(Dictionary prop){
        if( null == prop ){
            throw new IllegalArgumentException("Dictionary cann't be null");
        }
        this.prop = prop;
    }

    @Override
    public boolean isEmpty() {
        return prop.isEmpty();
    }

    @Override
    public void addProperty(String key, Object value) {
        prop.put(key, value);
    }

    @Override
    public void setProperty(String key, Object value) {
        prop.put(key, value);
    }

    @Override
    public Object getProperty(String key) {
        return this.prop.get(key);
    }

    @Override
    public Set<String> getKeys() {
        Enumeration<String> kenum = this.prop.keys();
        
        Set<String> keys = new HashSet<String>();
        while( kenum.hasMoreElements()){
            keys.add(kenum.nextElement());
        }
        return keys;
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        Object b = prop.get(key);
        if (null == b) {
            return defaultValue;
        }
        return (Boolean)b;
    }

    @Override
    public byte getByte(String key) {
        return getByte(key, null);
    }

    @Override
    public Byte getByte(String key, Byte defaultValue) {
        Object ob = prop.get(key);
        if (null == ob) {
            return defaultValue;
        }
        return Byte.valueOf(ob.toString());
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, null);
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        Object ob = prop.get(key);
        if (null == ob) {
            return defaultValue;
        }
        return Double.parseDouble(ob.toString());
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, null);
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        Object ob = prop.get(key);
        if (null == ob) {
            return defaultValue;
        }
        return Float.parseFloat(ob.toString());
    }

    @Override
    public int getInt(String key) {
        return getInteger(key, null);
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        Object ob = prop.get(key);
        if (null == ob) {
            return defaultValue;
        }
        return Integer.parseInt(ob.toString());
    }

    @Override
    public long getLong(String key) {
        return getLong(key, null);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        Object ob = prop.get(key);
        if (null == ob) {
            return defaultValue;
        }
        return Long.parseLong(ob.toString());
    }

    @Override
    public short getShort(String key) {
        return getShort(key, null);
    }

    @Override
    public Short getShort(String key, Short defaultValue) {
        Object ob = prop.get(key);
        if (null == ob) {
            return defaultValue;
        }
        return Short.parseShort(ob.toString());
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public String getString(String key, String defaultValue) {
        Object ob = prop.get(key);
        if (null == ob) {
            return defaultValue;
        }
        return (String) ob;
    }

    @Override
    public boolean containsKey(String key) {
        Set<String> keys = this.getKeys();
        
        return keys.contains(key);
    }

    @Override
    public Object getArray(String key) {
        return this.prop.get(key);
    }
}
