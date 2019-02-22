package com.yfancy.common.base.util;
 
import java.io.Writer;
import java.lang.reflect.Field;
 
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.springframework.util.StringUtils;


/** 
 * xml 转换工具类 
 *  
 * @author morning 
 * @date 2015年2月16日 下午2:42:50 
 */  
public class SerializeXmlUtil {

    /**
     * xml转成bean泛型方法
     * @param resultXml
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String resultXml, Class clazz) {
        // XStream对象设置默认安全防护，同时设置允许的类
        XStream stream = new XStream(new DomDriver());
//        XStream.setupDefaultSecurity(stream);
//        stream.allowTypes(new Class[]{clazz});
        stream.processAnnotations(new Class[]{clazz});
//        stream.setMode(XStream.NO_REFERENCES);
        stream.alias("xml", clazz);
        return (T) stream.fromXML(resultXml);
    }



    //xstream扩展,bean转xml自动加上![CDATA[]]
    private static XStream getMyXStream() {
        return new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 对所有xml节点都增加CDATA标记
                    boolean cdata = true;

                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
    }


    /**
     * bean转成微信的xml消息格式
     * @param object
     * @return
     */
    public static String beanToXml( Object object) {
        XStream xStream = getMyXStream();
//        xStream.alias("xml", object.getClass());
        xStream.processAnnotations(object.getClass());
        String xml = xStream.toXML(object);
        if (!StringUtils.isEmpty(xml)){
            return xml;
        }else{
            return null;
        }
    }






}  

