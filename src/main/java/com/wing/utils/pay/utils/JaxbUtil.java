package com.wing.utils.pay.utils;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

/**
 * 使用Jaxb2.0实现XML<->Java Object的Binder.
 * <p>
 * 特别支持Root对象是List的情形.
 */
public class JaxbUtil {

    // 多线程安全的Context.
    private JAXBContext jaxbContext;
    private String encoding="UTF-8";
    private Boolean format=Boolean.TRUE;

    /**
     * @param encoding  编码
     * @param types 所有需要序列化的Root对象的类型.
     */
    public JaxbUtil(String encoding, Class<?>... types) {
        try {
            jaxbContext = JAXBContext.newInstance(types);
            this.encoding=encoding;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    public JaxbUtil(String encoding, Boolean format, Class<?>... types) {
        try {
            jaxbContext = JAXBContext.newInstance(types);
            this.encoding=encoding;
            this.format=format;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Java Object->Xml.
     */
    public String toXml(Object root) {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller().marshal(root, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Java Object->Xml, 特别支持对Root Element是Collection的情形.
     */
    @SuppressWarnings("unchecked")
    public String toXml(Collection root, String rootName) {
        try {
            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.collection = root;

            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(
                    new QName(rootName), CollectionWrapper.class, wrapper);

            StringWriter writer = new StringWriter();
            createMarshaller().marshal(wrapperElement, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Xml->Java Object.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml) {
        try {
            StringReader reader = new StringReader(xml);
            return (T) createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Xml->Java Object, 支持大小写敏感或不敏感.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml, boolean caseSensitive) {
        try {
            String fromXml = xml;
            if (!caseSensitive)
                fromXml = xml.toLowerCase();
            StringReader reader = new StringReader(fromXml);
            return (T) createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建Marshaller, 设定encoding(可为Null).
     */
    public Marshaller createMarshaller() {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);  //是否格式化

            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  //设置编码

            return marshaller;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建UnMarshaller.
     */
    public Unmarshaller createUnmarshaller() {
        try {
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装Root Element 是 Collection的情况.
     */
    public static class CollectionWrapper {
        @SuppressWarnings("unchecked")
        @XmlAnyElement
        protected Collection collection;
    }

}
