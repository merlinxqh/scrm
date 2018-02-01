package com.hiveview.base.util.excel;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel读取工具类
 */
public class ExcelReader {

	/**
	 * 根据绝对路径获取excel数据,从第二行开始读取，第一行默认为表头
	 * 读完数据会删除文件
	 * @param pathName excel 绝对路径
	 * @return
	 */
	public static List<String[]> getExcelData(String pathName){
        return getExcelData(pathName,1);
	}

	public static List<String[]> getExcelData(String pathName, int startRow){
		File f = new File(pathName);
		return getExcelData(f,startRow);
	}

	public static List<String[]> getExcelData(File f){
		return getExcelData(f,1);
	}

	public static List<String[]> getExcelData(MultipartFile f, int startRow){
		String suffix = f.getName().substring(f.getName().lastIndexOf(".")+1);
		List<String[]> listCell = new ArrayList<String[]>();
		InputStream is=null;
		try {
			is=f.getInputStream();
			if(suffix.toLowerCase().equals("xls")){
				listCell = getExcelData2003(is, startRow);
			}else{
				listCell = getExcelData2007(is, startRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listCell;
	}


	public static List<String[]> getExcelData(File f, int startRow){
		String suffix = f.getName().substring(f.getName().lastIndexOf(".")+1);
		List<String[]> listCell = new ArrayList<String[]>();
		FileInputStream is=null;
		try {
			is= new FileInputStream(f);
			if(suffix.toLowerCase().equals("xls")){
				listCell = getExcelData2003(is, startRow);
			}else{
				listCell = getExcelData2007(is, startRow);
			}
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listCell;
	}
	
	
	/**
	 * 根据文件流获取excel数据
	 * @param is
	 * @param startRowNum
	 * @return
	 */
	public static List<String[]> getExcelData(InputStream is , int startRowNum){
			
			List<String[]> listCell = new ArrayList<String[]>();
			try {
				HSSFWorkbook wbs = new HSSFWorkbook(is);
				HSSFSheet childSheet = wbs.getSheetAt(0);
				for (int j = startRowNum; j <= childSheet.getLastRowNum(); j++) {
					childSheet.getNumMergedRegions();
					HSSFRow row = childSheet.getRow(j);
					String [] arr = new String[50];
					if (null != row) {
						for (int k = 0; k < row.getLastCellNum(); k++) {
							HSSFCell cell = row.getCell(k);
							//单元格数据
							arr[k] = getCellValue(cell);
						}
					}
					listCell.add(arr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listCell;
		}
	
	/**
	 * 根据文件流获取excel数据
	 * @param is
	 * @param startRowNum
	 * @return
	 */
	public static List<String[]> getExcelData(InputStream is , int startRowNum,boolean is2003){
			
			List<String[]> listCell = new ArrayList<String[]>();
			try {
				Workbook wbs = null ;
				if(is2003){
					wbs = new HSSFWorkbook(is);
				}else{
					wbs = new XSSFWorkbook(is);
				}
				Sheet childSheet = wbs.getSheetAt(0);
				for (int j = startRowNum; j <= childSheet.getLastRowNum(); j++) {
					childSheet.getNumMergedRegions();
					Row row = childSheet.getRow(j);
					String [] arr = new String[50];
					if (null != row) {
						for (int k = 0; k < row.getLastCellNum(); k++) {
							Cell cell = row.getCell(k);
							//单元格数据
							arr[k] = getCellValue(cell);
						}
					}
					listCell.add(arr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listCell;
		}
	
	public static Workbook getBook(FileInputStream is) throws IOException{
		Workbook book = null;
		try {
			book = new XSSFWorkbook(is);
		}catch (Exception e) {
			POIFSFileSystem fs = new POIFSFileSystem(is);
			book = new HSSFWorkbook(fs);
		}
		return book;
	}
	
	/**
	 * 根据绝对路径获取excel数据
	 * @param pathName excel 绝对路径
	 * @return
	 */
	public static List<String[]> getExcelData(FileInputStream is , int startRowNum,Boolean is2003excle){
		if(is2003excle){
			return getExcelData2003(is, startRowNum);
		}
		List<String[]> listCell = new ArrayList<String[]>();
		Workbook book = null;
		try {
			book = getBook(is);
			Sheet childSheet = book.getSheetAt(0);
			for (int j = startRowNum; j <= childSheet.getLastRowNum(); j++) {
				Row row = childSheet.getRow(j);
				String [] arr = new String[50];
				if (null != row) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						if(k>=50){
							break;
						}
						Cell cell = row.getCell(k);
						//单元格数据
						arr[k] = getCellValue2Pio(cell);
					}
				}
				listCell.add(arr);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return listCell;
	}
	
	/**
	 * 根据绝对路径获取excel数据
	 * @param startRowNum 从哪一行开始
	 * @return
	 */
	private static List<String[]> getExcelData2003(InputStream is , int startRowNum){
		List<String[]> listCell = new ArrayList<String[]>();
		try {
			HSSFWorkbook wbs = new HSSFWorkbook(is);
			HSSFSheet childSheet = wbs.getSheetAt(0);
			for (int j = startRowNum; j <= childSheet.getLastRowNum(); j++) {
				childSheet.getNumMergedRegions();
				HSSFRow row = childSheet.getRow(j);
				String [] arr = new String[50];
				if (null != row) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						if(k>=50){
							break;
						}
						HSSFCell cell = row.getCell(k);
						//单元格数据
						arr[k] = getCellValue(cell);
					}
				}
				listCell.add(arr);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return listCell;
	}
	
	private static List<String[]> getExcelData2007(InputStream is , int startRowNum){
		List<String[]> listCell = new ArrayList<String[]>();
		try {
			XSSFWorkbook wbs = new XSSFWorkbook(is);
			XSSFSheet childSheet = wbs.getSheetAt(0);
			for (int j = startRowNum; j <= childSheet.getLastRowNum(); j++) {
				childSheet.getNumMergedRegions();
				XSSFRow row = childSheet.getRow(j);
				String [] arr = new String[50];
				if (null != row) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						if(k>=50){
							break;
						}
						XSSFCell cell = row.getCell(k);
						//单元格数据
						arr[k] = getCellValue2007(cell);
					}
				}
				listCell.add(arr);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return listCell;
	}
	
	/**
	 * 获取单元格的值
	 * @param cell 单元
	 * @return 字符串
	 */
	private static String getCellValue(Cell cell ){
		String value = null;
		if (null != cell) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
			        //  如果是date类型则 ，获取该cell的date值   
					value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			    } else { // 纯数字   
			    	value = String.valueOf(cell.getNumericCellValue());   
			    }
				break;
			case Cell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue()+"";
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				break;
			default:
				break;
			}
		} else {
			
		}
		return value;
	}
	
	/**
	 * 获取单元格的值
	 * @param cell 单元
	 * @return 字符串
	 */
	private static String getCellValue2007(XSSFCell cell ){
		String value = null;
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
			        //  如果是date类型则 ，获取该cell的date值   
					value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			    } else { // 纯数字   
			    	value = String.valueOf(cell.getNumericCellValue());   
			    }
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
//				System.out.println("   ");
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				break;
			default:
				break;
			}
		} else {
		}
		return value;
	}
	
	/**
	 * 获取单元格的值
	 * @param cell 单元
	 * @return 字符串
	 */
	private static String getCellValue2Pio(Cell cell){
		String value = null;
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
			        //  如果是date类型则 ，获取该cell的date值   
					value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			    } else { // 纯数字   
			    	value = String.valueOf(cell.getNumericCellValue());   
			    }
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				break;
			default:
				break;
			}
		}
		return value;
	}
}
