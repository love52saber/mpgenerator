package com.hedian.myGenerator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGenerator {
    /**
     * 代码工厂实例
     * @param args
     */
    public static void main(String[] args) {
        String dataBase = "code_generator_test";     // 数据库名
        String username = "root";          // 数据库用户名
        String password = "123456";          // 数据库密码
        String tableName = "tbl_user"; // 表名
        String pack = "com.wj.model";      // 包名
        try {
            // 获取数据
            List<Property> properties = readDbData(dataBase, username, password, tableName);

            Configuration cfg = new Configuration();
            String templatePath = TestGenerator.class.getClassLoader().getResource("myTemplate").getPath();
            cfg.setDirectoryForTemplateLoading(new File(templatePath));
            cfg.setObjectWrapper(new DefaultObjectWrapper());

            //获取模板文件
            Template template = cfg.getTemplate("entity.ftl");

            Map<String, Object> map = new HashMap<String, Object>();
            Entity entity = new Entity();
            entity.setClassName(getClassName(tableName));
            entity.setJavaPackage(pack);
            entity.setProperties(properties);
            map.put("entity", entity);

//            // 生成输出到控制台
//            Writer out = new OutputStreamWriter(System.out);
//            myTemplate.process(map, out);
//            out.flush();

            //生成输出到文件
            String root = genPackStr("src",pack);
            File fileDir = new File("D://test");
            // 创建文件夹，不存在则创建
            FileUtils.forceMkdir(fileDir);
            // 指定生成输出的文件
            File output = new File(fileDir + "/"+getClassName(tableName)+".java");
            Writer writer = new FileWriter(output);
            template.process(map, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取表数据
     * @param dataBase 数据库名
     * @param tableName 表名
     * @return
     */
    public static List<Property> readDbData(String dataBase, String username, String password, String tableName){
//        Collection<Map<String, String>> properties = new HashSet<Map<String, String>>();
        List<Property> properties = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://106.12.20.134:3306/"+dataBase+"?useUnicode=true&characterEncoding=UTF-8",username, password);
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getColumns(null, null, tableName, null);
            while (rs.next()) {
                Property property = new Property();
                property.setJavaType(genFieldType(rs.getString("TYPE_NAME")));
                property.setPropertyName(genFieldName(rs.getString("COLUMN_NAME")));
                properties.add(property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(conn != null){
                    conn.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 根据包名获取对应的路径名
     * @param root 根路径
     * @param pack 包名
     * @return
     */
    public static String genPackStr(String root,String pack){
        String result = root;
        String [] dirs = pack.split("\\.");
        for(String dir : dirs){
            result += "/"+dir;
        }
        return result;
    }

    /**
     * 根据表面获取类名
     * @param tableName 表名
     * @return
     */
    public static String getClassName(String tableName){
        String result = "";
        String lowerFeild = tableName.toLowerCase();
        String[] fields = lowerFeild.split("_");
        if (fields.length > 1) {
            for(int i=0;i<fields.length;i++){
                result += fields[i].substring(0,1).toUpperCase() + fields[i].substring(1, fields[i].length());
            }
        }
        return result;
    }

    /**
     * 根据表字段名获取java中的字段名
     * @param field 字段名
     * @return
     */
    public static String genFieldName(String field) {
        String result = "";
        String lowerFeild = field.toLowerCase();
        String[] fields = lowerFeild.split("_");
        result += fields[0];
        if (fields.length > 1) {
            for(int i=1;i<fields.length;i++){
                result += fields[i].substring(0,1).toUpperCase() + fields[i].substring(1, fields[i].length());
            }
        }
        return result;
    }

    /**
     * 根据表字段的类型生成对应的java的属性类型
     * @param type 字段类型
     * @return
     */
    public static String genFieldType(String type){
        String result = "String";
        if(type.toLowerCase().equals("varchar")){
            result = "String";
        }else if(type.toLowerCase().equals("int")){
            result = "int";
        }
        return result;
    }
}