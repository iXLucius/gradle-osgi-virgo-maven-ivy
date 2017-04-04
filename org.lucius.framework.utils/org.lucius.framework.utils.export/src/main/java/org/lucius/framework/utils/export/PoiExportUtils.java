/**
 * @(#)PoiExportUtils.java 1.0 2015-4-28
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-4-28
 * Author:      zhuqh
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.export;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-4-28 上午10:44:10
 * Author:      zhuqh
 * Version:     1.0.0.0
 * Description: Initialize
 */
public class PoiExportUtils {
    private HSSFWorkbook workBook;

    public PoiExportUtils(){
        workBook = new HSSFWorkbook();
    }
    
    /**
     * 创建sheet
     * @param sheetName
     * @param workBook
     * @return
     */
    private HSSFSheet createHSSFSheet(String sheetName,HSSFWorkbook workBook){
         // 生成一个表格
        HSSFSheet sheet = workBook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);
        return sheet;
    }
    
    /**
     * 根据实体产生表格标题行
     * @param sheet
     * @param headers
     * @return
     */
	@SuppressWarnings("unused")
	private <T> void createHSSFSheetHeader(HSSFSheet sheet, List<T> fields) {
		HSSFRow row = sheet.createRow(0);
		int cellIndex = 0;
		for (Object obj : fields) {
			HSSFCell cell = row.createCell(cellIndex);
			HSSFRichTextString text = null;
			if (obj instanceof Field) {
				Field field = (Field) obj;
				
				String headerNameKey = field.getAnnotation(PoiCell.class).headerNameKey();
				String defaultHeaderName = field.getAnnotation(PoiCell.class).defaultHeaderName();
				text = new HSSFRichTextString(defaultHeaderName);
			} else {
				text = new HSSFRichTextString(obj.toString());
			}
			cell.setCellValue(text);
			setHeaderStyle(cell);//给页眉设置样式。
			cellIndex++;
		}
	}
    /**
     * 获取类中所有的注解了Excel cell标签的属性，包括父类的属性。
     * @param cla
     * @param fields
     */
	private void getCellFields(Class<?> cla, List<Field> fields,String...excludes) {
		if (!"java.lang.Object".equals(cla.getName())) {
			getCellFields(cla.getSuperclass(), fields,excludes);
		}
		A:for (Field field : cla.getDeclaredFields()) {
			if (field.isAnnotationPresent(PoiCell.class)) {
			    String fieldName = field.getName();
			    if(excludes.length > 0){
			        for(String exclude : excludes){
			            if(fieldName!=null && fieldName.equals(exclude)){
			                continue A;
			            }
			        }
			    }
				fields.add(field);
			}
		}
	}

	/**
     * @param sheetName,工作簿名称
     * @param dataset，需要导出的数据集合
     * @param clazz 集合中数据的类型
     * @param pattern，如果日期，那么需要设置日期类型格式
     * @param out，导出的流
     * @param excludes，排除的列，对应实体类属性名
	 */
	public <T> void exportExcel(String sheetName, Collection<T> dataset,Class<?> clazz, String pattern,
			OutputStream out, String... excludes) {
		try {
			HSSFSheet sheet = createHSSFSheet(sheetName, workBook);
			List<Field> fields = new ArrayList<Field>();
//		    将需要导出的列放入fields列表中。
			getCellFields(clazz, fields,excludes);
		    createHSSFSheetHeader(sheet, fields);

			Iterator<T> it = dataset.iterator();
			int index = 1;
			while (it.hasNext()) {
				HSSFRow row = sheet.createRow(index);
				T t = it.next();
				int cellIndex = 0;
				for (Field field : fields) {
					HSSFCell cell = row.createCell(cellIndex);
					setBodyStyle(cell);//给数据行设置样式。
					
					String fieldName = field.getName();
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

					Method getMethod = t.getClass().getMethod(getMethodName);
					Object returnValue = getMethod.invoke(t);

                    
                    String textValue = null;
					if (returnValue instanceof Date) {
						Date date = (Date) returnValue;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (returnValue instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(cellIndex, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) returnValue;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) cellIndex, index,
								(short) cellIndex, index);
						anchor.setAnchorType(2);
						HSSFPatriarch patri = sheet.createDrawingPatriarch();
						patri.createPicture(anchor, workBook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_PNG));
					} else if (returnValue != null) {
						// 其它数据类型都当作字符串简单处理
						textValue = returnValue.toString();
					}

					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							cell.setCellValue(textValue);
						}
					}
					cellIndex++;
				}
				index++;
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    
    /**
     * 给页眉添加样式
     * @param cell
     */
    private void setHeaderStyle(HSSFCell cell){
        // 生成一个样式
        HSSFCellStyle style = workBook.createCellStyle();
        // 设置这些样式
        style.setWrapText(true);//回绕文本
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);//设置单元格前景色，蓝色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
        // 生成一个字体
        HSSFFont font = workBook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        
        cell.setCellStyle(style);
    }
    
    /**
     * 给页眉添加样式
     * @param cell
     */
    private void setBodyStyle(HSSFCell cell){
        // 生成一个样式
        CellStyle style = workBook.createCellStyle();
        style.setWrapText(true);// 回绕文本
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
        
        cell.setCellStyle(style);
    }
    
    
    
    /**
     * 如果需要导出图片，那么需要在实体类中定义属性byte[] img 来存放图片数据
     * 这个方法，从url的路径获取图片字节数组
     * @param strUrl
     * @return
     */
//    private byte[] getImgByURL(String strUrl){
//        //"http://172.16.2.49/83/207/200/0db99469-d2b9-4b97-a1c2-497509c3f5f7.png"
//        byte[] byteArray = null;
//        try {
//            URL url = new URL(strUrl);
//            
//            InputStream input = url.openStream();
//   
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            byte[] buffer = new byte[4096];
//            int n = 0;
//            while (-1 != (n = input.read(buffer))) {
//                output.write(buffer, 0, n);
//            }
//            byteArray = output.toByteArray();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return byteArray;
//    }
}

