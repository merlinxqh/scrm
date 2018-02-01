package com.hiveview.base.util.excel;

import com.hiveview.base.util.serializer.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * @ClassName ExcelExportUtil
 * @Description 导出Excel工具类
 */
public class ExcelExportUtil {
	private static Logger logger= LoggerFactory.getLogger(ExcelExportUtil.class);

	/**
	 * 导出Excel，可以放自定义信息的
	 * @param headers 列头
	 * @param properties 字段
	 * @param data
	 * @throws IOException
	 */
	public static File export(String[] headers, String[] properties, List data, boolean isMap){
		FileOutputStream o = null;
		File file = null;
		try {
			//创建工作薄
			SXSSFWorkbook workBook = null;
			String filepath = System.getProperty("java.io.tmpdir") + File.separatorChar + System.currentTimeMillis()+""+ new Random().nextInt();
			workBook = new SXSSFWorkbook(200);//超过两百行放入就放入磁盘，否则在内存中
			filepath += ".xlsx";
			file = new File(filepath);
			if(headers == null || headers.length == 0 || properties == null || properties.length == 0){
				throw new Exception("入参出错！");
			}

			readInRows(workBook, properties,headers,data,isMap);
			o = new FileOutputStream(file);
			workBook.write(o);

        } catch (Exception ex) {
        	logger.error("导出时构造文件出错："+ex.getMessage());
        } finally {
            try {
				o.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
            try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

		return file;
	}


	/**
	 * 导出Excel,含多个sheet，可以放自定义信息的
	 * @param data
	 * @throws IOException
	 */
	public static File exportList(List<Map<String,Object>> data){
		FileOutputStream o = null;
		File file = null;
		try { //创建工作薄
			Workbook workBook = null;
			String filepath = System.getProperty("java.io.tmpdir") + File.separatorChar + System.currentTimeMillis()+""+ new Random().nextInt();
			workBook = new SXSSFWorkbook(200);//超过两百行放入就放入磁盘，否则在内存中
			filepath += ".xlsx";
			file = new File(filepath);
			newReadInRows(workBook, data);
			o = new FileOutputStream(file);
			workBook.write(o);

		} catch (Exception ex) {
			logger.error("导出时构造文件出错："+ex.getMessage());
		} finally {
			try {
				o.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return file;
	}

	/**
	 * 插入数据，返回最大行数
	 * @param sheet
	 * @param properties
	 * @param data
	 * @return
	 */
	public static int setRowData(Sheet sheet, int rowIndex, String[] properties, List<Object> data, boolean isMap){
		int i = rowIndex ;
		for(Object bean : data){
			//创建行
			Row row = sheet.createRow(i);
			//遍历属性创建单元格
			int j = 0 ;
			for(String property : properties){
				try {
					Cell cell = row.createCell(j);
					String cellValue = "" ;
					if(isMap){
                         Map map= (Map) bean;
                         cellValue = ObjectUtils.getMapValue(map,property);
					}else {
						if(property.indexOf(".")>0){
							String[] params=property.split("\\.");
							Method getMethod =null;
							Object value=null;
							for(int n=0;n<params.length;n++){
								if(n==0){
									getMethod = bean.getClass().getMethod(getMethodName("get",params[n]));
									value = getMethod.invoke(bean);
								}else if(n<params.length-1){
									getMethod = value.getClass().getMethod(getMethodName("get",params[n]));
									value = getMethod.invoke(value);
								}
							}
							if(value!=null){
								cellValue=getValueByProperty(value, params[params.length-1]);
							}
						}else{
							cellValue=getValueByProperty(bean, property);
						}
					}
					cell.setCellValue(cellValue);
				} catch (Exception e) {
					logger.error("", e);
				}
				j ++ ;
			}
			i++ ;
		}

		return i;
	}

	public static String getValueByProperty(Object obj,String property) throws Exception{
		String val="";
		String pp=property.indexOf("[")>0?property.split("\\[")[0]:property;
		String fmt=property.indexOf("[")>0?property.split("\\[")[1].replace("]", ""):"";

		Method getMethod = obj.getClass().getMethod(getMethodName("get",pp));
		//获取方法返回类型
		Object value = getMethod.invoke(obj);
		if(value!=null){
			val = value.toString();
		}else{
			val = "" ;
		}
		return val;
    }

	/**
	 * 创建列头
	 * @param sheet
	 * @param headers
	 */
	public static void setHeadRow(Workbook workBook, Sheet sheet , String[] headers){
		// 创建第一行
		CellStyle style = workBook.createCellStyle();
		style.setFillForegroundColor((short)17);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

		Row row = sheet.createRow(0);
		int i = 0 ;
		for(String head : headers){
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(head);
			i++ ;
		}
	}

	/**
	 * 创建汇总行
	 * @param sheet
	 */
	public static void setTotalRow(Sheet sheet , String[] totalCells , List data){
		// 创建行
		Row row = sheet.createRow(data.size()+2);
		int i = 0 ;
		for(String total : totalCells){
			Cell cell = row.createCell(i);
			cell.setCellValue(total);
			i++ ;
		}
	}

    /**
     * @Description 根据属性构建get/set方法名
     * @param type
     * @param fildeName
     * @return
     */
	public static String getMethodName(String type,String fildeName) {
    	//把一个字符串的第一个字母大写、效率是最高的、
        byte[] items = fildeName.getBytes();
        if(items.length > 2 && Character.isUpperCase(items[1])){
        	return type + fildeName ;
        }
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return type + (new String(items));
    }

	public static void down(HttpServletResponse response, File file, String downFileName) {
		OutputStream out = null;
    	try {

    		String suffix = file.getName().substring(file.getName().lastIndexOf("."));
    		FileInputStream inStream = null;
			inStream = new FileInputStream(file);
    		response.setContentType("application/octet-stream");
    		response.addHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(downFileName + suffix, "UTF-8"));
            out = response.getOutputStream();

    		byte[] buf = new byte[4096];
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                out.write(buf, 0, readLength);
            }
            inStream.close();
        } catch (IOException e) {
            logger.error("导出文件出错！");
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	public static void readInRows(Workbook workBook, String[] properties, String[] headers, List data, boolean isMap){
    	try {
    		Sheet sheet = null;
    		if(data !=null && data.size()>0){
    			sheet = workBook.createSheet();
				setHeadRow(workBook,sheet,headers);
				setRowData(sheet,1, properties, data,isMap);
    		}
		} catch (Exception e) {
			logger.error("--"+e.getMessage());
		}
    }




	public static void newReadInRows(Workbook workBook, List<Map<String,Object>> data) throws Exception {
		for(int i = 0; i < data.size(); i++) {
			String[] headers = (String[]) data.get(i).get("headers");
			String[] properties = (String[]) data.get(i).get("properties");
			String title = (String) data.get(i).get("title");
			if ( headers== null || headers.length == 0 || properties == null || properties.length == 0 || title == null || title.length() == 0) {
				throw new Exception("入参出错！");
			}
			try {
				Sheet sheet = null;
				if (data.get(i) != null && data.get(i).size() > 0) {
					sheet = workBook.createSheet(title);
					setHeadRow(workBook, sheet, headers);
					setRowData(sheet, 1, properties, (List<Object>) data.get(i).get("data"),false);
				}
			} catch (Exception e) {
				logger.error("--" + e.getMessage());
			}
		}
	}

    /**
     * 封装导出数据
     *
     * @author Alvin
     * @date 2016年4月20日
     * @version 1.0
     */
	public interface ExportModel{
    	/**
    	 * 数据集合
    	 * @return
    	 */
    	public List getData();
    	/**
    	 * 表头组
    	 * @return
    	 */
    	public String[] getHeaders();
    	/**
    	 * 字段组
    	 * @return
    	 */
    	public String[] getProperties();

    	/**
    	 * 导出文件名
    	 */
    	public String getFileName();
    }

    public static class DefaultExportModel<T> implements ExportModel{
    	private String[] headers;
    	private String[] properties;
    	private String fileName;
    	private List<T> data;


		@Override
		public List<T> getData() {
			return data;
		}

		@Override
		public String[] getHeaders() {
			return headers;
		}

		@Override
		public String[] getProperties() {
			return properties;
		}

		@Override
		public String getFileName() {
			return fileName;
		}

		public void setHeaders(String[] headers) {
			this.headers = headers;
		}

		public void setProperties(String[] properties) {
			this.properties = properties;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public void setData(List<T> data) {
			this.data = data;
		}



    }

}
