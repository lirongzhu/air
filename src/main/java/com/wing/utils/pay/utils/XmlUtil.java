package com.wing.utils.pay.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 类名：wx xml
 * 描述：
 * 作者：shao
 * 时间：2015-12-16 16:01
 */
public class XmlUtil {

    private String str; //xml字符串  或  xml路径  或URL
    private String encoding="UTF-8";

    private Logger log= LoggerFactory.getLogger(XmlUtil.class);


    private XmlUtil() {
    }

    public XmlUtil(String str) {
        this.str = str;
        this.encoding = "UTF-8";
    }

    public XmlUtil(String str, String encoding) {
        this.encoding = encoding;
        this.str = str;
    }

    /**
     * 读取XML文件获取Document对象
     * @return
     */
    public Document getDocumentByFile(){
        if(str==null||"".equals(str.trim()))
            return null;
        File file=new File(str);
        if(file.exists()&&file.isFile()){
            try {
                return new SAXReader().read(file);
            } catch (DocumentException e) {
                log.error("获取Document对象失败!失败原因："+e.toString());
            }
        }else {
            log.error("xml文件({})不存在：",str);
        }
        return null;
    }

    /**
     * 读取url路径获取Document对象
     * @return
     */
    public Document getDocumentByUrl(){
        if(str==null||"".equals(str.trim()))
            return null;
        try {
            return new SAXReader().read(new URL(str));
        } catch (DocumentException e) {
            log.error("获取Document对象失败!失败原因："+e.toString());
        } catch (MalformedURLException e) {
            log.error("URL：{}错误!失败原因："+e.toString(),str);
        }
        return null;
    }

    /**
     * 读取XML文本内容获取Document对象
     * @return
     */
    public Document getDocumentByStr(){
        if(str==null||"".equals(str.trim()))
            return null;
        try {
            return DocumentHelper.parseText(str);
        } catch (DocumentException e) {
            log.error("获取Document对象失败!失败原因："+e.toString());
        }
        return null;
    }

    /**
     * 判断文件是否存在
     * @param p
     * @return
     */
    public boolean fileIsExist(String p){
        if(p==null||"".equals(p.trim()))
            return false;
        File file=new File(p);
        return file.exists()&&file.isFile();
    }

    /**
     * 格式化写入xml文档
     * @param document
     * @return
     */
    public boolean writeFormat(Document document){
        OutputFormat format= OutputFormat.createPrettyPrint();
        format.setEncoding(this.encoding);
        return writeToXml(document,format);
    }

    /**
     * 非格式化写入xml文档
     * @param document
     * @return
     */
    public boolean writeNoFormat(Document document){
        return writeToXml(document,null);
    }

    /**
     * 遍历获取所有节点
     * @param document
     * @return
     */
    public JSONObject getAllNodes(Document document){
        return getNode(document.getRootElement());
    }

    /**
     * 获取节点所有信息 （包括属性和子节点）
     * @param node
     * @return
     */
    public JSONObject getNode(Element node){
        JSONArray nodes=getChildrenNodes(node);
        List<Attribute> attrs=getAttributes(node);
        JSONObject result=new JSONObject();
        result.put("name",node.getName());
        if(attrs!=null&&attrs.size()>0){
            for(Attribute a:attrs){
                result.put(a.getName(),a.getValue());
            }
        }
        if(nodes!=null&&nodes.size()>0){
            result.put("children",nodes);
        }else {
            result.put("value",node.getStringValue());
        }
        return result;
    }

    /**
     * 获取节点的所有属性
     * @return
     */
    public List<Attribute> getAttributes(Element node){
        return node.attributes();
    }

    /**
     * 遍历当前节点下所有子节点
     * @return
     */
    public JSONArray getChildrenNodes(Element node){
        List<Element> elements=node.elements();
        JSONArray array=new JSONArray(elements.size());
        for(Element e:elements){
            array.add(getNode(e));
        }
        return array;
    }

    public static void main(String[] args){
        XmlUtil xmlUtil=new XmlUtil("C:\\Users\\lenovo\\Desktop\\s11.xml");
        Document document= xmlUtil.getDocumentByFile();
        System.out.println(xmlUtil.getAllNodes(document));
    }

    private boolean writeToXml(Document document, OutputFormat format){
        try {

            XMLWriter writer=format==null?new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(str)))):new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(str))),format);
            writer.write(document);
            writer.flush();
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            log.error("文件({})未找到！",str);
        } catch (IOException e) {
            log.error("输出文件发生错误!错误信息："+e.toString());
        }
        return false;
    }

    /**
     * 解析request中的xml
     * @param request
     * @return
     * @throws Exception
     */
    public static JSONObject parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在JSONObject中
        JSONObject object=new JSONObject();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            object.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();

        return object;
    }

}
